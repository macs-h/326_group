#optimise distnce from top-left to bottom-right whilst maximising total distance to each non-smoker
import math

class Node:

    def __init__(self,state,parent,cost,heuristic):
        self.state = state
        self.parent = parent
        self.cost = cost
        self.heuristic = heuristic

    def toString(self):
        print("Node Position: " + str(self.state) + " Cost: " + str(self.cost) + " Heuristic: " +
              str(int(self.heuristic)) + " CH: " + str(self.cost + self.heuristic))

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

def getStates(currentState, thisWorld):
    #returns the states that can be reached from current state
    nextStates = []
    if currentState[0] > 0: nextStates.append([currentState[0] - 1,currentState[1]])#only add if there is a intersection to the left
    if currentState[1] > 0: nextStates.append([currentState[0],currentState[1] - 1]) #only add if there is a intersection above
    if currentState[0] < thisWorld[0][1] - 1: nextStates.append([currentState[0] + 1,currentState[1]]) #only add if there is a intersection to the right
    if currentState[1] < thisWorld[0][0] - 1: nextStates.append([currentState[0],currentState[1] + 1])#only add if there is a intersection below

    return nextStates

##########################
#### Code Starts Here ####
##########################

#need to split up different worlds, sepparate the first line from the rest.
#fileName = input("Enter name of file\n")
fileName = "exampleWorld"
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

print("Worlds: " + str(worlds))

initState = [0,0]
for world in worlds:
    openList = []
    closedList = []
    endState = [world[0][1]-1,world[0][0]-1]

    rootNode = Node(initState,None,getCost(initState,world),getHeuristic(initState,endState))
    rootNode.toString()
    openList.append(rootNode)
    currentNode = rootNode

    while not currentNode.state == endState:
        # att break point currentNode.state is [7,8] and so is endState but the error must occur before
        print(currentNode.state == endState)

        #find smallest cost+heuristic in open list
        lowestNodeIndex = 0
        for openNode in range(len(openList)):
            if(openList[openNode].cost +
               openList[openNode].heuristic) < (openList[lowestNodeIndex].cost +
                                                openList[lowestNodeIndex].heuristic):
                lowestNodeIndex = openNode

            closedList.append(openList[lowestNodeIndex])
            currentNode = openList.pop(lowestNodeIndex)
            #currentNode.toString()

            newStates = getStates(currentNode.state,world)

            for state in newStates:
                newNode = Node(state,currentNode,getCost(state,world),getHeuristic(state,endState))

                inClosedList = False
                for closeNode in closedList:
                    if closeNode.state == newNode.state:
                        inClosedList = True
                        break

                if inClosedList == False:
                    openList.append(newNode)
    print("End State: " + currentNode.toString())

'''
open_list = list()
closed_list = list()
init_state = puzzle.reset()
root = node(s=init_state, parent=None, cost=0, action=None, h=getManhattan(init_state))
open_list.append(root)
currentNode = root
numTurns = 0
start_time = time.time()

while not puzzle.isgoal(s=currentNode.s):
    #actions = puzzle.actions(s=current_state)
    lowestNode = 0;
    #this part is probably need to be more efficient!
    for i in range(0,len(open_list),10):
        if((open_list[i].cost + open_list[i].h) < (open_list[lowestNode].cost + open_list[lowestNode].h)):
            lowestNode = i

    closed_list.append(open_list[lowestNode])
    currentNode = open_list.pop(lowestNode)

    for action in puzzle.actions(currentNode.s):
        #open_list.append(puzzle.step(s=current_state, a=action))
        newState = puzzle.step(s=currentNode.s, a=action)
        newNode = node(s=newState,parent=currentNode,cost=currentNode.cost+1,action=action,h=getManhattan(newState))
        in_closed_list = False
        for n in closed_list:
            #if (compareArrays(n.s,newNode.s)):
            if(n.s == newNode.s):
                in_closed_list = True
                break

        if(in_closed_list == False):
            open_list.append(newNode)

print(currentNode.toString())

elapsed_time = time.time() - start_time
print("Time: %.1f seconds" % elapsed_time)
while currentNode != root:
    actions_list.append(currentNode.action)
    currentNode = currentNode.parent
actions_list.reverse()
print(len(actions_list))
puzzle.show(a=actions_list)
'''
