fun main() {
    println("Введите N - количество прямоугольников:")
    val n: Int = readln().toInt()
    val rectangles = getRectanglesArray(n)
    val testPoints = getTestPointsArray(n)

    printArrayRectangles(rectangles)

    val bruteForceAnswers = bruteForceAlgorithm(rectangles, testPoints)
    printAnswers(testPoints, bruteForceAnswers)
}