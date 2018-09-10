from copy import deepcopy
import sys
import numpy as np


class Node:
    def __init__(self, state, distances, non_smokers):
        self.state = state
        self.unchanged = False
        self.distances = distances

        count = 0
        for i in range(len(non_smokers)):
            distance = abs(self.state[0] - non_smokers[i][0]) +\
            abs(self.state[1] - non_smokers[i][1])
            if distance < self.distances[i]:
                self.distances[i] = distance
                count += 1
        if count == 0:
            self.unchanged = True

    def get_min(self):
        min = 1000
        for d in self.distances:
            if d < min:
                min = d
        return min

    def get_total(self):
        return sum(self.distances)

path = []
def pathfinder(x, y, grid):
    global path
    pair = []
    print(path)
    if x > len(grid[0]):
        print("x=", x)
        pathfinder(x-1, y+1, grid)
    elif y > len(grid):
        print("y=", y)
        pathfinder(x+1, y-1, grid)
    if x == len(grid[0]) and y == len(grid):
        return
    if grid[x+1][y] > grid[x][y+1]:
        pair.append(x+1)
        pair.append(y)
        path.append(pair)
        if grid[x+1][y] != None:
            pathfinder(x+1, y, grid)
    else:
        pair.append(x)
        pair.append(y+1)
        path.append(pair)
        if grid[x][y+1] != None:
            pathfinder(x, y+1, grid)

# MAIN

file = open('new_input.txt', 'r')
lines = file.readlines()
#lines = sys.stdin.readlines()
input_data = []
for line in lines:
    if line != "\n":
        line = line.strip('\n')
        line = line.split(' ')
        input_data.append((line[0], line[1]))

    else:

        grid_array = np.zeros((int(input_data[0][0]),int(input_data[0][1])))
        print(grid_array)
        smokers = input_data[1:]

        for row in range(int(input_data[0][0])):
            for col in range(int(input_data[0][1])):
                min_dist = int(input_data[0][0]) + 100 # change later.
                for smoker in smokers:
                    distance_to_smoker = abs(int(smoker[0]) - col) + abs(int(smoker[1]) - row)
                    if distance_to_smoker < min_dist:
                        min_dist = distance_to_smoker
                grid_array[row][col] = int(min_dist)

            print()
        print(grid_array)

        pathfinder(0, 0, grid_array)