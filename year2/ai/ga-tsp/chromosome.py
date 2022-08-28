from random import randint, uniform
from utils import generate_random_permutation


class Chromosome:
    def __init__(self, min_v, max_v, size):
        self.min = min_v
        self.max = max_v
        self.size = size
        self.rep = generate_random_permutation(size)
        self.fitness = 0.0

    def crossover(self, other):
        pos1 = randint(-1, self.size - 1)
        pos2 = randint(-1, self.size - 1)
        if pos2 < pos1:
            pos1, pos2 = pos2, pos1
        k = 0
        new_rep = self.rep[pos1: pos2]
        for el in other.rep[pos2:] + other.rep[:pos2]:
            if el not in new_rep:
                if len(new_rep) < self.size - pos1:
                    new_rep.append(el)
                else:
                    new_rep.insert(k, el)
                    k += 1
        offspring = Chromosome(self.min, self.max, self.size)
        offspring.rep = new_rep
        return offspring

    # SCX crossover
    def SCX_crossover(self, other, graph):
        current_node = self.rep[0]
        new_rep = [current_node]
        for i in range(self.size - 1):
            index = self.rep.index(current_node)
            first_legitimate = -1
            for j in range(index + 1, self.size):
                if self.rep[j] not in new_rep:
                    first_legitimate = self.rep[j]
                    break
            if first_legitimate == -1:
                for j in range(index):
                    if self.rep[j] not in new_rep:
                        first_legitimate = self.rep[j]
                        break
            index = other.rep.index(current_node)
            second_legitimate = -1
            for j in range(index + 1, other.size):
                if other.rep[j] not in new_rep:
                    second_legitimate = other.rep[j]
                    break
            if second_legitimate == -1:
                for j in range(index):
                    if other.rep[j] not in new_rep:
                        second_legitimate = other.rep[j]
                        break
            if graph.matrix[current_node][first_legitimate] < graph.matrix[current_node][second_legitimate]:
                new_rep.append(first_legitimate)
                current_node = first_legitimate
            else:
                new_rep.append(second_legitimate)
                current_node = second_legitimate
        offspring = Chromosome(self.min, self.max, self.size)
        offspring.rep = new_rep
        return offspring

    def mutation(self):
        pos1 = randint(0, self.size - 1)
        pos2 = randint(0, self.size - 1)
        if pos2 < pos1:
            pos1, pos2 = pos2, pos1
        el = self.rep[pos2]
        del self.rep[pos2]
        self.rep.insert(pos1 + 1, el)

    def invert(self, i, j):
        while i < j:
            self.rep[i], self.rep[j] = self.rep[j], self.rep[i]
            i = i + 1
            j = j - 1

    # RSM mutation
    def mutation_rate(self):
        gene1 = randint(1, self.size - 1)
        gene2 = randint(1, self.size - 1)
        start_gene = min(gene1, gene2)
        end_gene = max(gene1, gene2)
        self.invert(start_gene, end_gene)

    def __str__(self):
        return 'Chromosome: ' + str(self.rep) + ' has fitness: ' + str(self.fitness)
