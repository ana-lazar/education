from math import sqrt
import matplotlib.pyplot as plt
import plotly.express as px
import plotly.graph_objects as go
import numpy as np


def my_mean_root_square_error(real=None, computed=None):
    # computes the mean root square error value for the given data
    return sqrt(sum((r - c) ** 2 for r, c in zip(real, computed)) / len(real))


def plot_histogram(inputs, variable):
    # plots histogram from inputs
    plt.hist(inputs, 10)
    plt.title('Histogram of ' + variable)
    plt.show()


def plot_graphic(inputs, outputs):
    # plots graphic for inputs and outputs
    plt.plot(inputs, outputs, 'ro')
    plt.xlabel('GDP capita')
    plt.ylabel('happiness')
    plt.title('GDP capita vs. happiness')
    plt.show()


def plot_3d(inputs_1, inputs_2, outputs):
    # plots 3D graphic for inputs and outputs
    figure = plt.figure()
    ax = figure.add_subplot(111, projection='3d')
    ax.scatter(inputs_1, inputs_2, outputs, c='r', marker='o')
    ax.set_xlabel('GDP')
    ax.set_ylabel('Freedom')
    ax.set_zlabel(':)')
    plt.title("All data")
    plt.show()


def plot_diff_single(train_inputs, train_outputs, test_inputs, test_outputs):
    plt.plot(train_inputs, train_outputs, 'ro', label='training data')
    plt.plot(test_inputs, test_outputs, 'g^', label='validation data')
    plt.title('Train and test data')
    plt.xlabel('GDP')
    plt.ylabel(':)')
    plt.legend()
    plt.show()


def plot_diff_double(train_inputs_1, train_inputs_2, train_outputs, test_inputs_1, test_inputs_2, test_outputs):
    figure = plt.figure()
    ax = figure.add_subplot(111, projection='3d')
    l1 = ax.scatter(train_inputs_1, train_inputs_2, train_outputs, c='r', marker='o')
    l2 = ax.scatter(test_inputs_1, test_inputs_2, test_outputs, c='g', marker='^')
    ax.set_xlabel('GDP')
    ax.set_ylabel('Freedom')
    ax.set_zlabel(':)')
    plt.legend([l1, l2], ["training", "testing"])
    plt.title("Training and testing data")
    plt.show()


def plot_func_single(train_inputs, train_outputs, w0, w1):
    no_points = 1000
    xref = []
    val = min(train_inputs)
    step = (max(train_inputs) - min(train_inputs)) / no_points
    for i in range(1, no_points):
        xref.append(val)
        val += step
    yref = [w0 + w1 * el for el in xref]
    plt.plot(train_inputs, train_outputs, 'ro')
    plt.plot(xref, yref, 'b-', label='learnt model')
    plt.title('Training data and the learnt model')
    plt.xlabel('GDP')
    plt.ylabel(':)')
    plt.legend()
    plt.show()


def plot_func_double(train_inputs_1, train_inputs_2, train_outputs, wn):
    figure = plt.figure()
    ax = figure.add_subplot(111, projection='3d')
    ax.scatter(train_inputs_1, train_inputs_2, train_outputs, c='r', marker='o')
    x1_l = np.linspace(min(train_inputs_1), max(train_inputs_2), 1000)
    x2_l = np.linspace(min(train_inputs_2), max(train_inputs_1), 1000)
    X1, X2 = np.meshgrid(x1_l, x2_l)
    Y = wn[0] + wn[1] * X1 + wn[2] * X2
    ax.plot_surface(X1, X2, Y)
    ax.set_xlabel('GDP')
    ax.set_ylabel('Freedom')
    ax.set_zlabel(':)')
    plt.title("Training and testing data")
    plt.show()


def plot_computed_single(test_inputs, computed_outputs, test_outputs):
    plt.plot(test_inputs, test_outputs, 'g^', label='test outputs')
    plt.plot(test_inputs, computed_outputs, 'yo', label='computed outputs')
    plt.title('Computed vs real data')
    plt.xlabel('GDP')
    plt.ylabel(':)')
    plt.legend()
    plt.show()


def plot_computed_double(test_inputs_1, test_inputs_2, test_outputs, computed_test_outputs):
    figure = plt.figure()
    ax = figure.add_subplot(111, projection='3d')
    l1 = ax.scatter(test_inputs_1, test_inputs_2, computed_test_outputs, c='y', marker='o')
    l2 = ax.scatter(test_inputs_1, test_inputs_2, test_outputs, c='g', marker='^')
    ax.set_xlabel('GDP')
    ax.set_ylabel('Freedom')
    ax.set_zlabel(':)')
    plt.legend([l1, l2], ["training", "testing"])
    plt.title("Computed vs real data")
    plt.show()
