from ga import GA

import matplotlib.pyplot as plot
import networkx as nx
import numpy as np


class Service:
    def __init__(self, graph):
        self.graph = graph

    def find_communities(self, fitness, pop_size, no_gens, mut_rate, elite):
        # self.draw_network()

        ga_params = { 'pop_size': pop_size, 'no_gens': no_gens, 'elite': elite, 'mut_rate': mut_rate }
        pb_params = { 'fitness': fitness, 'min': 0, 'max': self.graph.no_nodes, 'size': self.graph.no_nodes }

        best_fitness = []
        generations = []

        ga = GA(ga_params, pb_params, self.graph)

        ga.initialisation()
        ga.evaluation()
        best_chromosome = ga.best_chromosome()

        overall_best = best_chromosome

        for generation in range(no_gens):
            generations.append(generation)
            # ga.next_generation_elitism()
            ga.next_generation_scx_elitism()
            # ga.survivor_selection()
            # ga.next_generation_steady_state()
            # ga.next_generation()
            best_chromosome = ga.best_chromosome()
            best_fitness.append(best_chromosome.fitness)

            print('Best solution in generation ' + str(generation) + ', fitness: f(x) = ' + str(
                best_chromosome.fitness) + ', best chromosome is: x = ' + str(
                best_chromosome.rep))

            if best_chromosome.fitness > overall_best.fitness:
                overall_best = best_chromosome

        print("Final result: ")
        print('Generation ' + str(no_gens) + ', fitness: f(x) = ' + str(
            best_chromosome.fitness) + ', best chromosome is: x = ' + str(
            best_chromosome.rep))

        self.graph.save_to_file(best_chromosome.rep, best_chromosome.fitness)

        # plot.ylabel('Fitness')
        # plot.plot(best_fitness)
        # plot.xlabel('Generation')
        # plot.show()

    def draw_network(self, color=None):
        matrix = np.matrix(self.graph.matrix)
        graph = nx.from_numpy_matrix(matrix)
        pos = nx.spring_layout(graph)
        plot.figure(figsize=(4, 4))
        if color is None:
            nx.draw(graph, with_labels=True)
        else:
            nx.draw_networkx_nodes(graph, pos, node_size=200, cmap=plot.cm.RdYlBu, node_color=color)
            nx.draw_networkx_edges(graph, pos, alpha=0.3)
        plot.show()

