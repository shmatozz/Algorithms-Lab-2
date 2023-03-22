import kotlin.math.pow

class Point (val x: Int, val y:Int)
class Rectangle (val left: Point, val right: Point)

fun printArrayRectangles(rectangles: Array<Rectangle>) {
    for (i in rectangles.indices) {
        println("(${rectangles[i].left.x}, ${rectangles[i].left.y}) " +
                "(${rectangles[i].right.x}, ${rectangles[i].right.y})")
    }
    println()
}

fun printAnswers(points: Array<Point>, answers: Array<Int>) {
    for (i in points.indices) {
        println("(${points[i].x}, ${points[i].y}) - ${answers[i]}")
    }
    println()
}

fun getRectanglesArray(n: Int) : Array<Rectangle> {
    val rectangles: Array<Rectangle> = Array(n) { Rectangle(Point(0, 0), Point(0, 0)) }

    for (i in 0 until n) {
        rectangles[i] = Rectangle(Point(10 * i, 10 * i), Point(10 * (2 * n - i), 10 * (2 * n - i)))
    }

    return rectangles
}

fun getTestPointsArray(n: Int) : Array<Point> {
    val testPoints: Array<Point> = Array(n) { Point(0, 0) }

    for (i in 0 until n) {
        testPoints[i] = Point((((101 * i).toDouble().pow(31)) % (20 * n)).toInt(),
            ((103 * i).toDouble().pow(31) % (20 * n)).toInt())
    }

    return testPoints
}