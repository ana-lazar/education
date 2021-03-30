#pragma once;

#include "IteratorColectie.h"

#define CAPACITATE 100

typedef int TElem;

typedef bool(*Relatie)(TElem, TElem);

// in implementarea operatiilor se va folosi functia (relatia) rel (de ex, pentru <=)
// va fi declarata in .h si implementata in .cpp ca functie externa colectiei
bool rel(TElem, TElem);

class IteratorColectie;

class Colectie {

	friend class IteratorColectie;

private:
	int cp; // capacitatea vectorului dinamic de elemente
	int prim; // referinta catre primul element
	int primLiber; // referinta catre primul spatiu liber
	int* urm; // vector dinamic pentru legaturi
	int* prec; // vector dinamic pentru legaturi
	TElem* elems; // vector dinamic de memorare a elementelor

	// complexitate: theta(n)
	// initializeaza spatiul liber
	void initSpatiuLiber();

	// complexitate: theta(1)
	// aloca spatiu din cele libere pentru un nou element
	// returneaza pozitia spatiului alocat
	int aloca();

	// complexitate: theta(1)
	// dealoca spatiul unui element, il adauga in lista spatiilor libere
	void dealoca(int p);

	// complexitate: theta(1)
	// aloca si initializeaza spatiul pentru un nou element
	int creeazaNod(TElem e);

	// complexitate: theta(n) - capacitatea tablourilor
	// redimensioneaza tablourile
	void resize();
public:
	// complexitate: theta(n) - capacitatea tablourilor
	// initializarea listei de spatii libere
	// constructorul implicit
	Colectie();

	// complexitate: O(n) - capacitatea tablourilor
	// adauga un element in colectie
	void adauga(TElem e);

	// complexitate: O(n) - capacitatea tablourilor
	// sterge o aparitie a unui element din colectie
	// returneaza adevarat daca s-a putut sterge
	bool sterge(TElem e);

	// complexitate: O(n) - capacitatea tablourilor
	// verifica daca un element se afla in colectie
	bool cauta(TElem elem) const;

	// complexitate: O(n) - capacitatea tablourilor
	// returneaza numar de aparitii ale unui element in colectie
	int nrAparitii(TElem elem) const;

	// complexitate: O(n) - capacitatea tablourilor
	// intoarce numarul de elemente din colectie
	int dim() const;

	// complexitate: theta(1)
	// verifica daca colectia e vida;
	bool vida() const;

	// complexitate: theta(1)
	// returneaza un iterator pe colectie
	IteratorColectie iterator() const;

	// complexitate: theta(1)
	// dealocarea spatiului pentru tabelele dinamice
	// destructorul colectiei
	~Colectie();
};