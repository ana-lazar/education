# Lazar Ana-Patricia
# Artificial Intelligence
# Logistic regression


from sevice import Service


def run():
    service = Service(['sepal length (cm)', 'sepal width (cm)', 'petal length (cm)', 'petal width (cm)'])
    # service = Service(['petal width (cm)'])
    service.logistic_regression()


if __name__ == '__main__':
    run()
