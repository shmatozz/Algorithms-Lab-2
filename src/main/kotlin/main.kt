fun main() {
    val n = readln().toInt()
    val rectangles = getRectanglesArray(n)
    val m = readln().toInt()
    val testPoints = getTestPointsArray(m)

    println("Rectangles")
    printArrayRectangles(rectangles)
    println("Points")
    printArrayPoints(testPoints)

    val bruteForceAnswers = bruteForceAlgorithm(rectangles, testPoints)
    println("BruteForce answers:")
    printAnswers(testPoints, bruteForceAnswers)

    val mapAnswers = mapAlgorithm(rectangles, testPoints)
    println("Map answers:")
    printAnswers(testPoints, mapAnswers)

}