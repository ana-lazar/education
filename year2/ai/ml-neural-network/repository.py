import numpy as np
from PIL import Image
import tensorflow


class Repository:
    def __init__(self, type, loader=None, input_features=None):
        self.type = type
        self.inputs = []
        self.outputs = []
        self.output_names = []
        self.input_features = input_features
        if type == "iris":
            self.load_iris_data(loader, input_features)
        elif type == "digits":
            self.load_digits_data(loader)
        elif type == "sepia":
            self.load_images_data()

    def load_iris_data(self, loader, input_features):
        data = loader()
        inputs = data['data']
        self.outputs = data['target']
        self.output_names = data['target_names']
        features = list(data['feature_names'])
        selected_features_index = []
        for feature in input_features:
            selected_feature_index = features.index(feature)
            selected_features_index.append(selected_feature_index)
        for i in range(len(inputs)):
            row = []
            for j in range(len(selected_features_index)):
                row.append(float(inputs[i][selected_features_index[j]]))
            self.inputs.append(row)

    def load_images_data(self):
        for i in range(1, 151):
            img = Image.open("cat/with_filter/cat" + str(i) + ".jpg")
            img.convert('RGB')
            pixels = np.array(img.getdata())
            self.inputs.append(pixels.reshape(-1).tolist())
            self.outputs.append(1)
        for i in range(1, 51):
            img = Image.open("cat/without_filter/cat" + str(i) + ".jpg")
            img.convert('RGB')
            pixels = np.array(img.getdata())
            self.inputs.append(pixels.reshape(-1).tolist())
            self.outputs.append(0)
        self.output_names = [0, 1]

    def load_digits_data(self, loader):
        data = loader()
        no = data.images.shape
        self.inputs = data['images'].reshape(no[0], no[1] * no[2]).tolist()
        self.outputs = data['target'].tolist()
        self.output_names = data['target_names']

    def get_image(self, filename):
        value = tensorflow.io.read_file(filename)
        decoded_image = tensorflow.image.decode_image(value)
        return decoded_image

    def load_images(self):
        for i in range(1, 151):
            img = Image.open("cat/with_filter/cat" + str(i) + ".jpg")
            img.convert('RGB')
            pixels = np.array(img.getdata())
            self.inputs.append(pixels.reshape(-1).tolist())
            self.outputs.append(1)
        for i in range(1, 151):
            img = Image.open("cat/without_filter/cat" + str(i) + ".jpg")
            img.convert('RGB')
            pixels = np.array(img.getdata())
            self.inputs.append(pixels.reshape(-1).tolist())
            self.outputs.append(0)
        self.output_names = [0, 1]
