import collections
from random import uniform, choice


def generate_new_value(a, b):
    return int(uniform(a, b))


def modularity(communities, graph):
    no_nodes = graph.no_nodes
    no_edges = graph.no_edges
    matrix = graph.matrix
    degrees = graph.degrees
    M = 2 * no_edges
    Q = 0.0
    for i in range(no_nodes):
        for j in range(no_nodes):
            if communities[i] == communities[j]:
                Q += (matrix[i][j] - degrees[i] * degrees[j] / M)
    return Q / M


def node_communities(chromosome):
    def dfs(node):
        communities[node - 1] = count
        visited.add(node)
        for n in neighbour[node]:
            if n not in visited:
                dfs(n)

    edges = []
    for i in range(len(chromosome)):
        edges.append([])
        edges[-1] = [i, chromosome[i]]
    neighbour = collections.defaultdict(list)
    for e in edges:
        u, v = e[0], e[1]
        neighbour[u].append(v)
        neighbour[v].append(u)
    visited = set()
    count = 0
    communities = [0 for _ in range(len(chromosome))]
    for i in range(1, len(chromosome) + 1):
        if i not in visited:
            count += 1
            dfs(i)
    return communities


def get_communities(nodes):
    result = {}
    for i in range(0, len(nodes)):
        if nodes[i] in result:
            result[nodes[i]].append(i)
        else:
            result[nodes[i]] = [i]
    return result


def random_neighbour(node, matrix):
    neighbours = []
    row = matrix[node]
    for i in range(len(row)):
        if row[i] == 1:
            neighbours.append(i)
    selected = choice(neighbours)
    return selected


def repair(chromosome, graph):
    matrix = graph.matrix
    for i in range(len(chromosome.rep)):
        if matrix[i][chromosome.rep[i]] == 0:
            chromosome.rep[i] = random_neighbour(i, matrix)
