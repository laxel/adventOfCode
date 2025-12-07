p1, p2 = [0,0]
grid = [list(line.strip()) for line in open("input.txt")]
beams = {grid[0].index("S"):1}
for y in range(1,len(grid)):
    nbeams = {}
    for x, n in beams.items():
        if grid[y][x] == ".":
            nbeams.setdefault(x,0)
            nbeams[x] = nbeams.get(x,0) + n
        elif grid[y][x] == "^":
            nbeams[x-1] = nbeams.get(x-1,0) + n
            nbeams[x+1] = nbeams.get(x+1,0) + n
            p1 += 1
    beams = nbeams
print("Part 1: "+str(p1))
for _, n in beams.items():
    p2 += n
print("Part 2: "+str(p2))
