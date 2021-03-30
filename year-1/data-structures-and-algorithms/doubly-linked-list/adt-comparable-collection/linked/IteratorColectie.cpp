#include "IteratorColectie.h"
#include "Colectie.h"

#include <exception>

using namespace std;

IteratorColectie::IteratorColectie(const Colectie& c) : col(c) {
	curent = col.prim; // primul element din colectie
}

TElem IteratorColectie::element() const {
	if (!valid()) {
		throw exception();
	}
	return col.elems[curent]; // elementul corespunzator pozitiei curente
}

bool IteratorColectie::valid() const {
	return curent != -1;
}

void IteratorColectie::urmator() {
	if (curent == -1) {
		throw exception();
	}
	curent = col.urm[curent]; // urmatorul element din colectie
}

void IteratorColectie::prim() {
	curent = col.prim; // primul element din colectie
}