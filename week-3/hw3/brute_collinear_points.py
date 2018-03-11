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

segments = []

N = len(points)
counter = 0

for i in range(N):
    p = points[i]

    for j in range(i+1, N):
        q = points[j]

        slope_pq = slope(p, q)

        for k in range(j+1, N):
            r = points[k]

            slope_pr = slope(p, r)

            if slope_pq != slope_pr:
                continue

            for l in range(k+1, N):
                s = points[l]

                slope_ps = slope(p, s)

                if slope_ps != slope_pq:
                    continue

                counter += 1
                segments.append([p, q, r, s])

print(segments)
