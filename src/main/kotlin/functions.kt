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