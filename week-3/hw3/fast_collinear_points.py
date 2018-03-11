# Using Python to debug my Java code because debugging Java is hard :(


def read_points(path):
    f = open(path, mode='r')
    data = f.readlines()
    f.close()

    points = []

    for line in data[1:]:
        x, y = line.split()

        x = int(x)
        y = int(y)

        points.append((x, y))

    return points


def slope(p, q):
    if p[1] == q[1]:
        if p[0] == q[0]:
            return float('-inf')
        else:
            return +0.0
    else:
        if p[0] == q[0]:
            return float('inf')
        else:
            return (q[1] - p[1]) / (q[0] - p[0])

points = read_points(path='week-3/hw3/collinear/input8.txt')

