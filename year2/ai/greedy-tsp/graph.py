
# Abstract class for the Graph data type
class Graph:
    # Constructor for the Graph object
    # nodes - Integer number
    # matrix - an Integer n x n matrix (adjacent)
    def __init__(self, nodes, matrix):
        self.nodes = nodes
        self.matrix = matrix

    # Computes the shortest path from a node to another in the current graph
    # source, destination - Integer numbers
    def nearest_neighbour(self, source, destination):
        path = [source]
        weight = 0
        while len(path) < self.nodes:
            min = 1000000
            current = path[-1]
            next = -1
            for node in range(self.nodes):
                if (node not in path) and (min > self.matrix[current][node]):
                    min = self.matrix[current][node]
                    next = node
            path.append(next)
            weight += min
            if next == destination:
                return path, weight
        weight += self.matrix[path[-1]][source]
        path.append(source)
        return path, weight
