# Lazar Ana - Patricia
# Artificial Intelligence
# Linear regression


from service import Service
from repository import Repository


def run():
    # runs linear regression algorithm for specified data and features
    # service = Service('data/2017.csv', ['Economy..GDP.per.Capita.'], 'Happiness.Score')
    service = Service('data/2017.csv', ['Economy..GDP.per.Capita.', 'Freedom'], 'Happiness.Score')
    # service = Service('data/2017.csv', ['Economy..GDP.per.Capita.', 'Freedom', 'Family'], 'Happiness.Score')
    service.linear_regression()


if __name__ == '__main__':
    run()
