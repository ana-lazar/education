#include "Colectie.h"
#include "IteratorColectie.h"

#include <iostream>

using namespace std;

bool rel(TElem e1, TElem e2) {
	return e1 <= e2;
}

Colectie::Colectie() {
	cp = CAPACITATE;
	// alocare spatiu pentru tablourile dinamice
	elems = new TElem[cp];
	urm = new int[cp];
	prec = new int[cp];
	// lista e vida
	prim = -1;
	// initializare listei de spatii libere: initSpatiuLiber()
	initSpatiuLiber();
}

int Colectie::aloca() {
	int p = primLiber;
	primLiber = urm[primLiber];
	return p;
}

void Colectie::dealoca(int p) {
	urm[p] = primLiber;
	prec[p] = -1;
	primLiber = p;
}

void Colectie::initSpatiuLiber() {
	for (int i = 0; i < cp - 1; i++) {
		urm[i] = i + 1;
	}
	urm[cp - 1] = -1;
	primLiber = 0;
}

void Colectie::resize() {
	// redimensionarea pentru cele 3 tablouri
	TElem* new_elems = new TElem[cp * 2];
	int* new_urm = new int[cp * 2];
	int* new_prec = new int[cp * 2];
	for (int i = 0; i < cp; i++) {
		new_elems[i] = elems[i];
		new_urm[i] = urm[i];
		new_prec[i] = prec[i];
	}
	// dealocarea spatiului pentru tablourile anterioare si initializarea lor
	delete elems;
	delete urm;
	delete prec;
	elems = new_elems;
	urm = new_urm;
	prec = new_prec;
	// reinitializarea listei de spatii libere
	for (int i = cp; i < cp * 2 - 1; i++) {
		urm[i] = i + 1;
	}
	urm[cp * 2 - 1] = -1;
	primLiber = cp;
	cp = cp * 2;
}

int Colectie::creeazaNod(TElem e) {
	if (primLiber == -1) {
		resize();
	}
	int p = aloca(); // pozitia unde se va adauga
	elems[p] = e;
	urm[p] = -1;
	prec[p] = -1;
	return p;
}

void Colectie::adauga(TElem e) {
	int p = creeazaNod(e);
	// daca lista e vida
	if (prim == -1) {
		prim = p;
		return;
	}
	// daca adaugam la inceputul listei
	if (rel(e, elems[prim])) {
		urm[p] = prim;
		prec[prim] = p;
		prim = p;
		return;
	}
	// actualizam tablourile de legaturi in functie de pozitia unde trebuie adaugat elementul
	int pos = prim;
	while (urm[pos] != -1 && rel(elems[urm[pos]], e)) {
		pos = urm[pos];
	}
	prec[p] = pos;
	urm[p] = urm[pos];
	if (urm[p] != -1) {
		prec[urm[p]] = p;
	}
	urm[pos] = p;
}

bool Colectie::sterge(TElem e) {
	if (elems[prim] == e) {
		int aux = prim;
		prim = urm[prim];
		dealoca(aux);
		return true;
	}
	// parcurgem lista pana gasim elementul de sters
	int p = prim;
	while (p != -1 && elems[p] != e) {
		p = urm[p];
	}
	if (p == -1) {
		return false; // elementul nu exista
	}
	// actualizam tablourile pentru precedent si urmator
	if (urm[p] != -1) {
		prec[urm[p]] = prec[p];
	}
	if (prec[p] != -1) {
		urm[prec[p]] = urm[p];
	}
	// stergem elementul
	dealoca(p);
	return true;
}

bool Colectie::cauta(TElem elem) const {
	// parcurgem lista pana gasim elementul dorit sau se termina
	int p = prim;
	while (p != -1 && elems[p] != elem) {
		p = urm[p];
	}
	return p != -1; // daca s-a ajuns la finalul listei, elementul nu a fost gasit
}

int Colectie::nrAparitii(TElem elem) const {
	// vom calcula numarul de aparitii prin parcurgerea listei
	int ap = 0;
	int p = prim;
	while (p != -1) {
		if (elems[p] == elem) {
			ap++;
		}
		p = urm[p];
	}
	return ap;
}

int Colectie::dim() const {
	// vom calcula dimensiunea prin parcurgerea listei
	int size = 0; 
	int p = prim;
	while (p != -1) {
		size++;
		p = urm[p];
	}
	return size;
}

bool Colectie::vida() const {
	return prim == -1;
}

IteratorColectie Colectie::iterator() const {
	return IteratorColectie(*this);
}

Colectie::~Colectie() {
	delete elems;
	delete urm;
	delete prec;
}