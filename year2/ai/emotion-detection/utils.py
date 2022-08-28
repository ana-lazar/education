import json
import numpy as np
import cv2
import os
import tensorflow as tf

from PIL import Image


def get_data(filename):
    file = open(filename, )

    data = json.load(file)

    inputs = []
    outputs = []
    for element in data:
        if element["has_img_apple"] and element["category"] == "Smileys & Emotion":
            inputs.append(get_image(element["subcategory"] + '/' + element["image"]))
            outputs.append(element["subcategory"])

    file.close()

    return inputs, outputs


def get_faces(filename):
    file = open(filename, )

    data = json.load(file)

    inputs = []
    outputs = []
    for element in data:
        if element["has_img_apple"] and element["category"] == "Smileys & Emotion":
            inputs.append(get_image(element["subcategory"] + '/' + element["image"]))
            outputs.append(element["subcategory"])

    file.close()

    return inputs, outputs


def move():
    file = open('/Users/analazar/OneDrive - Universitatea Babeş-Bolyai/Semestrul 4/Inteligenta Artificiala/lab-12/data/emoji.json', )

    data = json.load(file)

    os.chdir(
        '/Users/analazar/OneDrive - Universitatea Babeş-Bolyai/Semestrul 4/Inteligenta Artificiala/lab-12/data/emojis')
    for element in data:
        if element["has_img_apple"] and element["category"] == "Smileys & Emotion":
            for f in os.listdir():
                if f == element["image"]:
                    new_name = element['subcategory'] + '/' + element['image']
                    os.rename(f, element['subcategory'] + '/' + f)
                print(f)

    file.close()


def rename():
    for f in os.listdir():
        f_name, f_ext = os.path.splitext(f)
        print(f)


def get_image(filename):
    w = 64
    h = 64
    image = cv2.imread("/Users/analazar/OneDrive - Universitatea Babeş-Bolyai/Semestrul 4/Inteligenta Artificiala/lab-12/data/emojis/" + filename)
    image = cv2.resize(image, (w, h))
    image = tf.cast(image, tf.float32)
    return image


def get_emojis():
    inputs = []
    outputs = []
    for file in os.listdir('data/emojis/positive'):
        inputs.append(get_image('positive/' + file))
        outputs.append('positive')
    for file in os.listdir('data/emojis/negative'):
        inputs.append(get_image('negative/' + file))
        outputs.append('negative')
    return inputs, outputs


def get_categories():
    return ['face-unwell', 'cat-face', 'face-affection', 'face-costume', 'face-neutral-skeptical', 'face-hand',
            'face-tongue', 'face-smiling', 'emotion', 'face-negative', 'face-hat', 'monkey-face', 'face-sleepy',
            'face-concerned', 'face-glasses']
