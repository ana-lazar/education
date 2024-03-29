from math import sqrt


class MyStandardScaler:
    def __init__(self):
        self.mean = []
        self.std = []

    def fit(self, inputs):
        for i in range(len(inputs[0])):
            m = sum([inputs[j][i] for j in range(len(inputs))]) / len(inputs)
            s = sqrt((1 / len(inputs)) * sum([(inputs[j][i] - m) ** 2 for j in range(len(inputs))]))
            if m == 0:
                m = 2e-15
            if s == 0:
                s = 2e-15
            self.mean.append(m)
            self.std.append(s)

    def transform(self, inputs):
        res = inputs[:]
        for i in range(len(inputs)):
            for j in range(len(inputs[0])):
                res[i][j] = (inputs[i][j] - self.mean[j]) / self.std[j]
        return res
