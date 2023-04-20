import matplotlib.pyplot as mpl

def draw_graph(BruteForce, Map, SegmentTree, title):
    #x = list(range(10, 1001, 10))
    x = [2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096]
    f = mpl.figure()
    f.set_figwidth(10)
    f.set_figheight(7)
    mpl.plot(x, BruteForce, marker='', label='BruteForce')

    mpl.plot(x, Map, marker='', label='Map')

    mpl.plot(x, SegmentTree, marker='', label='SegmentTree')

    mpl.yscale("log")
    mpl.xscale("log")
    mpl.title(title)
    mpl.xlabel("number of rectangles")
    mpl.ylabel("time, nanoseconds")
    mpl.legend()

path = "C:\\Users\\matve\\IdeaProjects\\Algorithms-Labs\\Lab-2\\artefacts\\times.txt"

with open(path) as file:
    timesBruteForcePrep = list(map(int, file.readline().split()))
    timesMapPrep = list(map(int, file.readline().split()))
    timesSegmentTreePrep = list(map(int, file.readline().split()))

    timesBruteForceAns = list(map(int, file.readline().split()))
    timesMapAns = list(map(int, file.readline().split()))
    timesSegmentTreeAns = list(map(int, file.readline().split()))

    timesBruteForceTotal = [timesBruteForcePrep[i] + timesBruteForceAns[i] for i in range(0, len(timesBruteForcePrep))]
    timesMapTotal = [timesMapPrep[i] + timesMapAns[i] for i in range(0, len(timesMapPrep))]
    timesSTreeTotal = [timesSegmentTreePrep[i] + timesSegmentTreeAns[i] for i in range(0, len(timesSegmentTreePrep))]
    draw_graph(timesBruteForcePrep[1:], timesMapPrep[1:], timesSegmentTreePrep[1:], "Preparation time")
    draw_graph(timesBruteForceAns[1:], timesMapAns[1:], timesSegmentTreeAns[1:], "Answering time")
    draw_graph(timesBruteForceTotal[1:], timesMapTotal[1:], timesSTreeTotal[1:], "Total working time")
    mpl.show()
