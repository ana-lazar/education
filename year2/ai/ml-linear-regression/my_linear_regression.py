import numpy as np

from math import exp
from numpy.linalg import inv


class MyLinearRegression:
    def __init__(self):
        self.intercept_ = 0.0
        self.coef_ = []

    def fit(self, x, y):
        # using formula: b = (Xt * X)^(-1) * Xt * Yt
        X = []
        for i in range(len(x)):
            row = x[i][:]
            row.append(1)
            X.append(row)
        trans_x = self.transpose(X)
        trans_y = self.transpose([y])
        x_xt = self.multiply(trans_x, X)
        x_xt_1 = self.inverse(x_xt)
        xt_y = self.multiply(trans_x, trans_y)
        result = self.multiply(x_xt_1, xt_y)
        self.intercept_ = result[-1][0]
        for i in range(len(result) - 1):
            self.coef_.append(result[i][0])

    def multiply(self, x, y):
        # multiplies 2 matrices
        result = []
        for i in range(len(x)):
            row = []
            for j in range(len(y[0])):
                sum = 0
                for k in range(len(y)):
                    sum += x[i][k] * y[k][j]
                row.append(sum)
            result.append(row)
        return result

    def transpose(self, matrix):
        # transposes a matrix
        return [[matrix[j][i] for j in range(len(matrix))] for i in range(len(matrix[0]))]

    def inverse(self, matrix):
        identity = []
        for i in range(len(matrix)):
            row = [0 for j in range(len(matrix))]
            row[i] = 1
            identity.append(row)
        mat = matrix[:]
        for i in range(len(mat)):
            val1 = 1.0 / mat[i][i]
            for j in range(len(mat)):
                mat[i][j] *= val1
                identity[i][j] *= val1
            for k in range(len(mat)):
                if k != i:
                    val2 = mat[k][i]
                    for j in range(len(mat)):
                        mat[k][j] = mat[k][j] - val2 * mat[i][j]
                        identity[k][j] = identity[k][j] - val2 * identity[i][j]
        return identity

    def predict(self, inputs):
        # predict the outputs for some new inputs (by using the learnt model)
        outputs = []
        for i in range(len(inputs)):
            result = self.intercept_
            for j in range(len(self.coef_)):
                result += inputs[i][j] * self.coef_[j]
            outputs.append(result)
        return outputs
