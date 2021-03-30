from graph import Graph
from service import Service
from utils import compute_path


def run():
    graph = Graph("data/hard_tsp.txt", "data/out.txt")
    service = Service(graph)
    service.find_communities(fitness=compute_path, pop_size=100, no_gens=200, mut_rate=0.5, elite=10)


if __name__ == '__main__':
    run()
