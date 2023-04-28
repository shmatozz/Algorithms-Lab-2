fun bruteForceAlgorithm(rectangles: Array<Rectangle>, points: Array<Point>) : Array<Int> {
    val answersForPoints = Array(points.size) { 0 } // Array of answers to each given point

    for (i in points.indices) {
        for (j in rectangles.indices) {
            if (points[i].x >= rectangles[j].left.x && points[i].x < rectangles[j].right.x &&
                points[i].y >= rectangles[j].left.y && points[i].y < rectangles[j].right.y) {
                answersForPoints[i]++
            }
        }
    }

    return answersForPoints
}

fun bruteForcePrep(rectangles: Array<Rectangle>) : Int {
    return 0
}