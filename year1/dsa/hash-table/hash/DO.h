#pragma once

#include <utility>

typedef int TCheie;
typedef int TValoare;

#define NIL (-INT_MAX)
#define NULL_TVALOARE (-INT_MAX + 1)
#define DEL_TVALOARE (-INT_MAX + 2)
#define MAX_CAPACITY 9973

typedef std::pair<TCheie, TValoare> TElem;

class Iterator;

typedef bool(*Relatie)(TCheie, TCheie);

class DO {
	friend class Iterator;
private:
	// numarul de locatii, respectiv elemente din tabela
	int capacity, size;

	// vectorul de memorare al elementelor
	TElem* table;

	// relatia
	Relatie relatie;

	// calculeaza hashCode-ul unei chei
	int hashCode(TCheie ch);

	// functia de dispersie aleatoare
	int d1(TCheie ch);

	// functia de dispersie aleatoare
	int d2(TCheie ch);

	// functia de dispersie extinsa
	int d(TCheie ch, int i);

	// redimensionarea tabelei dinamice
	void resize();

	// gasim valoare prima pentru capacitate
	int valoareNouCapacitate();

	// functie de prim
	bool prim(int x);
public:
	// constructorul implicit al dictionarului
	DO(Relatie r);

	// adauga o pereche (cheie, valoare) in dictionar
	// daca exista deja cheia in dictionar, inlocuieste valoarea asociata cheii si returneaza vechea valoare
	// daca nu exista cheia, adauga perechea si returneaza null: NULL_TVALOARE
	TValoare adauga(TCheie c, TValoare v);

	// cauta o cheie si returneaza valoarea asociata (daca dictionarul contine cheia) sau null: NULL_TVALOARE
	TValoare cauta(TCheie c);

	// sterge o cheie si returneaza valoarea asociata (daca exista) sau null: NULL_TVALOARE
	TValoare sterge(TCheie c);

	// returneaza numarul de perechi (cheie, valoare) din dictionar
	int dim() const;

	// verifica daca dictionarul e vid
	bool vid() const;

	// se returneaza iterator pe dictionar
	// iteratorul va returna perechile in ordine dupa relatia de ordine (pe cheie)
	Iterator iterator() const;

	// destructorul dictionarului
	~DO();
};