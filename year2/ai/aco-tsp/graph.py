from city import City
from edge import Edge


def write_to_file(file_name, solution, distance):
    file = open(file_name, "w")
    result = ''
    result += str(distance) + "\n"
    for i in solution:
        result += str(i + 1) + " "
    file.writelines(result)
    file.close()


class Graph:
    def __init__(self, file_name):
        self.graph_params = {}
        self.aco_params = {}
        self.load_from_file(file_name)

    def load_from_file(self, file_name):
        file = open(file_name, "r")
        no_nodes = int(file.readline())
        self.graph_params['num_nodes'] = no_nodes
        matrix = []
        for i in range(no_nodes):
            matrix.append([])
            line = file.readline()
            elements = line.split(",")
            for j in range(no_nodes):
                matrix[-1].append(int(elements[j]))
        init_phe = float(file.readline())
        edges = [[None] * no_nodes for _ in range(no_nodes)]
        for i in range(no_nodes):
            for j in range(i + 1, no_nodes):
                edges[i][j] = edges[j][i] = Edge(i, j, init_phe, matrix[i][j])
        self.graph_params['edges'] = edges
        size = int(file.readline())
        gen = int(file.readline())
        rho = float(file.readline())
        phe_weight = float(file.readline())
        alpha = float(file.readline())
        beta = float(file.readline())
        self.aco_params = { 'col_size': size, 'gen': gen, 'init_phe': init_phe, 'rho': rho, 'phe_weight': phe_weight, 'alpha': alpha, 'beta': beta }
        file.close()

    def load_from_file_e(self, file_name):
        f = open(file_name, "r")
        n = int(f.readline())
        self.graph_params['num_nodes'] = n
        cities = []
        for i in range(n):
            line = f.readline()
            elements = line.split(" ")
            c = City(i, float(elements[1]), float(elements[2]))
            cities.append(c)
        initial_pheromone = float(f.readline())
        edges = [[None] * n for _ in range(n)]
        for i in range(n):
            for j in range(i + 1, n):
                edges[i][j] = edges[j][i] = Edge(cities[i], cities[j], initial_pheromone, None)
        self.graph_params['edges'] = edges
        size = int(f.readline())
        gen = int(f.readline())
        rho = float(f.readline())
        phe_dep = float(f.readline())
        alpha = float(f.readline())
        beta = float(f.readline())
        self.aco_params = {'col_size': size, 'gen': gen, 'init_phe': initial_pheromone,
                             'rho': rho, 'phe_weight': phe_dep,
                             'alpha': alpha, 'beta': beta
                             }
        f.close()

    def get_aco_params(self):
        return self.aco_params
