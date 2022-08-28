class Repository:
    def __init__(self, loader, input_features):
        self.inputs = []
        self.outputs = []
        self.input_features = input_features
        self.output_names = []
        self.load_data(loader, input_features)

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
