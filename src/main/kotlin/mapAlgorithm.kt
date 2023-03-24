// getting array where indexes is zipped coordinates
fun getZippedCoordinates(rectangles: Array<Rectangle>) : Pair<List<Int>, List<Int>> {
    val zippedX = Array(rectangles.size * 2) { 0 }
    val zippedY = Array(rectangles.size * 2) { 0 }
    var j = 0

    // get zipped coordinates for building matrix
    for (rectangle in rectangles) {
        zippedX[j] = rectangle.left.x
        zippedY[j] = rectangle.left.y
        zippedX[j + 1] = rectangle.right.x
        zippedY[j + 1] = rectangle.right.y
        j += 2
    }
    zippedX.sort()
    zippedY.sort()

    return Pair(zippedX.distinct(), zippedY.distinct())
}

// find position of point using upper_bound algorithm from cpp algorithm lib
fun findPosition(array: List<Int>, value: Int) : Int{
    if (value > array.last()) return -1
    if (value < array.first()) return -1
    if (value == array.last()) return array.size - 1
    var begin = 0;
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
    val (zippedX, zippedY) = getZippedCoordinates(rectangles)
    if (zippedX.isEmpty()) return answersForPoints
    val mapMatrix = Array(zippedY.size) { Array(zippedX.size) { 0 } }

    // filling map
    for (rectangle in rectangles) {
        val indexStartX = findPosition(zippedX, rectangle.left.x)
        val indexStartY = findPosition(zippedY, rectangle.left.y)
        val indexEndX = findPosition(zippedX, rectangle.right.x)
        val indexEndY = findPosition(zippedY, rectangle.right.y)
        for (i in indexStartY ..  indexEndY ) {
            for (j in indexStartX ..   indexEndX) {
                mapMatrix[i][j]++
            }
        }
    }


    // find answers for points
    for (i in points.indices) {
        val positionX = findPosition(zippedX, points[i].x)
        val positionY = findPosition(zippedY, points[i].y)
        if (positionX == -1 || positionY == -1) {
            answersForPoints[i] = 0
        } else {
            answersForPoints[i] = mapMatrix[positionY][positionX]
        }
    }

    return answersForPoints
}