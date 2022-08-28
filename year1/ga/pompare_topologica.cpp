#include <iostream>
#include <fstream>
#include <vector>
#include <queue>

#include "comun.h"

struct Vf {
	int e, h;
	Vf(int e, int h) : e(e), h(h) { }
};

vector<vector<int>> G; // reprezentare: lista de adiacenta
int V, E; // numarul de noduri, respectiv muchii
vector<vector<int>> GF; // rezultat
vector<Vf> R;
int maxF = 0;

void citire_matrice_ford(char* filename) {
	ifstream in(filename);
	if (in.fail()) {
		cout << "error opening input file";
		throw exception();
	}
	in >> V;
	in >> E;
	for (int i = 0; i < V; i++) {
		vector<int> Gi;
		for (int j = 0; j < V; j++) {
			Gi.push_back(0);
		}
		G.push_back(Gi);
	}
	int x, y, c;
	for (int i = 0; i < E; i++) {
		in >> x >> y >> c;
		G[x][y] = c;
	}
	in.close();
}

void pompare(int u, int v) {
	// min(u.e, cf (u, v))
	int deltaf;
	if (R[u].e < GF[u][v]) {
		deltaf = R[u].e;
	}
	else {
		deltaf = GF[u][v];
	}
	// daca muchia exista in graful initial
	GF[u][v] -= deltaf;
	GF[v][u] += deltaf;
	// actualizam excesele varfurilor
	R[u].e = R[u].e - deltaf;
	R[v].e = R[v].e + deltaf;
}

void inaltare(int u) {
	int min = R[u].h;
	for (int v = 0; v < V; v++) {
		if (v != 0 && v != V - 1 && G[u][v] - GF[u][v] > 0 && R[v].h < min) {
			min = R[v].h;
		}
	}
	R[u].h = 1 + min;
}

void pomp_topologica() {

}

void afisare_pomp(char* filename) {
	ofstream out(filename);
	if (out.fail()) {
		cout << "error opening output file";
		throw exception();
	}
	out << maxF << '\n';
	out.close();
}

int main_ford(int argc, char* argv[]) {
	citire_matrice_ford(argv[1]);
	pomp_topologica();
	afisare_pomp(argv[2]);
	return 0;
}