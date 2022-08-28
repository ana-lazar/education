import matplotlib.pyplot as plt

from ant import Ant


class Aco:
    def __init__(self, aco_params):
        self.steps = aco_params['steps']
        self.colony_size = aco_params['col_size']
        self.rho = aco_params['rho']
        self.phe_weight = aco_params['phe_weight']
        alpha = aco_params['alpha']
        beta = aco_params['beta']
        self.num_nodes = aco_params['num_nodes']
        self.edges = aco_params['edges']
        self.init_phe = aco_params['init_phe']
        self.colony = [Ant(alpha, beta, self.num_nodes, self.edges) for _ in range(self.colony_size)]
        self.best_tour = None
        self.best_distance = float("inf")

    def add_pheromone(self, tour, distance):
        pheromone_to_add = self.phe_weight / distance
        for i in range(self.num_nodes):
            self.edges[tour[i]][tour[(i + 1) % self.num_nodes]].pheromone += pheromone_to_add

    def update_local(self, tour):
        for i in range(self.num_nodes):
            self.edges[tour[i]][tour[(i + 1) % self.num_nodes]].pheromone += self.rho * self.init_phe

    def solve(self):
        progress = []
        for step in range(self.steps):
            for ant in self.colony:
                ant.find_tour()
                ant.calculate_distance()
                if ant.distance < self.best_distance:
                    self.best_tour = ant.tour
                    self.best_distance = ant.distance
                self.update_local(ant.tour)
            for i in range(self.num_nodes):
                for j in range(self.num_nodes):
                    if self.edges[i][j] is not None:
                        self.edges[i][j].pheromone *= (1.0 - self.rho)
            self.add_pheromone(self.best_tour, self.best_distance)
            progress.append(self.best_distance)
            print("Best solution at step " + str(step) + " is " + str(self.best_tour) + " with distance " + str(
                self.best_distance))

        plt.plot(progress)
        plt.ylabel('Distance')
        plt.xlabel('Generation')
        plt.savefig('figure')
        plt.show()











