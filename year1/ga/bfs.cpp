#include <iostream>
#include <fstream>
#include <vector>
#include <queue>

#include "comun.h"

using namespace std;

vector<vector<int>> G_bfs; // reprezentare: lista de adiacenta
int V_bfs, E_bfs; // numarul de noduri, respectiv muchii
vector<Varf> R_bfs; // rezultat

void citire_lista_bfs(char* filename) {
	ifstream in(filename);
	if (in.fail()) {
		cout << "error opening input file";
		throw exception();
	}
	in >> V_bfs;
	in >> E_bfs;
	// initializare lista
	for (int i = 0; i < V_bfs; i++) {
		vector<int> Gi;
		G_bfs.push_back(Gi);
	}
	// citire muchii din fisier
	int x, y;
	for (int i = 0; i < E_bfs; i++) {
		in >> x;
		in >> y;
		G_bfs.at(y).push_back(x);
		G_bfs.at(x).push_back(y);
	}
	in.close();
}

void BFS(int s) {
	// initializare lista de varfuri
	for (int i = 0; i < V_bfs; i++) {
		R_bfs.push_back(Varf("alb", NIL, INFINIT));
	}
	// incepem de la nodul sursa
	R_bfs.at(s).colour = "gri";
	R_bfs.at(s).d = 0;
	R_bfs.at(s).pi = NIL;
	// coada noastra
	queue<int> Q;
	Q.push(s);
	// parcurgem nodurile in ordinea din coada
	int u;
	while (!Q.empty()) {
		u = Q.front();
		Q.pop();
		// parcurgem toate nodurile adiancente nevizitate
		for (int v = 0; v < G_bfs.at(u).size(); v++) {
			int nod = G_bfs[u][v];
			if (R_bfs.at(nod).colour == "alb") {
				R_bfs.at(nod).colour = "gri";
				R_bfs.at(nod).d = R_bfs.at(u).d + 1;
				R_bfs.at(nod).pi = u;
				Q.push(nod);
			}
		}
		R_bfs.at(u).colour = "negru";
	}
}

void afisare_lant(char* filename) {
	ofstream out(filename);
	if (out.fail()) {
		cout << "error opening output file";
		throw exception();
	}
	for (int i = 0; i < V_bfs; i++) {
		if (R_bfs.at(i).d == 2)
			out << i << " ";
	}
	out.close();
}

int main_bfs(int argc, char* argv[]) {
	citire_lista_bfs(argv[1]);
	BFS(0);
	afisare_lant(argv[2]);
	return 0;
}