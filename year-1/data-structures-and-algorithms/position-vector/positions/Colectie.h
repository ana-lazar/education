#pragma once
#define NULL_TELEM -1
#include "Element.h"
#include "Vector.h"

class IteratorColectie;

class Colectie
{
	friend class IteratorColectie;

private:
	Vector elements;
	Vector positions;
public:
	Colectie();

	void adauga(TElem e);

	bool sterge(TElem e);

	bool cauta(TElem elem) const;

	int nrAparitii(TElem elem) const;

	int dim() const;

	bool vida() const;

	IteratorColectie iterator() const;

	~Colectie();
};