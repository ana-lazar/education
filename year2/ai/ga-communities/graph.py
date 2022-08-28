import networkx as nx


class Graph:
    def __init__(self, input_file, output_file):
        self.matrix = []
        self.no_nodes = 0
        self.no_edges = 0
        self.degrees = []
        self.input_file = input_file
        self.output_file = output_file
        if input_file.split('.')[1] == "gml":
            self.load_from_gml()
        else:
            self.load_from_file()

    def load_from_file(self):
        file = open(self.input_file, "r")
        lines = file.readlines()
        self.no_nodes = int(lines[0])
        self.matrix = []
        for i in range(1, self.no_nodes + 1):
            line = []
            for node in lines[i].split(' '):
                line.append(int(node))
            self.matrix.append(line)
        for i in range(0, self.no_nodes):
            degree = 0
            for j in range(0, self.no_nodes):
                if self.matrix[i][j] != 0:
                    degree += 1
                if i > j:
                    self.no_edges += 1
            self.degrees.append(degree)
        file.close()

    def load_from_gml(self):
        graph = nx.read_gml(self.input_file, label='id')
        self.no_nodes = len(graph.nodes)
        self.no_edges = len(graph.edges)
        for i in range(self.no_nodes):
            line = []
            for j in range(self.no_nodes):
                line.append(0)
            self.matrix.append(line)
        for edge in graph.edges:
            self.matrix[edge[0] - 1][edge[1] - 1] = 1
            self.matrix[edge[1] - 1][edge[0] - 1] = 1
        for i in range(self.no_nodes):
            if (i in graph.adj):
                self.degrees.append(len(graph.adj[i]))
            else:
                self.degrees.append(0)

    def save_to_file(self, communities, best_fitness, best_community_no):
        file = open(self.output_file, "w")
        result = str(best_community_no[-1]) + "\n"
        for node in communities.values():
            for value in node:
                result += str(value) + " "
            result += '\n'
        for fitness in best_fitness:
            result += str(fitness) + " "
        result += "\n"
        for community_no in best_community_no:
            result += str(community_no) + " "
        file.writelines(result)
        file.close()
