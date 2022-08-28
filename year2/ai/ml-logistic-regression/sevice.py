from my_standard_scaler import MyStandardScaler
from my_logistic_regression import MyLogisticRegression
from my_logistic_regression import cross_entropy_loss
from repository import Repository
from sklearn.metrics import mean_squared_error
from sklearn.preprocessing import StandardScaler
from sklearn.datasets import load_iris

import matplotlib.pyplot as plt
import plotly.express as px
import plotly.graph_objects as go
from sklearn import linear_model
import sklearn
import numpy as np
import os


class Service:
    def __init__(self, features):
        # creates repository and loads data from file
        self.repository = Repository(load_iris, features)

        self.wn = []
        self.train_inputs = []
        self.train_outputs = []
        self.validation_inputs = []
        self.validation_outputs = []
        self.computed_validation_outputs = []
        self.regression = None

    def logistic_regression(self):
        # splits data into 80% training data and 20% testing data
        self.split_data()

        # normalizes data
        self.train_inputs, self.validation_inputs = self.normalisation(self.train_inputs, self.validation_inputs)

        # find model using sklearn library
        # self.sklearn_logistic_regression()
        # self.manual_logistic_regression()
        self.manual_logistic_k_folds()

        error = 0.0
        for t1, t2 in zip(self.computed_validation_outputs, self.validation_outputs):
            if (t1 != t2):
                error += 1
        error = error / len(self.validation_outputs)
        print("classification error (manual): ", error)

        from sklearn.metrics import accuracy_score
        error = 1 - accuracy_score(self.validation_outputs, self.computed_validation_outputs)
        print("classification error (tool): ", error)

    def manual_logistic_k_folds(self):
        k = 5

        sample_inputs = []
        sample_outputs = []
        # splitting the data into k subsets
        for i in range(k):
            np.random.seed(i)
            inputs = self.repository.inputs
            outputs = self.repository.outputs

            # splitting the feature indexes between training and testing
            indexes = [i for i in range(len(inputs))]
            rate = (1 / k) * len(inputs)
            train_sample = np.random.choice(indexes, int(rate), replace=False)

            sample_outputs.append([outputs[i] for i in train_sample])
            sample_inputs.append([inputs[i] for i in train_sample])

        for i in range(k):
            # model initialisation
            self.regression = MyLogisticRegression()

            # initialize other k - 1 inputs
            self.train_inputs = []
            self.train_outputs = []
            for j in range(k):
                if i != j:
                    self.train_inputs += sample_inputs[j]
                    self.train_outputs += sample_outputs[j]

            # training the model by using the training inputs and known training outputs
            self.regression.fit(self.train_inputs, self.train_outputs)

            # use the trained model to predict new inputs
            self.computed_validation_outputs = self.regression.predict(sample_inputs[i])

            print(sample_outputs[i])
            print(self.computed_validation_outputs)

            self.validation_outputs = sample_outputs[i]
            self.eval_classification(self.computed_validation_outputs)

    def manual_logistic_regression(self):
        # model initialisation
        self.regression = MyLogisticRegression()

        # training the model by using the training inputs and known training outputs
        self.regression.fit(self.train_inputs, self.train_outputs)

        # use the trained model to predict new inputs
        self.computed_validation_outputs = self.regression.predict(self.validation_inputs)

        print(self.validation_outputs)
        print(self.computed_validation_outputs)

        self.eval_classification(self.computed_validation_outputs)

    def eval_classification(self, computed):
        real = self.validation_outputs
        labels = self.repository.output_names
        # calculate accuracy
        accuracy = sum(1 for i in range(len(real)) if real[i] == computed[i]) / len(real)
        # accuracy = cross_entropy_loss(self.validation_outputs, self.computed_validation_outputs)
        # build confusion matrix
        mat = [[0] * len(labels) for i in range(len(labels))]
        for i in range(len(computed)):
            t_index = real[i]
            c_index = computed[i]
            mat[c_index][t_index] += 1
        # calculate precision and recall for each label
        eval_mat = []
        for i in range(len(labels)):
            el = []
            if sum(mat[i]) != 0:
                prec = mat[i][i] / sum(mat[i])
                el.append(prec)
            else:
                el.append(0.0)
            if sum([mat[j][i] for j in range(len(labels))]) != 0:
                rec = mat[i][i] / sum([mat[j][i] for j in range(len(labels))])
                el.append(rec)
            else:
                el.append(0.0)
            eval_mat.append(el)
        print("accuracy: " + str(accuracy))
        for i in range(len(labels)):
            print("{} prec: {} rec: {}".format(labels[i], eval_mat[i][0], eval_mat[i][1]))

    def sklearn_logistic_regression(self):
        # model initialisation
        self.regression = linear_model.LogisticRegression()

        # training the model by using the training inputs and known training outputs
        self.regression.fit(self.train_inputs, self.train_outputs)

        # save the model parameters
        w0 = self.regression.intercept_[0]
        wn = self.regression.coef_
        self.wn = wn.tolist()[:]

        # use the trained model to predict new inputs
        self.computed_validation_outputs = self.regression.predict(self.validation_inputs)

        print(self.validation_outputs)
        print(self.computed_validation_outputs)

        print(sklearn.metrics.classification_report(self.validation_outputs, self.computed_validation_outputs,
                                                    target_names=self.repository.output_names))

    def normalisation(self, train_data, test_data):
        scaler = MyStandardScaler()
        if not isinstance(train_data[0], list):
            train_data = [[d] for d in train_data]
            test_data = [[d] for d in test_data]

            scaler.fit(train_data)
            normalised_train_data = scaler.transform(train_data)
            normalised_test_data = scaler.transform(test_data)

            normalised_train_data = [el[0] for el in normalised_train_data]
            normalised_test_data = [el[0] for el in normalised_test_data]
        else:
            scaler.fit(train_data)
            normalised_train_data = scaler.transform(train_data)
            normalised_test_data = scaler.transform(test_data)
        return normalised_train_data, normalised_test_data

    def split_data(self):
        np.random.seed(20)
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
