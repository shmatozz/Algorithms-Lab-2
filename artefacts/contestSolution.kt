class Point (val x: Int, val y:Int)
class Rectangle (val left: Point, val right: Point)

class Event (val x: Int,
             val yBegin: Int,
             val yEnd: Int,
             val status: Int)

class Node (var sum: Int = 0,
            var left: Node? = null,
            var right: Node? = null,
            var leftIndex: Int = 0,
            var rightIndex: Int = 0)

fun getRectanglesArray(n: Int) : Array<Rectangle> {
    val rectangles: Array<Rectangle> = Array(n) { Rectangle(Point(0, 0), Point(0, 0)) }

    for (i in 0 until n) {
        val (x1, y1, x2, y2) = readln().split(' ').map {it.toInt()}
        rectangles[i] = Rectangle(Point(x1, y1), Point(x2, y2))
        //rectangles[i] = Rectangle(Point(10 * i, 10 * i), Point(10 * (2 * n - i), 10 * (2 * n - i)))
    }

    return rectangles
}

fun getTestPointsArray(n: Int) : Array<Point> {
    val testPoints: Array<Point> = Array(n) { Point(0, 0) }

    for (i in 0 until n) {
        val (x, y) = readln().split(' ').map { it.toInt() }
        testPoints[i] = Point(x, y)
        //testPoints[i] = Point((((101 * i).toDouble().pow(31)) % (20 * n)).toInt(), ((103 * i).toDouble().pow(31) % (20 * n)).toInt())
    }

    return testPoints
}

// getting array where indexes is zipped coordinates
fun getZippedCoordinates(rectangles: Array<Rectangle>) : Pair<List<Int>, List<Int>> {
    val zippedX = Array(rectangles.size * 2) { 0 }
    val zippedY = Array(rectangles.size * 2) { 0 }
    var j = 0

    // get zipped coordinates for building matrix
    for (rectangle in rectangles) {
        zippedX[j] = rectangle.left.x
        zippedY[j] = rectangle.left.y
        zippedX[j + 1] = rectangle.right.x
        zippedY[j + 1] = rectangle.right.y
        j += 2
    }
    // sorting zipped coordinates
    zippedX.sort()
    zippedY.sort()

    return Pair(zippedX.distinct(), zippedY.distinct())
}

// find position of point using lower_bound algorithm
fun findPosition(array: List<Int>, value: Int) : Int {
    var begin = 0
    var count = array.size; var step: Int
    while (count > 0) {
        var cur = begin
        step = count / 2
        cur += step
        if (value >= array[cur]) {
            begin = cur + 1
            count -= step + 1
        } else {
            count = step
        }
    }
    return begin - 1
}

// building empty segment tree
fun buildEmptyTree(array: Array<Int>, leftIndex: Int, rightIndex: Int): Node {
    // if reached half interval [leftIndex, leftIndex + 1)
    if (leftIndex + 1 == rightIndex) {
        return Node(array[leftIndex], null, null, leftIndex, rightIndex)
    }

    val mid = (leftIndex + rightIndex) / 2
    val left = buildEmptyTree(array, leftIndex, mid)
    val right = buildEmptyTree(array, mid, rightIndex)

    return Node(left.sum + right.sum, left, right, left.leftIndex, right.rightIndex)
}

// get number of rectangles for point
fun getAnswer(node: Node?, target: Int): Int {
    // if node == null -> lower level reached
    if (node != null) {
        val mid = (node.leftIndex + node.rightIndex) / 2
        return if (target < mid) {
            node.sum + getAnswer(node.left, target)
        } else {
            node.sum + getAnswer(node.right, target)
        }
    }
    return 0
}

// adding to segment tree
fun add(node: Node, start: Int, end: Int, value: Int) : Node {
    // if current node is in interval, return *new persistent* node
    if (start <= node.leftIndex && node.rightIndex <= end) {
        return Node(node.sum + value, node.left, node.right, node.leftIndex, node.rightIndex)
    }

    // if current node is not in interval, return node
    if (node.rightIndex <= start || end <= node.leftIndex) {
        return node
    }

    // creating *new persistent* node
    val new = Node(node.sum, node.left, node.right, node.leftIndex, node.rightIndex)

    // checking left node
    new.left = add(new.left!!, start, end, value)

    // checking right node
    new.right = add(new.right!!, start, end, value)

    // return *new persistent* node
    return new
}

// algorithm of building persistent segment tree on rectangles existence
fun buildPersistentSegmentTree(rectangles: Array<Rectangle>, zippedX: List<Int>, zippedY: List<Int>) : Array<Node> {
    val events = Array(rectangles.size * 2) { Event(0, 0, 0, -1) } // beginning and ending of rectangle existence
    val roots = Array(events.size) { Node() }   // array of persistent tree roots

    // filling array of events
    var it = 0
    for (rectangle in rectangles) {
        events[it++] = Event(findPosition(zippedX, rectangle.left.x),    // position on zipped X coordinate
            findPosition(zippedY, rectangle.left.y),    // lower bound of rectangle
            findPosition(zippedY, rectangle.right.y), // upper bound of rectangle
            1)  // status == 1 means beginning of rectangle
        events[it++] = Event(findPosition(zippedX, rectangle.right.x),
            findPosition(zippedY, rectangle.left.y),
            findPosition(zippedY, rectangle.right.y),
            -1) // status == -1 means ending of rectangle
    }
    // sorting events array by X coordinate
    events.sortBy { it.x }

    // building empty segment tree
    var root = buildEmptyTree(Array(zippedY.size) { 0 }, 0, zippedY.size)

    // processing events
    var lastX = events[0].x
    it = 0
    for (event in events) {
        // if finished processing all events for the current X
        if (lastX != event.x) {
            roots[it++] = root   // add final root in array of roots
            lastX = event.x      // update last X
        }

        root = add(root, event.yBegin, event.yEnd, event.status)    // add new event to tree
    }

    return roots
}

// getting answers
fun getAnswersFromPersistentTree(points: Array<Point>, roots: Array<Node>, zippedX: List<Int>, zippedY: List<Int>) : Array<Int> {
    val answersForPoints = Array(points.size) { 0 }

    // searching answers for given points
    for (i in points.indices) {
        val positionX = findPosition(zippedX, points[i].x)  // get indexes of point position on the map
        val positionY = findPosition(zippedY, points[i].y)  //

        if (positionX == -1 || positionY == -1) {   // if point out of borders
            answersForPoints[i] = 0
        } else {
            answersForPoints[i] = getAnswer(roots[positionX], positionY)
        }
    }

    return answersForPoints
}

fun segmentTreeAlgorithm(rectangles: Array<Rectangle>, points: Array<Point>): Array<Int> {
    // if number of rectangles == 0, return array of 0 as answer
    if (rectangles.isEmpty()) {
        return Array(points.size) { 0 }
    }

    // getting zipped coordinates of rectangles
    val (zippedX, zippedY) = getZippedCoordinates(rectangles)
    // getting array of persistent segment tree roots for every event
    val persistentTreeRoots = buildPersistentSegmentTree(rectangles, zippedX, zippedY)

    // getting number of rectangles for points

    return getAnswersFromPersistentTree(points, persistentTreeRoots, zippedX, zippedY)
}

fun main() {
    val n = readln().toInt()
    val rectangles = getRectanglesArray(n)
    val m = readln().toInt()
    val testPoints = getTestPointsArray(m)

    val segmentTreeAnswers = segmentTreeAlgorithm(rectangles, testPoints)

    for (i in segmentTreeAnswers) print("$i ")
}