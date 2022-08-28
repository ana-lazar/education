#include "Iterator.h"
#include "DO.h"

#include <iostream>
#include <exception>
#include <math.h>

using namespace std;

// complexitate: theta(1)
// functie de calcul a hash code-ului unei chei
int DO::hashCode(TCheie ch) {
	return abs(ch);
}

// complexitate: theta(1)
// functie de calcul a hash code-ului unei chei
int DO::d1(TCheie ch) {
	return hashCode(ch) % capacity;
}

// complexitate: theta(1)
// functie de calcul a hash code-ului unei chei
int DO::d2(TCheie ch) {
	return 1 + hashCode(ch) % (capacity - 2);
}

// complexitate: theta(1)
// initializare functie de dispersie extinsa
int DO::d(TCheie ch, int i) {
	return (d1(ch) + i * d2(ch)) % capacity;
}

// functie de prim
bool DO::prim(int x) {
	for (int d = 2; d <= trunc(sqrt(x)); d++) {
		if (x % d == 0) {
			return false;
		}
	}
	return true;
}

// gasim valoare prima pentru capacitate
int DO::valoareNouCapacitate() {
	int cp = 2 * capacity + 1;
	while (!prim(cp)) {
		cp += 2;
	}
	return cp;
}

// complexitate: O(n^2)
// redimensionare tabela
void DO::resize() {
	int oldCapacity = capacity;
	capacity = valoareNouCapacitate();
	TElem* oldTable = table;
	table = new TElem[capacity];
	// initializam locurile libere
	for (int i = 0; i < capacity; i++) {
		table[i] = std::make_pair(NIL, NULL_TVALOARE);
	}
	int p;
	// redispersam tabela
	for (int i = 0; i < oldCapacity; i++) {
		// adaugam perechea in noua tabela
		TElem e = oldTable[i];
		for (int j = 0; j < capacity; j++) {
			p = d(e.first, j);
			if (table[p].first == NIL) {
					break;
			}
		}
		table[p] = e;
	}
	delete[] oldTable;
}

// complexitate: theta(n)
// constructor pentru dictionarul ordonat
DO::DO(Relatie r) {
	relatie = r; // initializare relatia de ordine
	capacity = MAX_CAPACITY; // initializare numar de locatii
	size = 0;
	// initializare tabela, se marcheaza toate pozitiile ca fiind libere
	table = new TElem[capacity];
	for (int i = 0; i < capacity; i++) {
		table[i] = std::make_pair(NIL, NULL_TVALOARE);
	}
}

// complexitate: O(n)
// caz mediu: theta(1)
// caz favorabil: theta(1) cand cheia exista deja si este pe prima pozitie
// caz defavorabil: theta(n) cand mai este un singur loc liber in tabela si trebuie parcursa toata
TValoare DO::adauga(TCheie c, TValoare v) {
	if (size >= capacity) {
		resize();
	}
	int i, p;
	// gasim pozitia din secventa unde trebuie inserat elementul
	for (i = 0; i < capacity; i++) {
		p = d(c, i);
		// daca cheia exista deja in dictionar
		if (table[p].first == c) {
			TValoare oldV = table[p].second;
			table[p].second = v;
			return oldV;
		}
		// daca trebuie inserat intr-un loc liber
		if (table[p].first == NIL) {
			size++;
			table[p] = std::make_pair(c, v);
			return NULL_TVALOARE;
		}
	}
	return NULL_TVALOARE;
}

// complexitate: O(n)
// caz mediu: theta(1)
// caz favorabil: theta(1) cand cheia este pe prima pozitie
// caz defavorabil: theta(n) cand cheia este pe ultima pozitie
// cauta o cheie si returneaza valoarea asociata (daca dictionarul contine cheia) sau null
TValoare DO::cauta(TCheie c) {
	int i, p;
	for (i = 0; i < capacity; i++) {
		p = d(c, i);
		if (table[p].first == c) {
			return table[p].second;
		}
		if (table[p].first == NIL) {
			break;
		}
	}
	return NULL_TVALOARE;
}

// complexitate: O(n)
// caz mediu: theta(1)
// sterge o cheie si returneaza valoarea asociata (daca exista) sau null
TValoare DO::sterge(TCheie c) {
	int i, p;
	for (i = 0; i < capacity; i++) {
		p = d(c, i);
		if (table[p].first == c) {
			TValoare toReturn = table[p].second;
			table[p] = std::make_pair(NIL, DEL_TVALOARE);
			size--;
			return toReturn;
		}
		if (table[p].second == NULL_TVALOARE) {
			break;
		}
	}
	return NULL_TVALOARE;
}

// complexitate: theta(1)
// returneaza numarul de perechi (cheie, valoare) din dictionar
int DO::dim() const {
	return size;
}

// complexitate: theta(1)
// verifica daca dictionarul e vid
bool DO::vid() const {
	return dim() == 0;
}

// complexitate: O(length^2)
Iterator DO::iterator() const {
	return Iterator(*this);
}

// complexitate: theta(1)
DO::~DO() {
	delete[] table;
}