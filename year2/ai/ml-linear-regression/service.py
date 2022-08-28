from utils import plot_histogram, plot_graphic, plot_3d, plot_diff_single, plot_diff_double, plot_func_single
from utils import plot_func_double, my_mean_root_square_error, plot_computed_single, plot_computed_double
from repository import Repository
from sklearn import linear_model
from sklearn.metrics import mean_squared_error
from my_linear_regression import MyLinearRegression

import matplotlib.pyplot as plt
import numpy as np
import os


class Service:
    def __init__(self, filename, features, output):
        # creates repository and loads data from file
        self.repository = Repository(filename, features, output)

        self.wn = []
        self.train_inputs = []
        self.train_outputs = []
        self.validation_inputs = []
        self.validation_outputs = []
        self.computed_validation_outputs = []
        self.regression = None

    def linear_regression(self):
        # plot histograms for all selected features
        self.plot_histograms()

        # plot the input values to check for linearity
        if self.repository.no_features() == 1:
            self.plot_single()
        if self.repository.no_features() == 2:
            self.plot_double()

        # splits data into 80% training data and 20% testing data
        self.split_data()

        # plot the split input values into training and testing
        if self.repository.no_features() == 1:
            self.plot_split_single()
        if self.repository.no_features() == 2:
            self.plot_split_double()

        # find model using sklearn library
        # self.sklearn_linear_regression()
        self.manual_linear_regression()

        # plot the split input values into training and testing
        if self.repository.no_features() == 1:
            self.plot_model_single()
        if self.repository.no_features() == 2:
            self.plot_model_double()

        # use the trained model to predict new inputs
        self.computed_validation_outputs = self.regression.predict(self.validation_inputs)

        # plot the test vs computed input values into training and testing
        if self.repository.no_features() == 1:
            self.plot_computed_single()
        if self.repository.no_features() == 2:
            self.plot_computed_double()

        # compute the differences between the predictions and real outputs
        error = 0.0
        for t1, t2 in zip(self.computed_validation_outputs, self.validation_outputs):
            error += (t1 - t2) ** 2
        error = error / len(self.validation_outputs)
        print('Prediction error (manual): ', error)

        error = mean_squared_error(self.validation_outputs, self.computed_validation_outputs)
        print('Prediction error (tool):  ', error)

    def manual_linear_regression(self):
        # model initialisation
        self.regression = MyLinearRegression()

        # training the model by using the training inputs and known training outputs
        self.regression.fit(self.train_inputs, self.train_outputs)

        # save the model parameters
        w0 = self.regression.intercept_
        self.wn = self.regression.coef_[:]

        # printing results
        result = 'MANUAL model: f(x) = ' + str(w0)
        for i in range(len(self.wn)):
            result += ' + ' + str(self.wn[i]) + ' * x' + str(i + 1)
        self.wn.insert(0, w0)
        print(result)

    def sklearn_linear_regression(self):
        # model initialisation
        self.regression = linear_model.LinearRegression()

        # training the model by using the training inputs and known training outputs
        self.regression.fit(self.train_inputs, self.train_outputs)

        # save the model parameters
        w0 = self.regression.intercept_
        wn = self.regression.coef_
        self.wn = wn.tolist()
        self.wn.insert(0, w0)

        # printing results
        result = 'SKLEARN model: f(x) = ' + str(w0)
        for i in range(len(wn)):
            result += ' + ' + str(wn[i]) + ' * x' + str(i + 1)
        print(result)

    def split_data(self):
        np.random.seed(5)
        inputs = self.repository.inputs
        outputs = self.repository.outputs

        # splitting the feature indexes between training and testing
        indexes = [i for i in range(len(inputs))]
        rate = 0.8 * len(inputs)
        train_sample = np.random.choice(indexes, int(rate), replace=False)
        validation_sample = [i for i in indexes if not i in train_sample]

        # saving training data separately
        self.train_inputs = [inputs[i] for i in train_sample]
        self.train_outputs = [outputs[i] for i in train_sample]

        # saving validation data separately
        self.validation_inputs = [inputs[i] for i in validation_sample]
        self.validation_outputs = [outputs[i] for i in validation_sample]

    def plot_histograms(self):
        # plots histogram for each input feature
        for feature in self.repository.features:
            plot_histogram(self.repository.get_inputs(feature), feature)
        plot_histogram(self.repository.outputs, ':)')

    def plot_single(self, feature=None):
        # plots graphic for a single selected feature
        if feature is None:
            feature = self.repository.features[0]
        plot_graphic(self.repository.get_inputs(feature), self.repository.outputs)

    def plot_double(self, feature_1=None, feature_2=None):
        # plots graphic for a single selected feature
        if feature_1 is None and feature_2 is None:
            feature_1 = self.repository.features[0]
            feature_2 = self.repository.features[1]
        plot_3d(self.repository.get_inputs(feature_1), self.repository.get_inputs(feature_2), self.repository.outputs)

    def plot_split_single(self):
        # plots graphic for a single selected feature
        plot_diff_single(self.train_inputs, self.train_outputs, self.validation_inputs, self.validation_outputs)

    def plot_split_double(self):
        # plots graphic for a double selected feature
        train_inputs_1, train_inputs_2 = [], []
        for i in range(len(self.train_inputs)):
            train_inputs_1.append(self.train_inputs[i][0])
            train_inputs_2.append(self.train_inputs[i][1])
        train_outputs = [self.train_outputs[i] for i in range(len(self.train_outputs))]
        test_inputs_1, test_inputs_2 = [], []
        for i in range(len(self.validation_inputs)):
            test_inputs_1.append(self.validation_inputs[i][0])
            test_inputs_2.append(self.validation_inputs[i][1])
        test_outputs = [self.validation_outputs[i] for i in range(len(self.validation_outputs))]
        plot_diff_double(train_inputs_1, train_inputs_2, train_outputs, test_inputs_1, test_inputs_2, test_outputs)

    def plot_model_single(self):
        # plots model for a single selected feature
        train_inputs = []
        for i in range(len(self.train_inputs)):
            train_inputs.append(self.train_inputs[i][0])
        plot_func_single(train_inputs, self.train_outputs, self.wn[0], self.wn[1])

    def plot_model_double(self):
        # plots model for a 2 feature input
        train_inputs_1, train_inputs_2 = [], []
        for i in range(len(self.train_inputs)):
            train_inputs_1.append(self.train_inputs[i][0])
            train_inputs_2.append(self.train_inputs[i][1])
        plot_func_double(train_inputs_1, train_inputs_2, self.train_outputs, self.wn)

    def plot_computed_single(self):
        # plots computed vs real values for a single selected feature
        plot_computed_single(self.validation_inputs, self.computed_validation_outputs, self.validation_outputs)

    def plot_computed_double(self):
        # plots computed vs real for a double selected feature
        test_inputs_1, test_inputs_2 = [], []
        for i in range(len(self.train_inputs)):
            test_inputs_1.append(self.train_inputs[i][0])
            test_inputs_2.append(self.train_inputs[i][1])
        train_outputs = [self.train_outputs[i] for i in range(len(self.train_outputs))]
        test_inputs_1, test_inputs_2 = [], []
        for i in range(len(self.validation_inputs)):
            test_inputs_1.append(self.validation_inputs[i][0])
            test_inputs_2.append(self.validation_inputs[i][1])
        test_outputs = [self.validation_outputs[i] for i in range(len(self.validation_outputs))]
        computed_outputs = [self.validation_outputs[i] for i in range(len(self.computed_validation_outputs))]
        plot_computed_double(test_inputs_1, test_inputs_2, test_outputs, computed_outputs)
