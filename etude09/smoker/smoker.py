#optimise distnce from top-left to bottom-right whilst optimising total distance to each smoker

class node:

    def __init__(self,state,parent,cost,heuristic):
        self.state = state
        self.parent = parent
        self.cost = cost
        self.heuristic = heuristic

    def toString(self):
        print("Node: " + self.state)

def costFunc(currentState):
    #min distance from any non-smoker
    goalState = ()

def heuristicFunc(currentState):
    #straight line distance
    return


#need to split up different worlds, sepparate the first line from the rest.
fileName = input("Enter name of file\n")
file = open(fileName + ".txt","r")
lines = file.readlines()
file.close()

worlds = [[]]
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

        worlds[numWorld].append(lines[i])

print(worlds)

