from random import randint, random

from utils import generate_new_value, random_neighbour


class Chromosome:
    def __init__(self, min_v, max_v, size):
        self.min = min_v
        self.max = max_v
        self.size = size
        self.rep = [generate_new_value(min_v, max_v) for _ in range(size)]
        self.fitness = 0.0

    def crossover(self, other):
        mask = [randint(0, 1) for _ in range(self.size)]
        off_rep = []
        for i in range(len(mask)):
            if mask[i] == 1:
                off_rep.append(self.rep[i])
            else:
                off_rep.append(other.rep[i])
        offspring = Chromosome(other.min, other.max, other.size)
        offspring.rep = off_rep
        return offspring

    def mutation(self, matrix):
        pos = randint(0, len(self.rep) - 1)
        self.rep[pos] = random_neighbour(pos, matrix)

    def mutation_at_rate(self, matrix, rate):
        for gene in range(len(self.rep)):
            if random() < rate:
                self.rep[gene] = random_neighbour(gene, matrix)

    def __str__(self):
        return 'Chromosome: ' + str(self.rep) + ' has fitness: ' + str(self.fitness)
