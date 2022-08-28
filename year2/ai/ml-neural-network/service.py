from repository import Repository
from my_neural_network import MyNeuralNetwork
from my_standard_scaler import MyStandardScaler

from sklearn.preprocessing import StandardScaler
from sklearn import neural_network
from sklearn.datasets import load_iris, load_digits
import numpy as np


class Service:
    def __init__(self, type):
        if type == 'iris':
            self.repository = Repository(type, load_iris, ['sepal length (cm)', 'sepal width (cm)',
                                                           'petal length (cm)', 'petal width (cm)'])
        elif type == 'digits':
            self.repository = Repository(type, load_digits)
        elif type == 'sepia':
            self.repository = Repository(type)
        elif type == 'faces':
            self.repository = Repository(type)

        self.train_inputs = []
        self.train_outputs = []
        self.validation_inputs = []
        self.validation_outputs = []
        self.computed_validation_outputs = []

    def neutral_network(self):
        # splits data into 80% training data and 20% testing data
        self.split_data()

        # normalizes data
        # if self.repository.type != 'sepia':
        #     self.train_inputs, self.validation_inputs = self.normalisation(self.train_inputs, self.validation_inputs)

        # find model using sklearn library
        # self.sklearn_neural_network()
        self.manual_neural_network()

        error = 0.0
        for t1, t2 in zip(self.computed_validation_outputs, self.validation_outputs):
            if (t1 != t2):
                error += 1
        error = error / len(self.validation_outputs)
        print("classification error (manual): ", error)

        from sklearn.metrics import accuracy_score
        error = 1 - accuracy_score(self.validation_outputs, self.computed_validation_outputs)
        print("classification error (tool): ", error)

    def transform_output(self, output):
        # etichetele reale se transforma in liste de probabilitati
        transformed = []
        nr_classes = len(self.repository.output_names)
        for i in range(len(output)):
            row = [1 if self.train_outputs[i] == j else 0 for j in range(nr_classes)]
            transformed.append(row)
        return transformed

    def normalize_images(self, input):
        inputs = []
        for i in range(len(input)):
            if isinstance(input[0], list):
                inputs.append([input[i][j] / 255 for j in range(len(input[0]))])
        return inputs

    def manual_neural_network(self):
        classifier = None
        if self.repository.type == 'iris':
            classifier = MyNeuralNetwork(no_inputs=4, no_outputs=3, hidden_layers=(5,5), max_iter=150,
                                         learning_rate=0.01)
        elif self.repository.type == 'digits':
            classifier = MyNeuralNetwork(no_inputs=64, no_outputs=10, hidden_layers=(64,), max_iter=200,
                                         learning_rate=0.01)
        elif self.repository.type == 'sepia':
            classifier = MyNeuralNetwork(no_outputs=2, hidden_layers=(5,5), max_iter=200,
                                         learning_rate=0.001)

        self.train_outputs = self.transform_output(self.train_outputs)

        self.training(classifier, self.train_inputs, self.train_outputs)
        self.computed_validation_outputs = self.classification(classifier, self.validation_inputs)

        self.eval_manual(self.computed_validation_outputs)

    def sklearn_neural_network(self):
        classifier = None

        if self.repository.type == 'iris':
            classifier = neural_network.MLPClassifier(hidden_layer_sizes=(5,), activation='relu', max_iter=126,
                                                      solver='sgd', verbose=10, random_state=1, learning_rate_init=.1)
        elif self.repository.type == 'digits':
            classifier = neural_network.MLPClassifier(hidden_layer_sizes=(64,), activation='relu', max_iter=60,
                                                      solver='sgd', verbose=10, random_state=1, learning_rate_init=.1)

        self.training(classifier, self.train_inputs, self.train_outputs)
        self.computed_validation_outputs = self.classification(classifier, self.validation_inputs)
        # acc, prec, recall, cm = self.eval_multi_class(np.array(self.validation_outputs),
        #                                               self.computed_validation_outputs, self.repository.output_names)

        # print("Accuracy: ", acc)
        # print("Precision: ", prec)
        # print("Recall: ", recall)
        # print("Confusion matrix: ")
        # print(cm)

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

    def eval_multi_class(self, real_labels, computed_labels, label_names):
        from sklearn.metrics import confusion_matrix

        confMatrix = confusion_matrix(real_labels, computed_labels)
        acc = sum([confMatrix[i][i] for i in range(len(label_names))]) / len(real_labels)
        precision = {}
        recall = {}
        for i in range(len(label_names)):
            precision[label_names[i]] = confMatrix[i][i] / sum([confMatrix[j][i] for j in range(len(label_names))])
            recall[label_names[i]] = confMatrix[i][i] / sum([confMatrix[i][j] for j in range(len(label_names))])
        return acc, precision, recall, confMatrix

    def training(self, classifier, train_inputs, train_outputs):
        classifier.fit(train_inputs, train_outputs)

    def classification(self, classifier, test_inputs):
        computed_test_outputs = classifier.predict(test_inputs)
        return computed_test_outputs

    def eval_manual(self, computed):
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
        # for i in range(len(labels)):
        #     print("{} prec: {} rec: {}".format(labels[i], eval_mat[i][0], eval_mat[i][1]))
        # print('confusion matrix:')
        # for line in mat:
        #     print(line)

