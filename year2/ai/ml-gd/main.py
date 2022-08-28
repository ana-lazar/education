# Lazar Ana - Patricia
# Artificial Intelligence
# Machine Learning: gradient descent regression


from service import Service


def run():
    # runs linear regression algorithm for specified data and features
    # service = Service('data/2017.csv', ['Economy..GDP.per.Capita.'], 'Happiness.Score', 0.1, 1000)
    # service = Service('data/2017.csv', ['Economy..GDP.per.Capita.', 'Freedom'], 'Happiness.Score', 0.1, 1000)
    # service = Service('data/2017.csv', ['Economy..GDP.per.Capita.', 'Freedom', 'Family'], 'Happiness.Score', 0.1, 1000)
    service = Service('data/2017.csv', ['Economy..GDP.per.Capita.', 'Freedom'], ['Happiness.Score', 'Family'], 0.1, 1000)
    service.linear_regression()


if __name__ == '__main__':
    run()
