# E9 - The Smoker's problem
#
# @author Tom Adams
# @author Max Huang
# @author Mitchie Maluschnig
# @author Asher Statham
#
# @since 23 July 2018

import math

#A class to hold information about each intersection
class Node:

    def __init__(self,state,parent,cost,heuristic,distance):
        self.state = state
        self.parent = parent
        self.cost = cost
        self.heuristic = heuristic
        self.distance = distance

    def toString(self):
        return("Node Position: " + str(self.state) + " Cost: " + str(self.cost) + " Heuristic: " +
              str(int(self.heuristic)) + " CH: " + str(self.cost + self.heuristic) + " distance: " + str(self.distance))

#returns the straight line distance between two states
def getDist(state1,state2):
    return abs(state1[0] - state2[0]) + abs(state1[1] - state2[1])

#Manhattan distance to closest non-smoker
def getCost(currentState,thisWorld):
    smallestDist = 10000
    for n in range(1,len(thisWorld)): #for each non-smoker in the world
        dist = getDist(currentState,thisWorld[n])
        if dist < smallestDist:
            smallestDist = dist
    return smallestDist*(-1)

#straight line distance from current state to the end state
def getHeuristic(currentState, endState):
    return math.sqrt(((endState[0] - currentState[0])**2) + ((endState[1] - currentState[1])**2))

#returns all the states that can be reached from current state
def getStates(currentState, thisWorld):
    nextStates = []
    if currentState[0] > 0: nextStates.append([currentState[0] - 1,currentState[1]])#only add if there is a intersection to the left
    if currentState[1] > 0: nextStates.append([currentState[0],currentState[1] - 1]) #only add if there is a intersection above
    if currentState[0] < thisWorld[0][1] - 1: nextStates.append([currentState[0] + 1,currentState[1]]) #only add if there is a intersection to the right
    if currentState[1] < thisWorld[0][0] - 1: nextStates.append([currentState[0],currentState[1] + 1])#only add if there is a intersection below

    return nextStates

##########################
#### Main Starts Here ####
##########################
file = None
while file == None:
    fileName = input("Enter name of file\n")
    try:
        file = open(fileName,"r")
    except:
        print("not a valid file name")
        file = None
lines = file.readlines()
file.close()

minCost = -10000
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

###########################
#### Search Starts Here####
###########################
#for every world, perform an A* search to minimise the total distance traveled
# whilst maximising total distance to each non-smoker.
initState = [0,0]
for world in worlds:
    openList = []
    closedList = []
    endState = [world[0][1]-1,world[0][0]-1]

    rootNode = Node(initState,None,getCost(initState,world),getHeuristic(initState,endState),0)
    openList.append(rootNode)
    currentNode = rootNode

    while not currentNode.state == endState:
        if (currentNode.cost > minCost):
            minCost = currentNode.cost

        #find smallest cost+heuristic in open list
        lowestNodeIndex = 0
        for openNode in range(len(openList)):
            if(openList[openNode].cost +
               openList[openNode].heuristic) < (openList[lowestNodeIndex].cost +
                                                openList[lowestNodeIndex].heuristic):
                lowestNodeIndex = openNode

        closedList.append(openList[lowestNodeIndex])
        currentNode = openList.pop(lowestNodeIndex)

        newStates = getStates(currentNode.state,world)

        for state in newStates:
            newNode = Node(state,currentNode,getCost(state,world),getHeuristic(state,endState),currentNode.distance+1)

            inClosedList = False
            for closeNode in closedList:
                if closeNode.state == newNode.state:
                    inClosedList = True
                    break

            if inClosedList == False:
                openList.append(newNode)
    print("Min " + str(minCost*(-1)) + ", Total " + str(currentNode.distance))

    #Path Travelled by Smoker
#---------------------------------------------
    #print the route of the considerate smoker
    # route = [currentNode]
    # while not currentNode == rootNode:
    #     route.append(currentNode.parent)
    #     currentNode = currentNode.parent
    # route.reverse()
    # for node in route:
    #     print(node.toString())
