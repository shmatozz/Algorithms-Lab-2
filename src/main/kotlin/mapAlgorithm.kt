fun generateMap(rectangles: Array<Rectangle>, zippedX: List<Int>, zippedY: List<Int>) : Array<Array<Int>> {
    // creating empty map
    val mapMatrix = Array(zippedY.size) { Array(zippedX.size) { 0 } }

    // filling map
    for (rectangle in rectangles) {
        val indexStartX = findPosition(zippedX, rectangle.left.x)              // get indexes of rectangle coordinates to fill matrix
        val indexStartY = findPosition(zippedY, rectangle.left.y)              //
        val indexEndX = findPosition(zippedX, rectangle.right.x)     //
        val indexEndY = findPosition(zippedY, rectangle.right.y)     //

        for (i in indexStartY until indexEndY) {
            for (j in indexStartX until indexEndX)  {
                mapMatrix[i][j]++
            }
        }
    }

    return mapMatrix
}

fun getAnswersFromMap(mapMatrix: Array<Array<Int>>, points: Array<Point>, zippedX: List<Int>, zippedY: List<Int>) : Array<Int> {
    val answersForPoints = Array(points.size) { 0 }

    // search answers to given points
    for (i in points.indices) {
        val positionX = findPosition(zippedX, points[i].x)  // get indexes of point position on the map
        val positionY = findPosition(zippedY, points[i].y)  //

        if (positionX == -1 || positionY == -1) {   // if point out of borders
            answersForPoints[i] = 0
        } else {
            answersForPoints[i] = mapMatrix[positionY][positionX]
        }
    }

    return answersForPoints
}

fun mapAlgorithm(rectangles: Array<Rectangle>, points: Array<Point>): Array<Int> {
    // if number of rectangles == 0, return array of 0 as answer
    if (rectangles.isEmpty()) {
        return Array(points.size) { 0 }
    }

    // getting zipped coordinates of rectangles
    val (zippedX, zippedY) = getZippedCoordinates(rectangles)
    val mapMatrix = generateMap(rectangles, zippedX, zippedY)

    return getAnswersFromMap(mapMatrix, points, zippedX, zippedY)
}