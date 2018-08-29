
from copy import deepcopy
import sys


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


# MAIN

lines = sys.stdin.readlines()
grid = []
for line in lines:
    if line != "\n":
        line = line.strip('\n')
        line = line.split(' ')
        grid.append((line[0], line[1]))

    else:
        end_state = (int(grid[0][1]) - 1, int(grid[0][0]) - 1)
        all_nodes = []
        non_smokers = []
        results = (0, 0)

        for i in range(1, len(grid)):
            non_smokers.append((int(grid[i][0]), int(grid[i][1])))
        root = Node((0, 0), [1000]*len(non_smokers), non_smokers)

        all_nodes.append(root)
        while all_nodes:
            node = all_nodes.pop(0)
            node_min = node.get_min()
            node_total = node.get_total()

            if node.unchanged:
                if node_min > results[0] or node_min == results[0] and node_total > results[1]:
                    results = (node_min, node_total)

            elif node_min != 0:
                if node.state[0] < end_state[0]:
                    side_node = Node((node.state[0] + 1, node.state[1]),\
                    node.distances.copy(), non_smokers)
                    all_nodes.insert(0, side_node)
                if node.state[1] < end_state[1]:
                    down_node = Node((node.state[0], node.state[1] + 1),\
                    node.distances.copy(), non_smokers)
                    all_nodes.insert(0, down_node)
        
        print("min %d, total %d" % results)

        grid = []
