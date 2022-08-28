from random import randint
from chromosome import Chromosome


class GA:
    def __init__(self, ga_params, pb_params, graph):
        self.size = ga_params['pop_size']
        self.ga_params = ga_params
        self.pb_params = pb_params
        self.graph = graph
        self.population = []
        self.mating_pool = []
        self.selection_probabilities = []

    def initialisation(self):
        for _ in range(0, self.ga_params['pop_size']):
            chromosome = Chromosome(self.pb_params['min'], self.pb_params['max'], self.pb_params['size'])
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
            if chromosome.fitness < best.fitness:
                best = chromosome
        return best

    def worst_chromosome(self):
        worst = self.population[0]
        for chromosome in self.population:
            if chromosome.fitness > worst.fitness:
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
            p2 = self.best_chromosome()
            off = p1.crossover(p2)
            off.mutation()
            new_pop.append(off)
        self.population = new_pop
        self.evaluation()

    def next_generation_scx_elitism(self):
        new_pop = [self.best_chromosome()]
        for _ in range(self.ga_params['elite']):
            p1 = self.population[self.selection()]
            # p2 = self.worst_chromosome()
            p2 = self.population[self.selection()]
            off = p1.SCX_crossover(p2, self.graph)
            off.mutation_rate()
            new_pop.append(off)
        for _ in range(self.ga_params['elite'], self.size - 1):
            p1 = self.population[self.selection()]
            p2 = self.best_chromosome()
            off = p1.SCX_crossover(p2, self.graph)
            off.mutation_rate()
            new_pop.append(off)
        self.population = new_pop
        self.evaluation()

    def next_generation_elitism(self):
        new_pop = [self.best_chromosome()]
        for _ in range(self.size):
            p1 = self.population[self.selection()]
            p2 = self.population[self.selection()]
            off = p1.crossover(p2)
            # off.mutation_rate(self.ga_params['mut_rate'])
            off.mutation_rate()
            new_pop.append(off)
        self.population = new_pop
        self.evaluation()

    def survivor_selection(self):
        new_pop = []
        for _ in range(self.size):
            p1 = self.population[self.selection()]
            p2 = self.population[self.selection()]
            off = p1.SCX_crossover(p2, self.graph)
            off.mutation()
            new_pop.append(off)
        for c in self.population:
            new_pop.append(c)
        new_pop.sort(key=lambda x: x.fitness)
        self.population = new_pop[self.size:]
        self.evaluation()
