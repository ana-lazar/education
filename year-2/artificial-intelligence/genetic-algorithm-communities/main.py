# Inteligenta Artificiala
# Lazar Ana - Patricia
# Laborator 3 - algoritmi evolutivi


from graph import Graph
from service import Service
from utils import modularity


def run():
    graph = Graph("files/lesmis.gml", "files/graph_out.txt")
    service = Service(graph)
    service.find_communities(fitness=modularity, pop_size=200, no_gens=250, mut_rate=0.01, elite=10)


if __name__ == '__main__':
    run()
