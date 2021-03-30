#pragma once

#include "Colectie.h"
#include "Element.h"

class Colectie;

class IteratorColectie
{
	friend class Colectie;
private:
	IteratorColectie(const Colectie& c);

	const Colectie& col;
	TElem curent;
public:
	//reseteaza pozitia iteratorului la inceputul containerului
	void prim();

	//muta iteratorul in container
	// arunca exceptie daca iteratorul nu e valid
	void urmator();

	//verifica daca iteratorul e valid (indica un element al containerului)
	bool valid() const;

	//returneaza valoarea elementului din container referit de iterator
	//arunca exceptie daca iteratorul nu e valid
	TElem element() const;
};
