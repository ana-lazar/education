from sklearn.metrics import confusion_matrix, accuracy_score, precision_score, recall_score
from math import sqrt, log


import matplotlib.pyplot as plt
import numpy as np


from utils import read_multi_target, read_single_target, read_multi_class, read_multi_prob, mean_absolute_error,\
    mean_root_square_error, labels_to_probabilities, cross_entropy_loss, sigmoid, read_multi_label


class Predictions:
    def __init__(self):
        pass

    def e1_error_prediction(self, filename):
        real, computed = read_single_target(filename)
        indexes = [i for i in range(len(real))]
        rel, = plt.plot(indexes, real, 'ro', label='real')
        comp, = plt.plot(indexes, computed, 'bo', label='computed')
        plt.xlim(0, 8)
        plt.ylim(0, 10)
        plt.legend([real, (rel, comp)], ["Real", "Computed"])
        plt.show()
        errorL1 = mean_absolute_error(real, computed)
        print('Error (L1): ', errorL1)
        errorL2 = mean_root_square_error(real, computed)
        print('Error (L2): ', errorL2)
        return errorL2

    def e2_binary_classification(self):
        realLabels = ['spam', 'spam', 'ham', 'ham', 'spam', 'ham']
        computedLabels = ['spam', 'ham', 'ham', 'spam', 'spam', 'ham']
        labelNames = ['spam', 'ham']
        # Matrix
        cm = confusion_matrix(realLabels, computedLabels, labels=labelNames)
        # TP + TN / TP + TN + FP + FN
        acc = accuracy_score(realLabels, computedLabels)
        # TP / TP + FP
        precision = precision_score(realLabels, computedLabels, average=None, labels=labelNames)
        # TP / TP + FN
        recall = recall_score(realLabels, computedLabels, average=None, labels=labelNames)
        print("Confusion matrix")
        print(cm)
        print("Accuracy " + str(acc))
        print("Precision " + str(precision))
        print("Recall " + str(recall))
        return cm, acc, precision, recall

    def e3_matrix_classification(self):
        realLabels = ['spam', 'spam', 'ham', 'ham', 'spam', 'ham']
        computedOutputs = [[0.7, 0.3], [0.2, 0.8], [0.4, 0.6], [0.9, 0.1], [0.7, 0.3], [0.4, 0.6]]
        labelNames = list(set(realLabels))
        computedLabels = [labelNames[np.argmax(p)] for p in computedOutputs]
        self.e2_binary_classification()
        return computedLabels

    def e4_multi_target_prediction(self, filename):
        # read realOutputs, computedOutputs from file
        real, computed = read_multi_target(filename)

        # build error list for each target - y1, y2 .. yk
        errors = []
        for fk in range(len(real[0])):
            # get realOutputs, computedOutputs lists for target
            real_k = [real[j][fk] for j in range(len(real))]
            computed_k = [computed[j][fk] for j in range(len(computed))]
            # compute error prediction for lists
            err_k = mean_root_square_error(real_k, computed_k)
            errors.append(err_k)

        # compute average for all error predictions
        # error = sum(errors) / len(errors)
        error = sqrt(sum([i ** 2 for i in errors]) / len(errors))

        # print results
        print('Error: ', error)
        return error

    def e5_multi_classification(self, filename):
        # read labels, realOutputs, computedOutputs from file
        labels, real, computed = read_multi_class(filename)

        # compute accuracy
        accuracy = sum(1 for i in range(len(real)) if real[i] == computed[i]) / len(real)

        # build confusion matrix
        dict = { }
        for i in range(len(labels)):
            dict[labels[i]] = i
        # a len(labels) x len(labels) matrix
        matrix = [[0] * len(labels) for i in range(len(labels))]
        for i in range(len(computed)):
            # increment value for [real, computed] on each index
            true_i = dict[real[i]]
            comp_i = dict[computed[i]]
            matrix[comp_i][true_i] += 1

        # compute precision for each class
        precision = []
        for i in range(len(labels)):
            precision.append(matrix[i][i] / sum(matrix[i]))

        # compute recall for each class
        recall = []
        for i in range(len(labels)):
            recall.append(matrix[i][i] / sum([matrix[j][i] for j in range(len(labels))]))

        # print results
        print('Accuracy: ', accuracy)
        print('Precision: ', precision)
        print('Recall: ', recall)
        print('Confusion matrix: ', matrix)

        return accuracy, precision, recall, matrix

    def e6_regression_loss(self, filename):
        # read realOutputs, computedOutputs from file
        real, computed = read_single_target(filename)

        # compute loss value for lists
        loss = mean_root_square_error(real, computed)

        # print results
        print('Cost value: ', loss)

        return loss

    def e7_classification_loss(self, filename):
        # read labels, realOutputs, computedOutputs from file
        labels, real, computed = read_multi_prob(filename)

        # build real value matrix
        matrix = labels_to_probabilities(real, labels)

        # compute cross entropy loss function
        loss = cross_entropy_loss(matrix, computed)

        # print result
        print('Loss: ', loss)

        return loss

    def e8_multi_label_loss(self, filename):
        # read labels, realOutputs, computedOutputs from file
        labels, real, computed = read_multi_label(filename)

        # build sigmoid value matrix
        c_sig = []
        for i in range(len(computed)):
            s = sigmoid(computed[i])
            c_sig.append(s)

        # compute cross entropy loss function
        loss = cross_entropy_loss(real, c_sig)

        # print result
        print('Loss: ', loss)
