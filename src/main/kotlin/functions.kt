import kotlin.math.pow

class Point (val x: Int, val y:Int)
class Rectangle (val left: Point, val right: Point)

// printing coordinates of rectangles
fun printArrayRectangles(rectangles: Array<Rectangle>) {
    for (i in rectangles.indices) {
        println("(${rectangles[i].left.x}, ${rectangles[i].left.y}) " +
                "(${rectangles[i].right.x}, ${rectangles[i].right.y})")
    }
    println()
}

// printing coordinates of points
fun printArrayPoints(points: Array<Point>) {
    for (point in points) {
        println("(${point.x}, ${point.y})")
    }
    println()
}

// printing answers for each point
fun printAnswers(points: Array<Point>, answers: Array<Int>) {
    for (i in points.indices) {
        println("(${points[i].x}, ${points[i].y}) - ${answers[i]}")
    }
    println()
}

// printing map of rectangles in map algorithm
fun printMapMatrix(matrix: Array<Array<Int>>) {
    for (row in matrix.indices) {
        for (col in matrix[row]) {
            print("%-4d".format(col))
        }
        println()
    }
}

// get coordinates of rectangles (from console / formula generation)
fun getRectanglesArray(n: Int) : Array<Rectangle> {
    val rectangles: Array<Rectangle> = Array(n) { Rectangle(Point(0, 0), Point(0, 0)) }

    for (i in 0 until n) {
        //val (x1, y1, x2, y2) = readln().split(' ').map {it.toInt()}
        //rectangles[i] = Rectangle(Point(x1, y1), Point(x2, y2))
        rectangles[i] = Rectangle(Point(10 * i, 10 * i), Point(10 * (2 * n - i), 10 * (2 * n - i)))
    }

    return rectangles
}

// get coordinates of points (from console / formula generation)
fun getTestPointsArray(n: Int) : Array<Point> {
    val testPoints: Array<Point> = Array(n) { Point(0, 0) }

    for (i in 0 until n) {
        //val (x, y) = readln().split(' ').map { it.toInt() }
        //testPoints[i] = Point(x, y)
        testPoints[i] = Point((((101 * i).toDouble().pow(31)) % (20 * n)).toInt(),
                               ((103 * i).toDouble().pow(31) % (20 * n)).toInt())
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
        //zippedX[j + 1] = rectangle.right.x
        zippedX[j + 1] = rectangle.right.x + 1
        //zippedY[j + 1] = rectangle.right.y
        zippedY[j + 1] = rectangle.right.y + 1
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