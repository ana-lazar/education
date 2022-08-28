from utils import get_data, get_categories, get_emojis, get_faces

import pandas as pd
import numpy as np


class Repository:
    def __init__(self, type):
        self.type = type
        self.inputs = []
        self.outputs = []
        self.output_names = []
        if type == 'emojis':
            self.load_emoji_data()
        elif type == 'faces':
            self.load_faces_data()

    def load_emoji_data(self):
        # self.output_names = get_categories()
        self.output_names = ['positive', 'negative']
        # self.inputs, outputs = get_data('data/emoji.json')
        self.inputs, outputs = get_emojis()
        output_list = list(self.output_names)
        self.outputs = [output_list.index(element) for element in outputs]

    def load_faces_data(self):
        self.output_names = ['anger', 'disgust', 'fear', 'happiness', 'sadness', 'surprise', 'neutral']
        data = pd.read_csv('data/faces/train.csv')
        inputs = np.zeros((28709, 48, 48))
        outputs = np.array(list(map(int, data['emotion'])))
        for i in range(len(data)):
            image = np.fromstring(data['pixels'][i], dtype=int, sep=' ')
            image = np.reshape(image, (48, 48))
            inputs[i] = image
        self.inputs = inputs
        self.outputs = outputs
