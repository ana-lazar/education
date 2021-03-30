from random import uniform

import matplotlib.pyplot as plot
import networkx as nx
import numpy as np

from ga import GA
from utils import node_communities, get_communities


def generate_value(lim1, lim2):
    return int(uniform(lim1, lim2))


class Service:
    def __init__(self, graph):
        self.graph = graph

    def find_communities(self, fitness, pop_size, no_gens, mut_rate, elite):
        # self.draw_network()

        ga_params = { 'pop_size': pop_size, 'no_gens': no_gens, 'elite': elite, 'mut_rate': mut_rate }
        pb_params = { 'fitness': fitness, 'min': 0, 'max': self.graph.no_nodes, 'size': self.graph.no_nodes }

        best_fitness = []
        best_community_no = []
        generations = []

        ga = GA(ga_params, pb_params, self.graph)

        ga.initialisation()
        ga.evaluation()
        best_chromosome = ga.best_chromosome()

        overall_best = best_chromosome

        for generation in range(no_gens):
            generations.append(generation)
            ga.next_generation_with_pool()
            # ga.next_generation_with_tournament()
            # ga.next_generation_elitism()
            # ga.next_generation_steady_state()
            # ga.next_generation()
            best_chromosome = ga.best_chromosome()
            communities = node_communities(best_chromosome.rep)
            best_community_no.append(max(communities))
            best_fitness.append(best_chromosome.fitness)

            # communities = components(bestChromo.repres)

            print('Best solution in generation no ' + str(generation) + ' has: communities: ' + str(
                best_community_no[-1]) + ', fitness: f(x) = ' + str(
                best_chromosome.fitness) + ', best chromosome is: x = ' + str(
                best_chromosome.rep))

            if best_chromosome.fitness > overall_best.fitness:
                overall_best = best_chromosome

        print("\nFinal result:")
        print('Generation no ' + str(no_gens) + ' has: communities: ' + str(best_community_no[-1]) + ', fitness: f(x) = ' + str(
            best_chromosome.fitness) + ', best chromosome is: x = ' + str(
            best_chromosome.rep))

        communities = node_communities(best_chromosome.rep)
        print("Community number per node where position is node")
        print(communities)
        # print("Communities:")
        # for commune in get_communities(communities).values():
        #     print(commune)
        print("Number of communities per generation:")
        print(best_community_no)
        print("Fitness function per generation:")
        print(best_fitness)

        self.graph.save_to_file(get_communities(communities), best_fitness, best_community_no)

        plot.ylabel('Fitness')
        plot.plot(best_fitness)
        plot.xlabel('Generation')
        plot.show()

        # self.draw_network(color=best_chromosome.rep)

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

