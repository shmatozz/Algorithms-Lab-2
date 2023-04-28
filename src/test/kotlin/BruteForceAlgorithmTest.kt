
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Test
import kotlin.math.pow

class BruteForceAlgorithmTest {
    @Test
    fun sameBordersTest() {
        val rectangles = arrayOf(
            Rectangle(Point(0, 0), Point(10, 10)),
            Rectangle(Point(0, 0), Point(20, 20)),
            Rectangle(Point(0, 0), Point(30, 30)),
            Rectangle(Point(0, 0), Point(40, 30)),
            Rectangle(Point(0, 0), Point(60, 60))
        )
        val testPoints = arrayOf(
            Point(0, 0),
            Point(10, 10),
            Point(30, 30),
            Point(-1, -1),
            Point(61, 0),
            Point(5, 5),
            Point(15, 5),
            Point(40, 30),
            Point(40, 31),
        )

        val bruteForceAnswers = bruteForceAlgorithm(rectangles, testPoints)

        assertArrayEquals(
            arrayOf(5, 4, 1, 0, 0, 5, 4, 1, 1),
            bruteForceAnswers
        )
    }

    @Test
    fun recommendedTest() {
        val rectangles = getRectangles(25)
        val testPoints = getPoints(25)

        val bruteForceAnswers = bruteForceAlgorithm(rectangles, testPoints)

        assertArrayEquals(
            arrayOf(1, 1, 7, 7, 3, 4, 3, 8, 15, 1, 18, 21, 7, 3, 13, 10, 14, 3, 10, 3, 4, 3, 22, 15, 12),
            bruteForceAnswers
        )
    }

    @Test
    fun contestExample() {
        val rectangles = arrayOf(
            Rectangle(Point(2, 2), Point(6, 8)),
            Rectangle(Point(5, 4), Point(9, 10)),
            Rectangle(Point(4, 0), Point(11, 6)),
            Rectangle(Point(8, 2), Point(12, 12))
        )
        val testPoints = arrayOf(
            Point(2, 2),
            Point(12, 12),
            Point(10, 4),
            Point(5, 5),
            Point(2, 10),
        )

        val bruteForceAnswers = bruteForceAlgorithm(rectangles, testPoints)

        assertArrayEquals(
            arrayOf(1, 0, 2, 3, 0),
            bruteForceAnswers
        )
    }

    private fun getRectangles(n: Int) : Array<Rectangle> {
        val rectangles: Array<Rectangle> = Array(n) { Rectangle(Point(0, 0), Point(0, 0)) }
        for (i in 0 until n) {
            rectangles[i] = Rectangle(Point(10 * i, 10 * i), Point(10 * (2 * n - i), 10 * (2 * n - i)))
        }
        return rectangles
    }

    private fun getPoints(n: Int) : Array<Point> {
        val testPoints: Array<Point> = Array(n) { Point(0, 0) }
        for (i in 0 until n) {
            testPoints[i] = Point((((101 * i).toDouble().pow(31)) % (20 * n)).toInt(), ((103 * i).toDouble().pow(31) % (20 * n)).toInt())
        }
        return testPoints
    }
}