
class Graph:
    def __init__(self, input_file, output_file):
        self.matrix = []
        self.no_nodes = 0
        self.no_edges = 0
        self.input_file = input_file
        self.output_file = output_file
        self.load_from_file()

    def load_from_file(self):
        file = open(self.input_file, "r")
        lines = file.readlines()
        self.no_nodes = int(lines[0])
        self.matrix = []
        for i in range(1, self.no_nodes + 1):
            line = []
            for node in lines[i].split(','):
                line.append(int(node))
            self.matrix.append(line)
        file.close()

    def save_to_file(self, path, cost):
        file = open(self.output_file, "w")
        result = str(cost) + '\n'
        for node in path:
            result += str(node + 1) + ' '
        file.writelines(result)
        file.close()
