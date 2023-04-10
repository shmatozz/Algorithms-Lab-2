// getting array where indexes is zipped coordinates
fun getZippedCoordinates(rectangles: Array<Rectangle>) : Pair<List<Int>, List<Int>> {
    val zippedX = Array(rectangles.size * 3) { 0 }
    val zippedY = Array(rectangles.size * 3) { 0 }
    var j = 0

    // get zipped coordinates for building matrix
    for (rectangle in rectangles) {
        zippedX[j] = rectangle.left.x
        zippedY[j] = rectangle.left.y
        zippedX[j + 1] = rectangle.right.x
        zippedX[j + 2] = rectangle.right.x + 1
        zippedY[j + 1] = rectangle.right.y
        zippedY[j + 2] = rectangle.right.y + 1
        j += 3
    }
    // sorting zipped coordinates
    zippedX.sort()
    zippedY.sort()

    return Pair(zippedX.distinct(), zippedY.distinct())
}

// find position of point using lower_bound algorithm
fun findPosition(array: List<Int>, value: Int) : Int{
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

fun mapAlgorithm(rectangles: Array<Rectangle>, points: Array<Point>) : Array<Int> {
    val answersForPoints = Array(points.size) { 0 }
    val (zippedX, zippedY) = getZippedCoordinates(rectangles)   // get zipped coordinates of rectangles
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
    printMapMatrix(mapMatrix)

    // find answers for points
    for (i in points.indices) {
        val positionX = findPosition(zippedX, points[i].x)  // get indexes of point position on the map
        val positionY = findPosition(zippedY, points[i].y)  //

        if (positionX == -1 || positionY == -1) {
            answersForPoints[i] = 0
        } else {
            answersForPoints[i] = mapMatrix[positionY][positionX]
        }
    }

    return answersForPoints
}