p1, p2 = [0,0]
f = open("input.txt")
ranges = []
ingrds = []
mode = 0
# PROCESS DATA
for l in f:
    data = l.strip()
    if mode == 0:
        if data == "":
            mode = 1
            continue
        l,r = data.split("-")
        ranges += [[int(l),int(r),True]]
    else:
        ingrds += [int(data)]
# PART 1
for i in ingrds:
    spoiled = True
    for r in ranges:
        if i >= r[0] and i <= r[1]:
            spoiled = False
            break
    if not spoiled:
        p1 += 1
print("Part 1: "+str(p1))
# PART 2
for a in range(0,len(ranges)):
    for b in range(a+1,len(ranges)):
        a1,a2,ac = ranges[a]
        b1,b2,bc = ranges[b]
        if not ac or not bc:
            continue
        # Check if A range is inside B range, if yes, invalidate A range
        if a1 >= b1 and a2 <= b2:
            ranges[a][2] = False
            break
        # Check if B range is inside A range, if yes, invalidate B range
        if b1 >= a1 and b2 <= a2:
            ranges[b][2] = False
            continue
        # Check if A range can be absorbed by B range
        if a2 >= b1 and a2 < b2:
            ranges[a][2] = False
            ranges[b] = [a1,b2,True]
            break
        if a1 <= b2 and a1 > b1:
            ranges[a][2] = False
            ranges[b] = [b1,a2,True]
            break
    # print (ranges)
for a,b,c in ranges:
    if c:
        p2 += b-a+1
print("Part 2: "+str(p2))
