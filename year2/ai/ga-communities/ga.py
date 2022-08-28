from random import randint

from numpy import interp

from chromosome import Chromosome

from utils import repair


# ga_params{ 'pop_size', 'no_gens', 'elite', 'mut_rate' }
# pb_params{ 'fitness', 'min', 'max', 'size' }


class GA:
    def __init__(self, ga_params, pb_params, graph):
        self.size = ga_params['pop_size']
        self.ga_params = ga_params
        self.pb_params = pb_params
        self.graph = graph
        self.population = []
        self.mating_pool = []

    def initialisation(self):
        for _ in range(0, self.ga_params['pop_size']):
            chromosome = Chromosome(self.pb_params['min'], self.pb_params['max'], self.pb_params['size'])
            repair(chromosome, self.graph)
            self.population.append(chromosome)

    def evaluation(self):
        fitness = self.pb_params['fitness']
        for chromosome in self.population:
            if chromosome.fitness == 0.0:
                chromosome.fitness = fitness(chromosome.rep, self.graph)
        self.population.sort(key=lambda x: x.fitness)

    def best_chromosome(self):
        best = self.population[0]
        for chromosome in self.population:
            if chromosome.fitness > best.fitness:
                best = chromosome
        return best

    def worst_chromosome(self):
        worst = self.population[0]
        for chromosome in self.population:
            if chromosome.fitness < worst.fitness:
                worst = chromosome
        return worst

    def selection(self):
        pos1 = randint(0, self.size - 1)
        pos2 = randint(0, self.size - 1)
        if self.population[pos1].fitness < self.population[pos2].fitness:
            return pos1
        else:
            return pos2

    def next_generation(self):
        new_pop = []
        for _ in range(self.size):
            p1 = self.population[self.selection()]
            p2 = self.population[self.selection()]
            off = p1.crossover(p2)
            off.mutation(self.graph.matrix)
            new_pop.append(off)
        self.population = new_pop
        self.evaluation()

    def next_generation_elitism(self):
        new_pop = [self.best_chromosome()]
        for _ in range(self.size - 1):
            p1 = self.population[self.selection()]
            p2 = self.population[self.selection()]
            off = p1.crossover(p2)
            off.mutation(self.graph.matrix)
            new_pop.append(off)
        self.population = new_pop
        self.evaluation()

    def next_generation_steady_state(self):
        p1 = self.population[self.selection()]
        p2 = self.population[self.selection()]
        off = p1.crossover(p2)
        off.mutation(self.graph.matrix)
        off.fitness = self.pb_params['fitness'](off.rep, self.graph)
        worst = self.worst_chromosome()
        if off.fitness < worst.fitness:
            if worst == self.population[0]:
                self.population[0] = off

    def tournament_selection(self):
        indexes = [randint(0, self.size - 1) for i in range(10)]
        c = [self.population[i] for i in indexes]
        return max(c, key=lambda x: x.fitness)

    def next_generation_with_tournament(self):
        new_pop = []
        for i in range(self.size - self.ga_params['elite'], self.size):
            new_pop.append(self.population[i])
        for _ in range(self.size - self.ga_params['elite']):
            p1 = self.tournament_selection()
            p2 = self.tournament_selection()
            off = p1.crossover(p2)
            off.mutation_at_rate(self.graph.matrix, self.ga_params['mut_rate'])
            new_pop.append(off)
        self.population = new_pop
        self.evaluation()

    def select_from_pool(self):
        pos = randint(0, len(self.mating_pool) - 1)
        return self.mating_pool[pos]

    def generate_pool(self):
        self.mating_pool = []
        max_fitness = self.best_chromosome().fitness
        min_fitness = self.worst_chromosome().fitness
        for i in range(len(self.population)):
            fitness = interp(self.population[i].fitness, [min_fitness, max_fitness], [0, 1])
            number = int(fitness * 100)
            for j in range(number):
                self.mating_pool.append(self.population[i])

    def next_generation_with_pool(self):
        self.generate_pool()
        new_population = []
        for i in range(len(self.population) - self.ga_params['elite'], len(self.population)):
            new_population.append(self.population[i])
        for _ in range(self.size - self.ga_params['elite']):
            p1 = self.select_from_pool()
            p2 = self.select_from_pool()
            off = p1.crossover(p2)
            off.mutation_at_rate(self.graph.matrix, self.ga_params['mut_rate'])
            new_population.append(off)
        self.population = new_population
        self.evaluation()
