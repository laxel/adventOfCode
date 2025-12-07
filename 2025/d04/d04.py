import copy
p1, p2 = [0,0]
nmatrix = [list(line.strip()) for line in open("input.txt")]
width, height = [len(nmatrix[0]), len(nmatrix)]
first_iter = True
while True:
    has_changed = False
    matrix = copy.deepcopy(nmatrix)
    for y in (range(0,height)):
        for x in range(0,width):
            tot = 0
            if matrix[y][x] == '.':
                continue
            for dy in range(-1,2):
                for dx in range(-1,2):
                    if y+dy < 0 or y+dy >= height or x+dx < 0 or x+dx >= width or (dx == 0 and dy == 0):
                        continue
                    if matrix[y+dy][x+dx] == '@':
                        tot += 1
            if tot < 4:
                if first_iter:
                    p1 += 1
                p2 += 1
                has_changed = True
                nmatrix[y][x] = '.'
    first_iter = False
    if not has_changed:
        break
print("Part 1: "+str(p1))
print("Part 2: "+str(p2))
