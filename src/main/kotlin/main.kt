fun main() {
    val n = readln().toInt()
    val rectangles = getRectanglesArray(n)
    val m = readln().toInt()
    val testPoints = getTestPointsArray(m, n)

    println("Rectangles")
    printArrayRectangles(rectangles)
    println("Points")
    printArrayPoints(testPoints)

    val bruteForceAnswers = bruteForceAlgorithm(rectangles, testPoints)
    //printAnswers(testPoints, bruteForceAnswers)

    val mapAnswers = mapAlgorithm(rectangles, testPoints)
    //printAnswers(testPoints, mapAnswers)

    val segmentTreeAnswers = segmentTreeAlgorithm(rectangles, testPoints)
    //printAnswers(testPoints, segmentTreeAnswers)
}