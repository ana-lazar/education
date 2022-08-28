from math import sqrt, log
import numpy as np


def mean_root_square_error(real = None, computed = None):
    return sqrt(sum((r - c) ** 2 for r, c in zip(real, computed)) / len(real))


def mean_absolute_error(real = None, computed = None):
    return sum(abs(r - c) for r, c in zip(real, computed)) / len(real)


def sigmoid(val):
    return [1 / (1 + np.exp(-i)) for i in val]


def labels_to_probabilities(real, labels):
    dic = { }
    for i in range(len(labels)):
        dic[labels[i]] = i
    p = []
    for i in range(len(real)):
        v = [0.0 for i in labels]
        v[dic[real[i]]] = 1.0
        p.append(v)
    return p


def cross_entropy_loss(real, computed):
    sum = 0
    for i in range(len(computed)):
        for j in range(len(computed[i])):
            sum += -real[i][j] * log(computed[i][j] + 1e-15)
    return sum / len(computed)


def read_single_target(filename):
    file = open(filename)
    lines = file.readlines()
    real = lines[0].split(' ')
    real = [float(i) for i in real]
    computed = lines[1].split(' ')
    computed = [float(i) for i in computed]
    return real, computed


def read_multi_target(filename):
    file = open(filename)
    lines = file.readlines()
    size = int(lines[0])
    real = []
    for i in range(1, size + 1):
        line = lines[i].split(' ')
        line = [float(j) for j in line]
        real.append(line)
    computed = []
    for i in range(1, size + 1):
        line = lines[i + size].split(' ')
        line = [float(j) for j in line]
        computed.append(line)
    return real, computed


def read_multi_label(filename):
    file = open(filename)
    lines = file.readlines()
    labels = lines[0].split(' ')
    labels = [labels[i].strip() for i in range(len(labels))]
    size = int(lines[1])
    real = []
    for i in range(2, size + 2):
        line = lines[i].split(' ')
        line = [float(j) for j in line]
        real.append(line)
    computed = []
    for i in range(2, size + 2):
        line = lines[i + size].split(' ')
        line = [float(j) for j in line]
        computed.append(line)
    return labels, real, computed


def read_multi_class(filename):
    file = open(filename)
    lines = file.readlines()
    labels = lines[0].split(' ')
    labels = [labels[i].strip() for i in range(len(labels))]
    real = lines[1].split(' ')
    real = [real[i].strip() for i in range(len(real))]
    computed = lines[2].split(' ')
    computed = [computed[i].strip() for i in range(len(computed))]
    return labels, real, computed


def read_multi_prob(filename):
    file = open(filename)
    lines = file.readlines()
    labels = lines[0].split(' ')
    labels = [labels[i].strip() for i in range(len(labels))]
    real = lines[1].split(' ')
    real = [real[i].strip() for i in range(len(real))]
    num = int(lines[2])
    computed = []
    for i in range(num):
        val = lines[i + 3].split(' ')
        val = [float(i) for i in val]
        computed.append(val)
    return labels, real, computed

