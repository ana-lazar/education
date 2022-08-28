from math import sqrt

import random
import matplotlib.pyplot as plt
import numpy as np


class MyMBGDRegression:
    def __init__(self):
        self.intercept_ = 0.0
        self.coef_ = []

    # simple batch MB GD algorithm
    def fit(self, inputs, outputs, learning_rate=0.001, no_epochs=1000):
        self.coef_ = [0.0 for i in range(len(inputs[0]))]
        for epoch in range(no_epochs):
            for batch in self.random_mini_batches(inputs, outputs):
                locals = self.fit_batch(batch[0], batch[1])
                avg = sum(locals) / len(locals)
                for j in range(len(inputs[0])):
                    self.coef_[j] = self.coef_[j] - avg * learning_rate
                self.intercept_ = self.intercept_ - avg * learning_rate
            error = self.mean_root_square_error(outputs, self.predict(inputs))
            # print("epoch {} error: {}".format(epoch, error))

    def random_mini_batches(self, inputs, outputs, batch_size=1):
        batches = []
        batch_size = 16
        indexes = [i for i in range(len(inputs))]
        for i in range(int(len(inputs) / batch_size)):
            if len(indexes) < batch_size:
                batch = indexes
            else:
                batch = np.random.choice(indexes, batch_size, replace=False)
            indexes = [i for i in indexes if not i in batch]
            batch_inputs = [inputs[i] for i in batch]
            batch_outputs = [outputs[i] for i in batch]
            batches.append([batch_inputs, batch_outputs])
        return batches

    def fit_batch(self, inputs, outputs):
        locals = [0.0 for i in range(len(inputs[0]) + 1)]
        for i in range(len(inputs)):
            computed_output = self.eval(inputs[i])
            error = computed_output - outputs[i]
            for j in range(len(inputs[0])):
                locals[j] += (1 / len(inputs)) * error * inputs[i][j]
            locals[-1] += (1 / len(inputs)) * error
        return locals

    def mean_root_square_error(self, real=None, computed=None):
        # computes the mean root square error value for the given data
        return sum([(computed[i] - real[i]) ** 2 for i in range(len(computed))]) / len(computed)

    def eval(self, xi):
        yi = self.intercept_
        for j in range(len(xi)):
            yi += self.coef_[j] * xi[j]
        return yi

    def predict(self, x):
        y_computed = [self.eval(xi) for xi in x]
        return y_computed

