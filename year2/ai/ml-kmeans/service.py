from my_standard_scaler import MyStandardScaler
from my_kmeans import MyKMeans
from my_logistic_regression import MyLogisticRegression
from repository import Repository

import matplotlib.pyplot as plt
from sklearn.cluster import KMeans
from sklearn.metrics import accuracy_score
from sklearn.feature_extraction.text import CountVectorizer, TfidfVectorizer
from nltk import ngrams
import pandas as pd
import sklearn
import numpy as np
import gensim
import os
import re


# TODO: n_grams


class Service:
    def __init__(self, features=None, loader=None, file_name=None):
        # creates repository and loads data from file
        if loader is not None:
            self.repository = Repository(features, loader=loader)
        else:
            self.repository = Repository(features, file_name=file_name)

        self.wn = []
        self.train_inputs = []
        self.train_outputs = []
        self.text_test_inputs = []
        self.test_inputs = []
        self.test_outputs = []
        self.computed_test_outputs = []
        self.vectorizer = None
        self.classifier = None

    def start(self):
        # splits data into 80% training data and 20% testing data
        self.split_data()

        # representing the text into vectors
        # self.bag_of_words()
        self.tf_idf()
        # self.word2vec()
        # self.n_grams()

        # prints the inputs
        self.print_inputs()

        # find model
        # self.kmeans_tool_spam()
        # self.kmeans_tool_numeric()
        # self.kmeans_manual_numeric()
        self.emotions_manual()
        # self.hybrid_algorithm()

        error = 0.0
        for t1, t2 in zip(self.computed_test_outputs, self.test_outputs):
            if t1 != t2:
                error += 1
        error = 1 - error / len(self.test_outputs)
        print("classification accuracy (manual): ", error)

        accuracy = accuracy_score(self.test_outputs, self.computed_test_outputs)
        print("classification accuracy (tool): ", accuracy)

    def bag_of_words(self):
        self.vectorizer = CountVectorizer()

        self.text_test_inputs = self.test_inputs[:]
        self.train_inputs = self.vectorizer.fit_transform(self.train_inputs).toarray().tolist()
        self.test_inputs = self.vectorizer.transform(self.test_inputs).toarray().tolist()

    def tf_idf(self):
        self.vectorizer = TfidfVectorizer(max_features=50)

        self.text_test_inputs = self.test_inputs[:]
        self.train_inputs = self.vectorizer.fit_transform(self.train_inputs).toarray().tolist()
        self.test_inputs = self.vectorizer.transform(self.test_inputs).toarray().tolist()

    def word2vec(self):
        crt_dir = os.getcwd()
        model_path = os.path.join(crt_dir, 'models', 'GoogleNews-vectors-negative300.bin')

        self.vectorizer = gensim.models.KeyedVectors.load_word2vec_format(model_path, binary=True)
        print(self.vectorizer.most_similar('support'))
        print("vec for house: ", self.vectorizer["house"])

    def n_gram(self):
        ngram = ngrams(sentence.split(' '), n=2)

    def print_inputs(self):
        print("INPUTS")
        df = pd.DataFrame()
        df['vocabulary'] = self.vectorizer.get_feature_names()
        df['train vector'] = self.train_inputs[0]
        df['test vector'] = self.test_inputs[1]
        df.set_index('vocabulary', inplace=True)
        print(df.T)
        print()

    def kmeans_tool_spam(self):
        self.classifier = KMeans(n_clusters=2)
        computed_train_outputs = self.classifier.fit_predict(self.train_inputs)
        computed_test_indexes = self.classifier.predict(self.test_inputs)
        self.computed_test_outputs = [self.repository.output_names[value] for value in computed_test_indexes]

    def kmeans_tool_numeric(self):
        self.classifier = KMeans(n_clusters=3)
        computed_train_outputs = self.classifier.fit_predict(self.train_inputs)
        computed_test_indexes = self.classifier.predict(self.test_inputs)
        self.computed_test_outputs = [self.repository.output_names[value] for value in computed_test_indexes]
        self.plot_results(computed_train_outputs, self.train_inputs, self.classifier.cluster_centers_)
        self.plot_results(computed_test_indexes, self.test_inputs, self.classifier.cluster_centers_)
        self.test_outputs = [self.repository.output_names[value] for value in self.test_outputs]
        print(self.test_outputs)
        print(self.computed_test_outputs)

    def kmeans_manual_numeric(self):
        self.classifier = MyKMeans(n_clusters=3, iterations=100)
        computed_train_outputs = self.classifier.fit_predict(self.train_inputs)
        computed_test_indexes = self.classifier.predict(self.test_inputs)
        self.computed_test_outputs = [self.repository.output_names[value] for value in computed_test_indexes]
        self.plot_results(computed_train_outputs, self.train_inputs, self.classifier.centroids)
        self.plot_results(computed_test_indexes, self.test_inputs, self.classifier.centroids)
        self.test_outputs = [self.repository.output_names[value] for value in self.test_outputs]
        print(self.test_outputs)
        print(self.computed_test_outputs)

    def emotions_manual(self):
        # best: 0.59
        # worst: 0.40
        # self.classifier = KMeans(n_clusters=2, random_state=0)
        # self.classifier.fit_predict(self.train_inputs)

        # best: 0.71
        # worst: 0.38
        self.classifier = MyKMeans(n_clusters=2, iterations=200)
        self.classifier.fit_predict(self.train_inputs)

        # best: 0.80
        # worst: 0.45
        # self.classifier = MyLogisticRegression()
        # self.classifier.fit(self.train_inputs, self.train_outputs)

        computed_test_indexes = self.classifier.predict(self.test_inputs)
        self.computed_test_outputs = [self.repository.output_names[value] for value in computed_test_indexes]

        print("{} - True".format(self.test_outputs))
        print("{} - Predicted\n".format(self.computed_test_outputs))

    def hybrid_algorithm(self):
        # apply unsupervised classification algorithm
        self.classifier = MyKMeans(n_clusters=2, iterations=500)
        self.classifier.fit_predict(self.train_inputs)
        computed_test_indexes = self.classifier.predict(self.test_inputs)
        self.computed_test_outputs = [self.repository.output_names[value] for value in computed_test_indexes]

        # load lists of words
        positive = self.load_words('data/positive.txt')
        negative = self.load_words('data/negative.txt')

        # apply rules
        for i in range(len(self.text_test_inputs)):
            if self.computed_test_outputs[i] == "positive":
                pos_accepted = False
                words = re.sub(r'[.!,;?]', ' ', self.text_test_inputs[i]).split()
                for word in words:
                    if word in positive:
                        pos_accepted = True
                neg_accepted = True
                for word in words:
                    if word in negative:
                        neg_accepted = False
                if not pos_accepted or not neg_accepted:
                    self.computed_test_outputs[i] = 'negative'
        # best: 0.78
        # worst: 0.45
        print(self.test_outputs)
        print(self.computed_test_outputs)
        print()

    def plot_results(self, predicted, inputs, clusters):
        x1 = [inputs[i][0] for i in range(len(inputs))]
        x2 = [inputs[i][1] for i in range(len(inputs))]
        clusters1 = [clusters[i][0] for i in range(len(clusters))]
        clusters2 = [clusters[i][1] for i in range(len(clusters))]
        plt.scatter(clusters1, clusters2, c='black')
        plt.scatter(x1, x2, c=predicted, cmap='rainbow')
        plt.show()

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
        np.random.seed(10)
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
        self.test_inputs = [inputs[i] for i in validation_sample]
        self.test_outputs = [outputs[i] for i in validation_sample]

    def load_words(self, file_name):
        result = []
        file = open(file_name, 'r')
        lines = file.readlines()
        for line in lines:
            result.append(line.strip())
        return result
