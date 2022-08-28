#include "Iterator.h"
#include "DO.h"

#include <exception>
#include <iostream>

using namespace std;

// complexitate: O(length^2)
Iterator::Iterator(const DO& d) : dict(d) {
	int n = 0;
	// initializam vectorul de elemente
	elems = new TElem[d.dim()];
	for (int i = 0; i < d.capacity; i++) {
		// daca exista elementul in tabela, il adaugam in vector
		if (d.table[i].first != NIL) {
			elems[n++] = d.table[i];
		}
	}
	// sortam vectorul obtinut
	for (int i = 0; i < n - 1; i++) {
		for (int j = i + 1; j < n; j++) {
			// daca cele doua elemente nu sunt in relatie, trebuie interschimbate
			if (!d.relatie(elems[i].first, elems[j].first)) {
				TElem aux;
				aux = elems[i];
				elems[i] = elems[j];
				elems[j] = aux;
			}
		}
	}
	// setam pozitia la inceputul vectorului
	curent = 0;
	length = d.dim();
}

// complexitate: theta(1)
void Iterator::prim() {
	curent = 0;
}

// complexitate: theta(1)
void Iterator::urmator() {
	if (!valid()) {
		throw exception();
	}
	curent++;
}

// complexitate: theta(1)
bool Iterator::valid() const {
	return (curent < length);
}

// complexitate: theta(1)
TElem Iterator::element() const {
	if (!valid()) {
		throw exception();
	}
	return elems[curent];
}

// complexitate: theta(1)
Iterator::~Iterator() {
	delete[] elems;
}