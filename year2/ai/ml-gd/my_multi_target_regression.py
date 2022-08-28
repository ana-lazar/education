import random
import matplotlib.pyplot as plt

from cmath import sqrt


class MyMultiTargetGDRegression:
    def __init__(self):
        self.coef_ = []
        self.intercept_ = []

    def initialize_matrix(self, lx, ly):
        intercept = []
        coef = []
        for i in range(ly):
            intercept.append(0.0)
            row = []
            for j in range(lx):
                row.append(0.0)
            coef.append(row)
        return coef, intercept

    def fit(self, inputs, outputs, learning_rate=0.001, no_epochs=10000):
        self.coef_, self.intercept_ = self.initialize_matrix(len(inputs[0]), len(outputs[0]))
        for epoch in range(no_epochs):
            locals = self.initialize_matrix(len(inputs[0]) + 1, len(outputs[0]))[0]
            for i in range(len(inputs)):
                computed_output = self.eval(inputs[i])
                error = [computed_output[j] - outputs[i][j] for j in range(len(outputs[0]))]
                for k in range(len(outputs[0])):
                    for j in range(len(inputs[0])):
                        locals[k][j] += (1 / len(inputs)) * error[k] * inputs[i][j]
                    locals[k][-1] += (1 / len(inputs)) * error[k]
            for i in range(len(outputs[0])):
                for j in range(len(inputs[0])):
                    self.coef_[i][j] = self.coef_[i][j] - locals[i][j] * learning_rate
                self.intercept_[i] = self.intercept_[i] - locals[i][-1] * learning_rate
            iteration_error = self.prediction_error_multi_target(outputs, self.predict(inputs))

    def prediction_error_multi_target(self, real, computed):
        rmse = []
        for i in range(len(real[0])):
            r = [real[j][i] for j in range(len(real))]
            c = [computed[j][i] for j in range(len(computed))]
            val = self.mean_roort_square_error(r, c)
            rmse.append(val)
        return sum([i for i in rmse]) / len(rmse)

    def mean_roort_square_error(self, real, computed):
        return sum([(computed[i] - real[i]) ** 2 for i in range(len(computed))]) / len(computed)

    def predict(self, x):
        y = []
        for i in range(len(x)):
            s = self.eval(x[i])
            y.append(s)
        return y

    def eval(self, x):
        result = []
        for i in range(len(self.intercept_)):
            s = self.intercept_[i]
            for j in range(len(x)):
                s += x[j] * self.coef_[i][j]
            result.append(s)
        return result
