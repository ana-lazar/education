from math import exp
import random
import numpy as np


def sigmoid(x):
    return 1 / (1 + exp(-x))


def sigmoid_derivative(x):
    return x * (1 - x)


def softmax(x):
    e = [exp(i) for i in x]
    s = sum(e)
    return [i / s for i in e]


class MyNeuralNetwork:
    def __init__(self, no_inputs=None, no_outputs=None, hidden_layers=(2,), max_iter=100, learning_rate=0.001,
                 activation_type='identity'):
        self.no_inputs = 300
        self.no_outputs = no_outputs
        self.hidden_layers = hidden_layers
        self.max_iter = max_iter
        self.learning_rate = learning_rate
        self.activation_type = activation_type

        # list of weight matrices
        self.weights = []
        self.biases = []

        self.initialize()

    def initialize(self):
        # append weight matrix for inputs
        self.weights.append(np.random.rand(self.hidden_layers[0], self.no_inputs))
        self.biases.append(np.random.rand(self.hidden_layers[0], 1))
        # append weight matrix for hidden layers
        for i in range(1, len(self.hidden_layers)):
            mat = np.random.rand(self.hidden_layers[i], self.hidden_layers[i - 1])
            bias = np.random.rand(self.hidden_layers[i], 1)
            self.weights.append(mat)
            self.biases.append(bias)
        # append weight matrix for outputs
        self.weights.append(np.random.rand(self.no_outputs, self.hidden_layers[-1]))
        self.biases.append(np.random.rand(self.no_outputs, 1))

    def train(self, inputs, outputs):
        output_results, input_mat, predicted = self.forward_propagation(inputs)

        # error
        out = np.array(outputs)
        layer_error = predicted - out
        layer_error = layer_error.reshape((layer_error.shape[0], 1))

        # backpropagation
        errors = self.compute_errors(layer_error)
        self.update_weights(errors, output_results, input_mat)

    def fit(self, inputs, outputs):
        prev_error = 1
        improve = 0
        indexes = [i for i in range(len(inputs))]
        for iteration in range(self.max_iter):
            train_indexes = random.sample(indexes, len(indexes))
            input_sample = [inputs[i] for i in train_indexes]
            output_sample = [outputs[i] for i in train_indexes]

            for j in range(len(input_sample)):
                self.train(input_sample[j], output_sample[j])

            this_error = self.square_error(output_sample, self.predict_probab(input_sample))
            # this_error = self.cross_entropy_loss(output_sample, self.predict_probab(input_sample))
            # print('Error: ', this_error)

            if prev_error - this_error < 0.00001:
                improve += 1
            else:
                improve = 0

            if improve > 10:
                print('Not improving for more than 10 iterations; stopping')
                break

            prev_error = this_error

    def predict_probab(self, inputs):
        result = []
        # calculeaza pentru fiecare set de inputuri
        for i in range(len(inputs)):
            result.append(self.predict_sample(inputs[i]))
        return result

    def predict_sample(self, inputs_sample):
        input_mat = np.array(inputs_sample)
        layer_input = input_mat.reshape((input_mat.shape[0], 1))
        # trece prin fiecare nivel al retelei
        for i in range(len(self.weights)):
            mat = self.weights[i]
            bias = self.biases[i]
            result_mat = mat.dot(layer_input)
            result_mat = result_mat + bias
            if i == len(self.weights) - 1:
                result_mat = np.array(softmax(result_mat.flatten()))
                result_mat = result_mat.reshape((result_mat.shape[0], 1))
            else:
                result_mat = self.activate(sigmoid, result_mat)
            layer_input = result_mat
        return layer_input.flatten()

    def predict(self, inputs):
        probab = self.predict_probab(inputs)
        results = []
        for i in range(len(probab)):
            # ia eticheta cu probabilitatea calculata cea mai mare
            index = np.where(probab[i] == (max(probab[i])))[0]
            results.append(index.tolist()[0])
        return results

    def transfer(self, value):
        if self.activation_type == 'identity':
            return value
        elif self.activation_type == 'sigmoid':
            return sigmoid(value)

    def activate(self, func, matrix):
        # applies the function func to every element in the matrix
        all_func = np.vectorize(func)
        return all_func(matrix)

    def forward_propagation(self, inputs):
        input_mat = np.array(inputs)
        output_layers = []
        layer_input = input_mat.reshape((input_mat.shape[0], 1))
        input_mat = layer_input
        # for each layer
        for i in range(len(self.weights)):
            matrix = self.weights[i]
            bias = self.biases[i]
            # compute outputs
            output_mat = matrix.dot(layer_input) + bias
            # if we reached the output layer
            if i == len(self.weights) - 1:
                # compute probabilities with softmax
                output_mat = np.array(softmax(output_mat.flatten()))
                output_mat = output_mat.reshape((output_mat.shape[0], 1))
            else:
                # activate the neuron
                output_mat = self.activate(sigmoid, output_mat)
            layer_input = output_mat
            output_layers.append(output_mat)
        return output_layers, input_mat, layer_input.flatten()

    def transfer_inverse(self, value):
        if self.activation_type == 'identity':
            return value
        elif self.activation_type == 'sigmoid':
            return value * (1 - value)

    def compute_errors(self, layer_error):
        errors = []
        layer_nr = len(self.weights)
        # parcurgem nivelurile in sens invers
        for i in range(layer_nr - 1, -1, -1):
            error_index = layer_nr - 1 - i
            # daca suntem la outputuri, pastram eroarea mare
            if i == layer_nr - 1:
                errors.append(layer_error)
            else:
                # altfel eroarea este eroarea neuronului de dupa el
                tran = self.weights[i + 1].transpose()
                errors.append(tran.dot(errors[error_index - 1]))
        return errors

    def update_weights(self, errors, layer_results, input_mat):
        layer_nr = len(self.weights)
        # parcurgem nivelurile in sens invers
        for i in range(layer_nr - 1, -1, -1):
            error_index = layer_nr - 1 - i
            # daca suntem la ultimul nivel, eroarea este cea calculata
            if i == layer_nr - 1:
                gradients = errors[error_index]
            else:
                # daca suntem la alt nivel, calculam eroarea cu formula
                gradients = self.activate(sigmoid_derivative, layer_results[i])
                gradients = gradients * errors[error_index]
            gradients = gradients * self.learning_rate
            # actualizam termenii liberi
            self.biases[i] = self.biases[i] - gradients
            # daca nu suntem la inputuri, inmultim cu output-ul de pe layer-ul precedent
            if i != 0:
                input_tran = layer_results[i - 1].transpose()
            else:
                input_tran = input_mat.transpose()
            gradients = gradients.dot(input_tran)
            # actualizam greutatile
            self.weights[i] = self.weights[i] - gradients

    @staticmethod
    def mean_square_error(real, computed):
        return sum([(computed[i] - real[i]) ** 2 for i in range(len(computed))]) / len(computed)

    def square_error(self, real, computed):
        all = []
        for i in range(len(real[0])):
            r = [real[j][i] for j in range(len(real))]
            c = [computed[j][i] for j in range(len(computed))]
            val = self.mean_square_error(r, c)
            all.append(val)
        return sum([i for i in all]) / len(all)

    @staticmethod
    def cross_entropy_loss(real, computed):
        sum = 0
        for i in range(len(computed)):
            sum += -real[i] * log(sigmoid(computed[i]) + 1e-15)
        return sum / len(computed)
