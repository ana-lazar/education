#include "CP.h"

#include <exception>
#include <stdio.h>

using namespace std;

CP::CP(Relatie r) {
	this->prim = nullptr;
	this->r = r;
}

void CP::adauga(TElem e, TPrioritate p) {
	//creeazaNodLDI
	//aloca
	Nod* point = new Nod;
	point->elem = pair<TElem, TPrioritate>(e, p);
	point->urm = nullptr;
	point->prec = nullptr;
	//daca se adauga la inceputul listei
	if (this->prim == nullptr || r(p, prim->elem.second)) {
		point->urm = this->prim;
		this->prim = point;
	}
	else {
		//se parcurge pana unde trebuie adaugat, in fct de relatie
		Nod* curent = this->prim;
		while (curent->urm != nullptr && !r(p, curent->urm->elem.second)) {
			curent = curent->urm;
		}
		point->urm = curent->urm;
		point->prec = curent;
		curent->urm = point;
	}
}

//arunca exceptie daca coada e vida
Element CP::element() const {
	if (vida()) {
		throw exception();
	}
	return prim->elem;
}

//arunca exceptie daca coada e vida
Element CP::sterge() {
	if (vida()) {
		throw exception();
	}
	Element prioritar = prim->elem;
	Nod* aux = prim;
	prim = prim->urm;
	delete aux;
	if (prim) {
		prim->prec = nullptr;
	}
	return prioritar;
}

//arunca exceptie daca exista mai putin de k elemente in coada
Element CP::elK(int k) const {
	Nod* curent = this->prim;
	int count = 1;
	while (curent != nullptr && count < k) {
		curent = curent->urm;
		count++;
	}
	if (curent == nullptr) {
		throw exception();
	}
	return curent->elem;
}

bool CP::vida() const {
	if (prim == nullptr) {
		return true;
	}
	return false;
}


CP::~CP() {
	Nod* curent = prim;
	while (curent != nullptr) {
		Nod* aux = curent->urm;
		delete curent;
		curent = aux;
	}
};
