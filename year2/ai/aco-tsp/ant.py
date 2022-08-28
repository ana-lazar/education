import random

import numpy.random as npr


class Ant:
    def __init__(self, alpha, beta, num_nodes, edges):
        self.alpha = alpha
        self.beta = beta
        self.num_nodes = num_nodes
        self.edges = edges
        self.tour = None
        self.distance = 0.0

    def find_tour(self):
        self.tour = [random.randint(0, self.num_nodes - 1)]
        while len(self.tour) < self.num_nodes:
            node = self._select_node()
            self.tour.append(node)
        return self.tour

    def calculate_distance(self):
        self.distance = 0.0
        for i in range(self.num_nodes):
            self.distance += self.edges[self.tour[i]][self.tour[(i + 1) % self.num_nodes]].weight
        return self.distance

    def _select_node(self):
        unvisited_nodes = [node for node in range(self.num_nodes) if node not in self.tour]
        denominator = 0
        for i in unvisited_nodes:
            denominator += (self.edges[self.tour[-1]][i].pheromone ** self.alpha) * (
                    (1 / self.edges[self.tour[-1]][i].weight)
                    ** self.beta)
        probabilities = [0 for i in range(self.num_nodes)]
        for i in unvisited_nodes:
            probabilities[i] = ((self.edges[self.tour[-1]][i].pheromone ** self.alpha) * (
                    (1 / self.edges[self.tour[-1]][i].weight) ** self.beta)) / denominator
        return npr.choice([i for i in range(self.num_nodes)], p=probabilities)