#include "IteratorColectie.h"
#include "Colectie.h"

IteratorColectie::IteratorColectie(const Colectie& c) : col(c) {
	curent = 0;
}

void IteratorColectie::prim() {
	curent = 0;
}

void IteratorColectie::urmator() {
	curent++;
}

bool IteratorColectie::valid() const {
	return curent < col.dim();
}

TElem IteratorColectie::element() const {
	return col.elements.get(col.positions.get(curent));
}