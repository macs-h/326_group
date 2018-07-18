#optimise distnce from top-left to bottom-right whilst optimising total distance to each smoker

class node:

    def __init__(self,state,parent,cost,heuristic):
        self.state = state
        self.parent = parent
        self.cost = cost
        self.heuristic = heuristic

    def toString(self):
        print("Node: " + self.state)

def cost(currentState):
    goalState = ()



#need to split up different worlds, sepparate the first line from the rest.
fileName = input("Enter name of file\n")
file = open(fileName + ".txt","r")
lines = file.readlines()
file.close()

worlds = [[]]
isFirst = 0
numWorld = 0
for i in range(len(lines)):
    if lines[i] == "\n":
        numWorld +=1
        isFirst = 0
        worlds.append([])
        i +=1
    else:
        lines[i] = lines[i][:-1].split(" ")
        lines[i][0] = int(lines[i][0])
        lines[i][1] = int(lines[i][1])

        if isFirst == 0:
            isFirst = 1
            length = lines[i][0]
            height = lines[i][1]
        else:
            worlds[numWorld].append(lines[i])

print(worlds,length,height)

