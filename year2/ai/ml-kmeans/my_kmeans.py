import numpy as np
import random
from math import sqrt


def euclidean_distance(x, y):
    sums = [(x[i] - y[i]) ** 2 for i in range(len(x))]
    return sqrt(sum(sums))


class MyKMeans:
    def __init__(self, n_clusters=5, iterations=100):
        print('MyKMeans')
        self.n_clusters = n_clusters
        self.iterations = iterations
        self.clusters = []
        self.centroids = []

    def init_centroids(self, inputs):
        indexes = [i for i in range(len(inputs))]
        centroids = np.random.choice(indexes, self.n_clusters, replace=False)
        self.centroids = [inputs[i] for i in centroids]

    def pick_cluster(self, x):
        distances = [euclidean_distance(x, self.centroids[j]) for j in range(len(self.centroids))]
        return distances.index(min(distances))

    def fit_predict(self, inputs):
        self.init_centroids(inputs)
        for iteration in range(self.iterations):
            clusters = []
            c = [self.pick_cluster(x) for x in inputs]
            for i in range(len(self.centroids)):
                clusters.append([inputs[j] for j in range(len(c)) if i == c[j]])
            computed_centroids = []
            for i in range(len(self.centroids)):
                s = [0.0 for i in range(len(inputs[0]))]
                for j in range(len(clusters[i])):
                    for k in range(len(clusters[i][0])):
                        s[k] += clusters[i][j][k]
                s = [s[j] / len(clusters[i]) for j in range(len(inputs[0]))]
                computed_centroids.append(s)
            stop = True
            for i in range(len(computed_centroids)):
                if computed_centroids[i] != self.centroids[i]:
                    stop = False
                    break
            if stop:
                break
            self.centroids = computed_centroids
        return self.predict(inputs)

    def predict(self, inputs):
        return [self.pick_cluster(i) for i in inputs]
