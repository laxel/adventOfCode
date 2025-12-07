def joltage(data, n):
    ppos = -1
    res  = ""
    for k in range(0,n):
        hi  = 0
        pos = 0
        for i in range(ppos+1, len(data)-n+1+k):
            cval = int(data[i])
            if cval > hi:
                pos = i
                hi  = cval
        ppos = pos
        res += str(hi)
    return int(res)
    
f = open("input.txt")
p1, p2 = [0,0]
for l in f:
    data = l.strip()
    # print(data)
    p1 += joltage(data,2)
    p2 += joltage(data,12)

print("Part 1: "+str(p1))
print("Part 2: "+str(p2))

