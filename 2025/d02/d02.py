import re
def p1_check(n):
    pattern = re.compile(r'^(\d*)\1$')
    return bool(pattern.match(str(n)))
def p2_check(n):
    pattern = re.compile(r'^(\d*)\1+$')
    return bool(pattern.match(str(n)))

p1 = 0
p2 = 0
with open("input.txt", "r") as f:
    line = f.readline()
    for l in line.split(","):
        # print(l)
        lo,hi = l.split("-")
        for i in range (int(lo), int(hi)+1):
            word = str(i)
            if p1_check(i):
                p1 += i
                # print("   " + word)
            if p2_check(i):
                p2 += i
                # print("   " + word)


print("Part 1: "+str(p1))
print("Part 2: "+str(p2))