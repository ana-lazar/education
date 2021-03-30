from graph import Graph

# Abstract class for the Repository data type
class Repository:
    # Constructor for the Repository object
    # input_file - a String containing the input file name
    # output_file - a String containing the output file name
    def __init__(self, input_file, output_file):
        self.source = -1
        self.destination = -1
        self.graph = Graph(0, [])
        self.input_file = input_file
        self.output_file = output_file

    # Loads all the information from the file
    def load_from_file(self):
        file = open(self.input_file, "r")
        lines = file.readlines()
        nodes = int(lines[0])
        matrix = []
        for i in range(1, nodes + 1):
            line = []
            for node in lines[i].split(','):
                line.append(int(node))
            matrix.append(line)
        self.source = int(lines[nodes + 1])
        self.destination = int(lines[nodes + 2])
        self.graph = Graph(nodes, matrix)

    # Saves the information to the output file
    # tsp, md - pairs of an Integer number and a List of integers
    def save_to_file(self, tsp, md):
        result = str(self.graph.nodes) + '\n'
        path, cost = tsp
        for node in path[:-1]:
            result += str(node) + ','
        result += str(path[-1]) + '\n'
        result += str(cost) + '\n'
        path, cost = md
        result += str(len(path)) + '\n'
        for node in path[:-1]:
            result += str(node) + ','
        result += str(path[-1]) + '\n'
        result += str(cost)
        file = open("graph_out.txt", 'w')
        file.writelines(result)
        file.close()

    # Runs the travelling salesman path algorithm
    def tsp(self):
        path, cost = self.graph.nearest_neighbour(0, -1)
        path = [i + 1 for i in path]
        path = path[:-1]
        return path, cost

    # Runs the minimum distance path algorithm
    def md(self):
        source_path, source_cost = self.graph.nearest_neighbour(self.source - 1, self.destination - 1)
        dest_path, dest_cost = self.graph.nearest_neighbour(self.destination - 1, self.source - 1)
        if source_cost < dest_cost:
            source_path = [i + 1 for i in source_path]
            source_path = source_path[::-1]
            return source_path, source_cost
        dest_path = [i + 1 for i in dest_path]
        dest_path = dest_path[::-1]
        return dest_path, dest_cost
