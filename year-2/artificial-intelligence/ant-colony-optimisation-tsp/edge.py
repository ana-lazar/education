class Edge:
    def __init__(self, x, y, init_phe, d):
        if d is None:
            self.weight = x.distance(y)
            self.city_x = x.i
            self.city_y = y.i
        else:
            self.weight = d
            self.city_x = x
            self.city_y = y
        self.pheromone = init_phe

    def __str__(self):
        return str(self.city_x) + " " + str(self.city_y) + " dist: " + str(self.weight)
