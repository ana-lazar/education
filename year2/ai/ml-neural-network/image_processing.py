from PIL import Image
from numpy import asarray


def to_sepia(img):
    width, height = img.size
    pixels = img.load()
    for py in range(height):
        for px in range(width):
            r, g, b = img.getpixel((px, py))

            tr = int(0.393 * r + 0.769 * g + 0.189 * b)
            tg = int(0.349 * r + 0.686 * g + 0.168 * b)
            tb = int(0.272 * r + 0.534 * g + 0.131 * b)

            if tr > 255:
                tr = 255

            if tg > 255:
                tg = 255

            if tb > 255:
                tb = 255

            pixels[px, py] = (tr, tg, tb)
    return img


def remove_sepia(img):
    width, height = img.size
    pixels = img.load()
    for py in range(height):
        for px in range(width):
            r, g, b = img.getpixel((px, py))

            tr = int(0.607 * r + 0.231 * g + 0.811 * b)
            tg = int(0.651 * r + 0.314 * g + 0.832 * b)
            tb = int(0.728 * r + 0.466 * g + 0.869 * b)

            if tr > 255:
                tr = 255

            if tg > 255:
                tg = 255

            if tb > 255:
                tb = 255

            pixels[px, py] = (tr, tg, tb)
    return img


def transform_to_sepia():
    for i in range(1, 151):
        img = Image.open("cat/with_filter/cat" + str(i) + ".jpg")
        img_sepia = to_sepia(img)
        img_sepia.save("cat/with_filter/cat" + str(i) + ".jpg")


def transform_from_sepia():
    for i in range(151, 300):
        img = Image.open("new_cats/cat" + str(i) + ".jpg")
        img_sepia = to_sepia(img)
        img_sepia.save("new_cats/cat" + str(i) + ".jpg")


def resize():
    for i in range(1, 151):
        img = Image.open("cot/with_filter/cat" + str(i) + ".jpg")
        imResize = img.resize((100, 100), Image.ANTIALIAS)
        imResize.save("cot/with_filter/cat" + str(i) + ".jpg")


import os

os.chdir('/Users/analazar/OneDrive - Universitatea Babeş-Bolyai/Semestrul 4/Inteligenta Artificiala/lab-10')
COUNT = 0

def increment():
    global COUNT
    COUNT = COUNT + 1


def rename():
    for f in os.listdir():
        f_name, f_ext = os.path.splitext(f)
        increment()

        new_name = 'cat{}.jpg'.format(str(COUNT))
        os.rename(f, new_name)


def extract_face(filename, required_size=(100, 100)):
    image = Image.open(filename)
    image = image.convert('RGB')
    pixels = asarray(image)
    detector = MTCNN()
    results = detector.detect_faces(pixels)
    x1, y1, width, height = results[0]['box']
    x1, y1 = abs(x1), abs(y1)
    x2, y2 = x1 + width, y1 + height
    face = pixels[y1:y2, x1:x2]
    image = Image.fromarray(face)
    image.save('faces/ana.jpg')
    image = image.resize(required_size)
    face_array = asarray(image)
    return face_array
