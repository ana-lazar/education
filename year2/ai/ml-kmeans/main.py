# Lazar Ana-Patricia
# Artificial Intelligence
# K-means


from service import Service
from sklearn.datasets import load_iris


def run():
    # service = Service(features=['sepal length (cm)', 'sepal width (cm)', 'petal length (cm)', 'petal width (cm)'],
    #                   loader=load_iris)
    # service = Service(features=['sepal length (cm)', 'sepal width (cm)'], loader=load_iris)
    # service = Service(file_name='data/spam.csv')
    service = Service(file_name='data/mixed_review.csv')
    service.start()


if __name__ == '__main__':
    run()
