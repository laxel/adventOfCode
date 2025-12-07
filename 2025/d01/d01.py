import math

f = open("input.txt")
pos = 50
p1 = 0
p2 = 0
for l in f:
    data = l.strip()
    char = data[0]
    num = int(data[1:])
    if data[0] == "L":
        num = -num
    ppos = pos
    pos += num
    p2 += math.floor(abs(pos)/100)
    if pos <= 0:
        p2 += 1
    if ppos == 0 and pos < 0:
        p2 -= 1
    pos = pos % 100
    if pos == 0:
        p1 += 1
    # print("Rotated "+data+", new position: "+str(pos)+ ".\t p1:"+str(p1)+", p2:"+str(p2))
print("Part 1: "+str(p1))
print("Part 2: "+str(p2))