from aco import Aco
from graph import write_to_file


class Service:
    def __init__(self, repo):
        self.graph = repo

    def run_aco(self):
        graph = self.graph.graph_params
        params = self.graph.get_aco_params()

        aco_params = { 'edges': graph['edges'], 'num_nodes': graph['num_nodes'], 'col_size': params['col_size'],
                      'steps': params['gen'], 'init_phe': params['init_phe'], 'rho': params['rho'],
                      'phe_weight': params['phe_weight'], 'alpha': params['alpha'], 'beta': params['beta'] }

        aco = Aco(aco_params)
        aco.solve()

        sol = [i + 1 for i in aco.best_tour]
        sol.append(sol[0])
        print(sol)
        print(aco.best_distance)

        write_to_file("data/graph_out.txt", aco.best_tour, aco.best_distance)
