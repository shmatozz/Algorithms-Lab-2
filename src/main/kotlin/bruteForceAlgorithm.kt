fun bruteForceAlgorithm(rectangles: Array<Rectangle>, points: Array<Point>) : Array<Int> {
    val answersForPoints = Array<Int>(points.size) { 0 }

    for (i in points.indices) {
        for (rectangle in rectangles) {
            if (points[i].x >= rectangle.left.x && points[i].x <= rectangle.right.x &&
                points[i].y >= rectangle.left.y && points[i].y <= rectangle.right.y) {
                answersForPoints[i]++
            }
        }
    }

    return answersForPoints
}