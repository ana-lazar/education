import numpy as np


class MySGDRegression:
    def __init__(self):
        self.intercept_ = 0.0
        self.coef_ = []

    # simple stochastic GD
    def fit(self, inputs, outputs, learning_rate=0.001, noEpochs=1000):
        self.coef_ = [0.0 for _ in range(len(inputs[0]) + 1)]
        # self.coef_ = [random.random() for _ in range(len(x[0]) + 1)]
        for epoch in range(noEpochs):
            # np.random.shuffle(inputs)
            for i in range(len(inputs)): # for each sample from the training data
                y_computed = self.eval(inputs[i])  # estimate the output
                crt_error = y_computed - outputs[i]  # compute the error for the current sample
                for j in range(0, len(inputs[0])):  # update the coefficients
                    self.coef_[j] = self.coef_[j] - learning_rate * crt_error * inputs[i][j]
                self.coef_[len(inputs[0])] = self.coef_[len(inputs[0])] - learning_rate * crt_error * 1
        self.intercept_ = self.coef_[-1]
        self.coef_ = self.coef_[:-1]

    def eval(self, xi):
        yi = self.coef_[-1]
        for j in range(len(xi)):
            yi += self.coef_[j] * xi[j]
        return yi

    def predict(self, x):
        y_computed = [self.eval(xi) for xi in x]
        return y_computed
