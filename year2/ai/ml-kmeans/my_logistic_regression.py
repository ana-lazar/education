from math import exp, log
from numpy.linalg import inv
import numpy as np


def sigmoid(x):
    return 1 / (1 + exp(-x))


def cross_entropy_loss(real, computed):
    sum = 0
    for i in range(len(computed)):
        sum += -real[i] * log(sigmoid(computed[i]) + 1e-15)
    return sum / len(computed)


def mean_square_error(real, computed):
    return sum([(computed[i] - real[i]) ** 2 for i in range(len(computed))]) / len(computed)


class MyLogisticRegression:
    def __init__(self):
        print('MyLogisticRegression')
        self.coef_ = []
        self.classifiers = []

    # simple stochastic GD
    def binary_fit(self, inputs, outputs, learning_rate=0.0001, no_epochs=100):
        self.coef_ = [0.0 for _ in range(len(inputs[0]) + 1)]
        # self.coef_ = [random.random() for _ in range(len(x[0]) + 1)]
        for epoch in range(no_epochs):
            # np.random.shuffle(inputs)
            for i in range(len(inputs)):  # for each sample from the training data
                y_computed = self.eval(inputs[i], self.coef_)  # estimate the output
                crt_error = y_computed - outputs[i]  # compute the error for the current sample
                for j in range(0, len(inputs[0])):  # update the coefficients
                    self.coef_[j] = self.coef_[j] - learning_rate * crt_error * inputs[i][j]
                self.coef_[len(inputs[0])] = self.coef_[len(inputs[0])] - learning_rate * crt_error * 1

    # simple binary batch
    def binary_batch(self, inputs, outputs, learning_rate=0.0001, no_epochs=100):
        self.coef_ = [0.0 for i in range(len(inputs[0]) + 1)]
        for epoch in range(no_epochs):
            gradients = [0.0 for i in range(len(inputs[0]) + 1)]
            for i in range(len(inputs)):
                computed_output = sigmoid(self.eval(inputs[i], self.coef_))
                error = computed_output - outputs[i]
                for j in range(len(inputs[0])):
                    gradients[j] += (1 / len(inputs)) * error * inputs[i][j]
                gradients[-1] += (1 / len(inputs)) * error
            for j in range(len(inputs[0])):
                self.coef_[j] = self.coef_[j] - gradients[j] * learning_rate
            self.coef_[-1] = self.coef_[-1] - gradients[-1] * learning_rate
        # print('Loss: ', cross_entropy_loss(outputs, self.predict_prob(outputs)), '; Accuracy: ',
        #       mean_square_error(outputs, self.predict_prob(outputs)))

    def fit(self, x, y):
        output_names = list(set(y))
        for label in range(len(output_names)):
            train_outputs_label = self.transform_data_by_label(y, label)
            self.binary_fit(x, train_outputs_label, 0.1, 200)
            # self.binary_batch(x, train_outputs_label, 2, 100)
            self.classifiers.append(self.coef_)

    def transform_data_by_label(self, y, label):
        train_outputs = y[:]
        for i in range(len(train_outputs)):
            if train_outputs[i] == label:
                train_outputs[i] = 1
            else:
                train_outputs[i] = 0
        return train_outputs

    def eval(self, xi, coef):
        yi = coef[-1]
        for j in range(len(xi)):
            yi += coef[j] * xi[j]
        return yi

    def predict(self, x):
        computed = []
        for i in range(len(x)):
            val = [self.predict_prob_sample(x[i], self.classifiers[label]) for label in
                   range(len(self.classifiers))]
            # s = sum(val)
            # val = [i / s for i in val]
            index = val.index(max(val))
            computed.append(index)
        return computed

    def predict_prob(self, x, coef=None):
        return [self.predict_prob_sample(x[i], coef) for i in range(len(x))]

    def predict_prob_sample(self, x, coef=None):
        return sigmoid(self.eval(x, coef))
