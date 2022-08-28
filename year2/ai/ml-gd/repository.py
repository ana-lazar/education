import csv


class Repository:
    def __init__(self, filename, features, output):
        # file for the data to be loaded from
        self.filename = filename
        # list of features to be considered inputs
        self.features = features
        # string containing the output feature
        self.output = output
        # lists of input / output values
        # self.inputs, self.outputs = self.read_data()
        self.inputs, self.outputs = self.read_multi_target_data()

    def read_data(self):
        data = []
        data_names = []
        # loading data from csv file
        with open(self.filename) as csv_file:
            csv_reader = csv.reader(csv_file, delimiter=',')
            line_count = 0
            for row in csv_reader:
                if line_count == 0:
                    # first row, containing all the feature names
                    data_names = row
                else:
                    # other rows, containing feature data
                    data.append(row)
                line_count += 1
        inputs = []
        # for each row given in the data
        for i in range(len(data)):
            # creates a list of input feature values
            row = []
            for j in range(len(self.features)):
                variable = data_names.index(self.features[j])
                row.append(float(data[i][variable]))
            inputs.append(row)
        # creates a list of output feature values
        output = data_names.index(self.output)
        outputs = [float(data[i][output]) for i in range(len(data))]
        return inputs, outputs

    def read_multi_target_data(self):
        data = []
        data_names = []
        # loading data from csv file
        with open(self.filename) as csv_file:
            csv_reader = csv.reader(csv_file, delimiter=',')
            line_count = 0
            for row in csv_reader:
                if line_count == 0:
                    # first row, containing all the feature names
                    data_names = row
                else:
                    # other rows, containing feature data
                    data.append(row)
                line_count += 1
        inputs = []
        # for each row given in the data
        for i in range(len(data)):
            # creates a list of input feature values
            row = []
            for j in range(len(self.output)):
                variable = data_names.index(self.output[j])
                row.append(float(data[i][variable]))
            inputs.append(row)
        outputs = []
        # creates a list of output feature values
        for i in range(len(data)):
            # creates a list of input feature values
            row = []
            for j in range(len(self.features)):
                variable = data_names.index(self.features[j])
                row.append(float(data[i][variable]))
            outputs.append(row)
        return inputs, outputs

    def read_uni(self):
        data = []
        data_names = []
        # loading data from csv file
        with open(self.filename) as csv_file:
            csv_reader = csv.reader(csv_file, delimiter=',')
            line_count = 0
            for row in csv_reader:
                if line_count == 0:
                    # first row, containing all the feature names
                    data_names = row
                else:
                    # other rows, containing feature data
                    data.append(row)
                line_count += 1
        selected_variable = data_names.index(self.features[0])
        inputs = [float(data[i][selected_variable]) for i in range(len(data))]
        selected_output = data_names.index(self.output)
        outputs = [float(data[i][selected_output]) for i in range(len(data))]
        return inputs, outputs

    def get_inputs(self, feature):
        index = self.features.index(feature)
        inputs = []
        # creates a list of input values for a specific feature
        for row in self.inputs:
            inputs.append(row[index])
        return inputs

    def no_features(self):
        # returns number of input features
        return len(self.features)
