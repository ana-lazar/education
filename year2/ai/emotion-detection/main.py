# Lazar Ana - Patricia
# Artificial Intelligence
# Lab 12


from service import Service
from utils import rename, move


def run():
    service = Service('emojis')
    # service = Service('faces')
    service.run()


if __name__ == '__main__':
    run()
