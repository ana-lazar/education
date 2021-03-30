#pragma once

#include "Colectie.h"

class Colectie;

typedef int TElem;

class IteratorColectie
{
	friend class Colectie;

private:
	// complexitate: theta(1)
	// constructorul primeste o referinta catre Container
	// iteratorul va referi primul element din container
	IteratorColectie(const Colectie& c);

	const Colectie& col; // contine o referinta catre containerul pe care il itereaza
	int curent;
public:
	// complexitate: theta(1)
	// reseteaza pozitia iteratorului la inceputul containerului
	void prim();

	// complexitate: theta(1)
	// muta iteratorul in container
	// arunca exceptie daca iteratorul nu e valid
	void urmator();

	// complexitate: theta(1)
	// verifica daca iteratorul e valid (indica un element al containerului)
	bool valid() const;

	// complexitate: theta(1)
	// returneaza valoarea elementului din container referit de iterator
	// arunca exceptie daca iteratorul nu e valid
	TElem element() const;
};
