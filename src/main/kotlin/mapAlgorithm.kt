import java.text.ParsePosition

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

// find position with binary search
fun findPos(array: List<Int>, value: Int) : Int{
    var left = 0; var right = array.size - 1
    while (left < right) {
        val middle = (right + left) / 2
        if (array[middle] == value) {
            return middle
        } else if (array[middle] < value) {
            left = middle + 1
        } else if (array[middle] > value) {
            right = middle - 1
        }
    }
    return right
}

fun findPointPosition(array: List<Int>, value: Int) : Int {
    if (value == array.last()) return array.size - 2
    if (value > array.last()) return -1
    if (value < array.first()) return -1
    var left = 0; var right = array.size - 2
    while (left < right) {
        val middle = (right + left) / 2
        if (array[middle] <= value && value < array[middle + 1]) {
            return middle
        } else if (array[middle] > value) {
            right = middle - 1
        } else {
            left = middle + 1
        }
    }
    return right
}


fun mapAlgorithm(rectangles: Array<Rectangle>, points: Array<Point>) : Array<Int> {
    val answersForPoints = Array(points.size) { 0 }
    val (zippedX, zippedY) = getZippedCoordinates(rectangles)
    if (zippedX.isEmpty()) return answersForPoints
    val mapMatrix = Array(zippedY.size - 1) { Array(zippedX.size - 1) { 0 } }

    // filling map
    for (rectangle in rectangles) {
        val indexStartX = findPos(zippedX, rectangle.left.x)
        val indexStartY = findPos(zippedY, rectangle.left.y)
        val indexEndX = findPos(zippedX, rectangle.right.x)
        val indexEndY = findPos(zippedY, rectangle.right.y)
        for (i in indexStartY until   indexEndY) {
            for (j in indexStartX until   indexEndX) {
                mapMatrix[i][j]++
            }
        }
    }

    // find answers for points
    for (i in points.indices) {
        var positionX = findPointPosition(zippedX, points[i].x)
        var positionY = findPointPosition(zippedY, points[i].y)

        if (positionX == -1 || positionY == -1) {
            answersForPoints[i] = 0
        } else {
            if (zippedY[positionY] == points[i].y && positionY > (zippedY.size - 2) / 2) {
                positionY--
            }
            if (zippedX[positionX] == points[i].x && positionX > (zippedX.size - 2) / 2) {
                positionX--
            }
            answersForPoints[i] = mapMatrix[positionY][positionX]
        }
    }

    return answersForPoints
}