# Lazar Ana - Patricia
# Artificial Intelligence
# Neural Network


from image_processing import resize, transform_to_sepia, rename, transform_from_sepia, extract_face
from service import Service


def run():
    service = Service("sepia")
    service.neutral_network()


if __name__ == '__main__':
    run()
