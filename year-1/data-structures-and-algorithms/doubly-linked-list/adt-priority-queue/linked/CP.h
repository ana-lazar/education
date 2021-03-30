#pragma once

#include <vector>
#include <utility>

using namespace std;

typedef int TElem;
typedef int TPrioritate;

typedef std::pair<TElem, TPrioritate> Element;

struct Nod {
	Element elem;
	Nod* urm;
	Nod* prec;
};

typedef bool(*Relatie)(TPrioritate p1, TPrioritate p2);

class CP {
private:
	Nod* prim;
	Relatie r;
public:
	//complexitate theta(1)
	//constructorul implicit
	CP(Relatie r);

	//complexitate O(n)
	//caz favorabil: elementul adaugat este cel mai prioritar => theta(1)
	//caz defavorabil: elementul adaugat este cel mai neprioritar => theta(n)
	//caz mediu: elementul adaugat este al (1, 2, ..., n)-ulea cel mai prioritar element => O(n)
	//adauga un element in CP
	void adauga(TElem e, TPrioritate p);

	//complexitate theta(1)
	//acceseaza elementul cel mai prioritar in raport cu relatia de ordine
	//arunca exceptie daca CP e vida
	Element element()  const;

	//complexitate O(n)
	//caz favorabil: trebuie returnat cel mai prioritar element => theta(1)
	//caz defavorabil: trebuie returnat cel mai neprioritar element => theta(n)
	//caz mediu: trebuie returnat al (1, 2, ..., n)-ulea cel mai prioritar element => O(n)
	//returneaza al k-lea cel mai prioritar element în raport cu relatia de ordine
	//arunca exceptie daca nu sunt k elemente în CP
	Element elK(int k) const;

	//complexitate theta(1)
	//sterge elementul cel mai prioritar si il returneaza
	//arunca exceptie daca CP e vida
	Element sterge();

	//complexitate theta(1)
	//verifica daca CP e vida;
	bool vida() const;

	//complexitate theta(1)
	// destructorul cozii
	~CP();
};