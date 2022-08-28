import csv


class Repository:
    def __init__(self, input_features, loader=None, file_name=None):
        self.inputs = []
        self.outputs = []
        self.input_features = input_features
        self.output_names = []
        if loader is not None:
            self.load_data(loader, input_features)
        else:
            self.load_data_csv(file_name)

    def load_data(self, loader, input_features):
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

    def load_data_csv(self, file_name):
        data = []
        with open(file_name) as csv_file:
            csv_reader = csv.reader(csv_file, delimiter=',')
            line_count = 0
            for row in csv_reader:
                if line_count == 0:
                    data_names = row
                else:
                    data.append(row)
                line_count += 1
        self.inputs = [data[i][0] for i in range(len(data))]
        self.outputs = [data[i][1] for i in range(len(data))]
        self.output_names = list(set(self.outputs))
