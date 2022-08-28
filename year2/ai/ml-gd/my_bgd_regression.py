import random
from math import sqrt
import matplotlib.pyplot as plt


class MyBGDRegression:
    def __init__(self):
        self.intercept_ = 0.0
        self.coef_ = []

    # simple batch GD algorithm
    def fit(self, inputs, outputs, learning_rate=0.001, no_epochs=1000):
        self.coef_ = [0.0 for i in range(len(inputs[0]))]
        for epoch in range(no_epochs):
            locals = [0.0 for i in range(len(inputs[0]) + 1)]
            for i in range(len(inputs)):
                computed_output = self.eval(inputs[i])
                error = computed_output - outputs[i]
                for j in range(len(inputs[0])):
                    locals[j] += (1 / len(inputs)) * error * inputs[i][j]
                locals[-1] += (1 / len(inputs)) * error
            for j in range(len(inputs[0])):
                self.coef_[j] = self.coef_[j] - locals[j] * learning_rate
            self.intercept_ = self.intercept_ - locals[-1] * learning_rate
            error = self.mean_root_square_error(outputs, self.predict(inputs))
            # print("epoch {} error: {}".format(epoch, error))

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
