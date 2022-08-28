from repository import Repository

from sklearn.preprocessing import StandardScaler
from sklearn import neural_network, linear_model
from tensorflow import keras
from tensorflow.keras import layers, Sequential, Input
from tensorflow.keras.utils import to_categorical
from tensorflow.keras.applications.vgg16 import preprocess_input
from tensorflow.keras.applications.vgg16 import decode_predictions
from tensorflow.keras.applications.vgg16 import VGG16
from tensorflow.keras.models import load_model

import tensorflow as tf
import numpy as np


class Service:
    def __init__(self, type):
        self.repository = Repository(type)

        self.train_inputs = []
        self.train_outputs = []
        self.test_inputs = []
        self.test_outputs = []
        self.computed_test_outputs = []

    def run(self):
        # splits data into 80% training data and 20% testing data
        self.split_data()

        # find model
        self.emojis_tensorflow()
        # self.faces_keras()
        # self.faces_vgg16()

    def emojis_sklearn(self):
        classifier = neural_network.MLPClassifier(hidden_layer_sizes=(50,50,3), activation='relu', max_iter=200,
                                                  solver='sgd', random_state=0, learning_rate_init=.1)

        self.training(classifier, self.train_inputs, self.train_outputs)
        self.computed_test_outputs = self.classification(classifier, self.test_inputs)

        print('Real:')
        print([self.repository.output_names[i] for i in self.test_outputs])
        print('Computed:')
        print([self.repository.output_names[i] for i in self.computed_test_outputs])

        error = 0.0
        for t1, t2 in zip(self.computed_test_outputs, self.test_outputs):
            if t1 != t2:
                error += 1
        error = error / len(self.test_outputs)
        print("classification error (manual): ", error)

        from sklearn.metrics import accuracy_score
        error = 1 - accuracy_score(self.test_outputs, self.computed_test_outputs)
        print("classification error (tool): ", error)

    def emojis_tensorflow(self):
        model = Sequential()
        model.add(layers.Conv2D(filters=16, kernel_size=(3, 3), input_shape=(64, 64, 3)))
        model.add(layers.Activation('relu'))
        model.add(layers.MaxPooling2D(pool_size=(2, 2)))

        model.add(layers.Conv2D(16, (3, 3)))
        model.add(layers.Activation('relu'))
        model.add(layers.MaxPooling2D(pool_size=(2, 2)))

        model.add(layers.Conv2D(16, (3, 3)))
        model.add(layers.Activation('relu'))
        model.add(layers.MaxPooling2D(pool_size=(2, 2)))

        model.add(layers.Flatten())
        model.add(layers.Dense(64))
        model.add(layers.Activation('relu'))
        model.add(layers.Dense(2))
        model.add(layers.Activation('sigmoid'))

        model.compile(loss='binary_crossentropy', metrics=['accuracy'])

        train = np.asarray(self.train_inputs)
        out = np.asarray(self.train_outputs)
        model.fit(train, out, epochs=35, steps_per_epoch=len(train))

        test = np.asarray(self.test_inputs)
        ouch = np.asarray(self.test_outputs)
        print()
        print('Results for test data')
        model.evaluate(test, ouch)

    def faces_keras(self):
        model = Sequential()
        model.add(layers.Conv2D(16, (3, 3), input_shape=(48, 48, 1)))
        model.add(layers.Activation('relu'))
        model.add(layers.MaxPooling2D(pool_size=(2, 2)))

        model.add(layers.Conv2D(16, (3, 3)))
        model.add(layers.Activation('relu'))
        model.add(layers.MaxPooling2D(pool_size=(2, 2)))

        model.add(layers.Conv2D(16, (3, 3)))
        model.add(layers.Activation('relu'))
        model.add(layers.MaxPooling2D(pool_size=(2, 2)))

        model.add(layers.Flatten())
        model.add(layers.Dense(64))
        model.add(layers.Activation('relu'))
        model.add(layers.Dense(7))
        model.add(layers.Activation('softmax'))

        model.compile(loss='categorical_crossentropy', metrics=['accuracy'])

        inputs = self.repository.inputs.reshape((28709, 48, 48, 1))
        outputs = to_categorical(self.repository.outputs)
        model.fit(inputs, outputs, epochs=10, batch_size=32, steps_per_epoch=len(self.train_inputs) // 32, verbose=2)

    def faces_vgg16(self):
        conv_base = VGG16(include_top=False, weights='imagenet', input_shape=(48, 48, 3))
        for layer in conv_base.layers[:-2]:
            layer.trainable = False

        model = Sequential()
        model.add(conv_base)
        model.add(layers.GlobalAveragePooling2D())
        model.add(layers.Dense(7, activation='softmax'))

        model.compile(loss='categorical_crossentropy', metrics=['accuracy'])

        inputs = np.zeros((28709, 48, 48, 3))
        for inp in range(len(self.repository.inputs)):
            image = self.repository.inputs[inp]
            for i in range(len(image)):
                for j in range(len(image[0])):
                    for k in range(3):
                        inputs[inp][i][j][k] = image[i][j]
        outputs = to_categorical(self.repository.outputs)

        model.fit(inputs, outputs, epochs=10, batch_size=32, steps_per_epoch=len(self.train_inputs) // 32, verbose=2)

    def faces_skimage(self):
        from skimage import filters

        inputs = []
        for inp in range(len(self.repository.inputs)):
            inputs.append(filters.sobel(self.repository.inputs[inp]))
        inputs = np.asarray(inputs).reshape((28709, 48, 48, 1))

        model = Sequential()
        model.add(layers.Conv2D(16, (3, 3), input_shape=(48, 48, 1)))
        model.add(layers.Activation('relu'))
        model.add(layers.MaxPooling2D(pool_size=(2, 2)))

        model.add(layers.Conv2D(16, (3, 3)))
        model.add(layers.Activation('relu'))
        model.add(layers.MaxPooling2D(pool_size=(2, 2)))

        model.add(layers.Conv2D(16, (3, 3)))
        model.add(layers.Activation('relu'))
        model.add(layers.MaxPooling2D(pool_size=(2, 2)))

        model.add(layers.Flatten())
        model.add(layers.Dense(64))
        model.add(layers.Activation('relu'))
        model.add(layers.Dense(7))
        model.add(layers.Activation('softmax'))

        model.compile(loss='categorical_crossentropy', metrics=['accuracy'])

        outputs = to_categorical(self.repository.outputs)
        model.fit(inputs, outputs, epochs=10, batch_size=32, steps_per_epoch=len(self.train_inputs) // 32, verbose=2)

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

    def eval_multi_class(self, real_labels, computed_labels, label_names):
        from sklearn.metrics import confusion_matrix

        conf_matrix = confusion_matrix(real_labels, computed_labels)
        acc = sum([conf_matrix[i][i] for i in range(len(label_names))]) / len(real_labels)
        precision = {}
        recall = {}
        for i in range(len(label_names)):
            precision[label_names[i]] = conf_matrix[i][i] / sum([conf_matrix[j][i] for j in range(len(label_names))])
            recall[label_names[i]] = conf_matrix[i][i] / sum([conf_matrix[i][j] for j in range(len(label_names))])
        return acc, precision, recall, conf_matrix

    def training(self, classifier, train_inputs, train_outputs):
        classifier.fit(train_inputs, train_outputs)

    def classification(self, classifier, test_inputs):
        computed_test_outputs = classifier.predict(test_inputs)
        return computed_test_outputs
