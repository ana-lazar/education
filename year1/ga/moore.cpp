#include <iostream>
#include <fstream>
#include <vector>
#include <queue>

#include "comun.h"

using namespace std;

vector<vector<int>> G_moore; // reprezentare: lista de adiacenta
int V_moore; // numarul de noduri
vector<int> P; // lista de parinti
vector<int> L; // lista de lungimi

void citire_lista_moore(char* filename) {
	ifstream in(filename);
	if (in.fail()) {
		cout << "error opening input file";
		throw exception();
	}
	in >> V_moore;
	// initializare lista
	for (int i = 0; i < V_moore; i++) {
		vector<int> Gi;
		G_moore.push_back(Gi);
	}
	// citire muchii din fisier
	int x, y;
	while (!in.eof()) {
		in >> x;
		in >> y;
		G_moore.at(y).push_back(x);
		G_moore.at(x).push_back(y);
	}
	in.close();
}

void MOORE(int s) {
	for (int i = 0; i < V_moore; i++) {
		L.push_back(INFINIT);
		P.push_back(NIL);
	}
	L[s] = 0;
	P[s] = NIL;
	queue<int> Q;
	Q.push(s);
	while (!Q.empty()) {
		int u = Q.front();
		Q.pop();
		for (const int v : G_moore[u]) {
			if (L[v] == INFINIT) {
				P[v] = u;
				L[v] = L[u] + 1;
				Q.push(v);
			}
		}
	}
}

void afisare_lant_moore(char* filename) {
	ofstream out(filename);
	if (out.fail()) {
		cout << "error opening output file";
		throw exception();
	}
	int t = V_moore - 1;
	vector<int> lant;
	for (int u = t; u != NIL; u = P[u]) {
		lant.insert(lant.begin(), u);
	}
	out << L[t] << '\n';
	for (const int u : lant) {
		out << u << ' ';
	}
	out.close();
}

int main_moore(int argc, char* argv[]) {
	citire_lista_moore(argv[1]);
	MOORE(0);
	afisare_lant_moore(argv[2]);
	return 0;
}