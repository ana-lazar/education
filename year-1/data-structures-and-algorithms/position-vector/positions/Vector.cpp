#include <exception>
#include <iostream>
#include "Vector.h"

using namespace std;

Vector::Vector() {
	capacity = 100000;
	length = 0;
	elements = new TElem[capacity];
}

void Vector::resize() {
	capacity *= 2;
	TElem* new_elements = new TElem[capacity];
	for (int i = 0; i < length; i++) {
		new_elements[i] = elements[i];
	}
	delete elements;
	elements = new_elements;
}

void Vector::add(TElem elem) {
	if (length >= capacity) {
		resize();
	}
	elements[length++] = elem;
}

void Vector::remove(int position) {
	if (length <= 1) {
		length = 0;
		return;
	}
	for (int i = position; i < length - 1; i++) {
		elements[i] = elements[i + 1];
	}
	length -= 1;
}

int Vector::size() const {
	return length;
}

TElem Vector::get(int index) const {
	return elements[index];
}

void Vector::set(int index, TElem elem) {
	elements[index] = elem;
}

int Vector::find(int position, TElem elem) const {
	for (int i = position; i < length; i++) {
		if (elements[i] == elem) {
			return i;
		}
	}
	return -1;
}

Vector::~Vector() {
	delete elements;
}