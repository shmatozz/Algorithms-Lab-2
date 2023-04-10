fun mapAlgorithm(rectangles: Array<Rectangle>, points: Array<Point>) : Array<Int> {
    val answersForPoints = Array(points.size) { 0 }

    // getting zipped coordinates of rectangles
    val (zippedX, zippedY) = getZippedCoordinates(rectangles)

    if (zippedX.isEmpty()) return answersForPoints      // if number of rectangles == 0, return array of 0 as answer
    val mapMatrix = Array(zippedY.size) { Array(zippedX.size) { 0 } }

    // filling map
    for (rectangle in rectangles) {
        val indexStartX = findPosition(zippedX, rectangle.left.x)              // get indexes of rectangle coordinates to fill matrix
        val indexStartY = findPosition(zippedY, rectangle.left.y)              //
        val indexEndX = findPosition(zippedX, rectangle.right.x + 1)     //
        val indexEndY = findPosition(zippedY, rectangle.right.y + 1)     //

        for (i in indexStartY until indexEndY) {
            for (j in indexStartX until indexEndX)  {
                mapMatrix[i][j]++
            }
        }
    }

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