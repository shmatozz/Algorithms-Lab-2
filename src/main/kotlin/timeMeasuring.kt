import java.io.File

fun main() {
    val n = 13
    val timesBruteForcePrep = Array(n) { 0L }
    val timesBruteForceAns = Array(n) { 0L }
    val timesMapPrep = Array(n) { 0L }
    val timesMapAns = Array(n) { 0L }
    val timesSegmentTreePrep = Array(n) { 0L }
    val timesSegmentTreeAns = Array(n) { 0L }
    var zippedCords: Pair<List<Int>, List<Int>>
    var count = 1
    for (i in 0..12) {
        val rectangles = getRectanglesArray(count)
        val testPoints = getTestPointsArray(100000, count)

        // measuring preparation time
        var begin: Long = System.nanoTime()
        bruteForcePrep(rectangles)
        timesBruteForcePrep[i] = System.nanoTime() - begin
        // measuring answering time
        begin = System.nanoTime()
        bruteForceAlgorithm(rectangles, testPoints)
        timesBruteForceAns[i] = System.nanoTime() - begin

        // measuring preparation time
        begin = System.nanoTime()
        zippedCords = getZippedCoordinates(rectangles)
        val mapMatrix = generateMap(rectangles, zippedCords.first, zippedCords.second)
        timesMapPrep[i] = System.nanoTime() - begin
        // measuring answering time
        begin = System.nanoTime()
        getAnswersFromMap(mapMatrix, testPoints, zippedCords.first, zippedCords.second)
        timesMapAns[i] = System.nanoTime() - begin

        // measuring preparation time
        begin = System.nanoTime()
        zippedCords = getZippedCoordinates(rectangles)
        val persistentTreeRoots = buildPersistentSegmentTree(rectangles, zippedCords.first, zippedCords.second)
        timesSegmentTreePrep[i]  = System.nanoTime() - begin
        // measuring answering time
        begin = System.nanoTime()
        getAnswersFromPersistentTree(testPoints, persistentTreeRoots, zippedCords.first, zippedCords.second)
        timesSegmentTreeAns[i] = System.nanoTime() - begin

        println(count)
        count *= 2
    }

    val path = "C:\\Users\\matve\\IdeaProjects\\Algorithms-Labs\\Lab-2\\artefacts\\times.txt"
    File(path).printWriter().use { out ->
        timesBruteForcePrep.forEach {
            out.print("$it ")
        }
        out.println()
        timesMapPrep.forEach {
            out.print("$it ")
        }
        out.println()
        timesSegmentTreePrep.forEach {
            out.print("$it ")
        }
        out.println()

        timesBruteForceAns.forEach {
            out.print("$it ")
        }
        out.println()
        timesMapAns.forEach {
            out.print("$it ")
        }
        out.println()
        timesSegmentTreeAns.forEach {
            out.print("$it ")
        }
        out.println()
    }
}