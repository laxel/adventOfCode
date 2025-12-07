from functools import reduce
p1, p2 = [0,0]
file = "input.txt"
# PART 1
matrix = [(line.strip()).split() for line in open(file)]
for ncol in range(0,len(matrix[0])):
    col = [row[ncol] for row in matrix]
    nrs = [int(x) for x in col[:-1]]
    op  = col[-1]
    if op == "+":
        p1 += reduce(lambda x, y: x + y, nrs)
    else:
        p1 += reduce(lambda x, y: x * y, nrs)
# PART 2
cmatrix = [[c for c in line if c != "\n"] for line in open(file)]
# Add one more empty row to the end of the data to mark exit
for i in range(0,len(cmatrix)):
    cmatrix[i] += " "
newline = True
nrs = []
for ncol in range(0,len(cmatrix[0])):
    col = [row[ncol] for row in cmatrix]
    # If start of new coloumn group, save the operator 
    if newline:
        op = col[-1]
        newline = False
    # If end of coloumn group, calculate equation, else read number 
    if all([c==" " for c in col]): # TODO: Add end condtion
        newline = True
        if op == "+":
            p2 += reduce(lambda x, y: x + y, nrs)
        else:
            p2 += reduce(lambda x, y: x * y, nrs)
        nrs = []
    else:
        nrs += [int("".join(col[:-1]))]
         
print("Part 1: "+str(p1))
print("Part 2: "+str(p2))