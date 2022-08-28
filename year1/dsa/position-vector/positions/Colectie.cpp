#include "Colectie.h"
#include "IteratorColectie.h"
#include <exception>
#include <iostream>

using namespace std;

Colectie::Colectie() {
	
}

void Colectie::adauga(TElem elem) {
	int count = elements.size();
	int i;
	for (i = 0; i < count; i++) {
		if (elements.get(i) == elem) {
			break;
		}
	}
	if (i >= count) {
		elements.add(elem);
		positions.add(count);
	}
	else {
		positions.add(i);
	}
}

bool Colectie::sterge(TElem elem) {
	int elems_index = elements.find(0, elem);
	if (elems_index == -1) {
		return false;
	}
	int pos_index = positions.find(0, elems_index);
	int next_index = positions.find(pos_index + 1, elems_index);
	positions.remove(pos_index);
	if (next_index == -1) {
		elements.remove(elems_index);
		int pos;
		for (int i = 0; i < positions.size(); i++) {
			pos = positions.get(i);
			if (pos >= elems_index) {
				positions.set(i, pos - 1);
			}
		}
	}
	return true;
}

bool Colectie::cauta(TElem elem) const {
	return elements.find(0, elem) != -1;
}

int Colectie::nrAparitii(TElem elem) const {
	int position = elements.find(0, elem);
	if (position == -1) {
		return 0;
	}
	int index = positions.find(0, position);
	int aparitii = 0;
	while (index != -1) {
		aparitii++;
		index = positions.find(index + 1, position);
	}
	return aparitii;
}

int Colectie::dim() const {
	return positions.size();
}

bool Colectie::vida() const {
	return positions.size() == 0;
}

IteratorColectie Colectie::iterator() const {
	return  IteratorColectie(*this);
}

Colectie::~Colectie() {
	
}