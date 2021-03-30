import random
from random import choice


def generate_random_permutation(size):
    permutation = [i for i in range(size)]
    random.shuffle(permutation)
    return permutation


def compute_path(path, graph):
    cost = 0
    matrix = graph.matrix
    for k in range(len(path) - 1):
        i, j = path[k], path[k + 1]
        cost += matrix[i][j]
    cost += matrix[path[0]][path[-1]]
    return cost


def repair(chromosome):
    missing = [i for i in range(chromosome.size)]
    for i in chromosome.rep:
        missing.remove(chromosome.rep[i])
    for i in chromosome.rep:
        if chromosome.rep[i] in chromosome.rep[i + 1:]:
            old = chromosome.rep[i]
            chromosome.rep[i] = choice(missing)
            missing.remove(old)
