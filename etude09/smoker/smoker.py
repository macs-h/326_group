#optimise distnce from top-left to bottom-right whilst maximising total distance to each non-smoker
import math

class Node:

    def __init__(self,state,parent,cost,heuristic):
        self.state = state
        self.parent = parent
        self.cost = cost
        self.heuristic = heuristic

    def toString(self):
        print("Node Position: " + str(self.state) + " Cost: " + str(self.cost) + " Heuristic: " + str(self.heuristic))

def getDist(state1,state2):
    return abs(state1[0] - state2[0]) + abs(state1[1] - state2[1])

def getCost(currentState,thisWorld):
    #distance from closest non-smoker (may need to expand to encorperate all non-smokers)
    smallestDist = 10000
    closestPersonIndex = 1
    for n in range(1,len(thisWorld)-1):
        dist = getDist(currentState,thisWorld[n])
        if dist < smallestDist:
            smallestDist = dist
            closestPersonIndex = n
    return smallestDist


def getHeuristic(currentState, endState):
    #straight line distance
    return math.sqrt(((endState[0] - currentState[0])**2) + ((endState[1] - currentState[1])**2))

##########################
#### Code Starts Here ####
##########################

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

initState = [0,0]
n1 = Node(initState,None,getCost(initState,worlds[0]),getHeuristic(initState,worlds[0][0]))
n1.toString()