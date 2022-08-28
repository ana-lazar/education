#include <iostream>
#include <fstream>
#include <vector>
#include <queue>

#include "comun.h"

vector<vector<int>> G_ford; // reprezentare: lista de adiacenta
int V_ford, E_ford; // numarul de noduri, respectiv muchii
vector<vector<int>> GF_ford; // rezultat
int maxFlux = 0;

void citire_matrice_ford(char* filename) {
	ifstream in(filename);
	if (in.fail()) {
		cout << "error opening input file";
		throw exception();
	}
	in >> V_ford;
	in >> E_ford;
	for (int i = 0; i < V_ford; i++) {
		vector<int> Gi;
		for (int j = 0; j < V_ford; j++) {
			Gi.push_back(0);
		}
		G_ford.push_back(Gi);
	}
	int x, y, c;
	for (int i = 0; i < E_ford; i++) {
		in >> x >> y >> c;
		G_ford[x][y] = c;
	}
	in.close();
}

bool bfs(int s, vector<int>& drum) {
	// vector de contorizare a varfurilor vizitate in BFS
	vector<bool> visited;
	for (int i = 0; i < V_ford; i++)
		visited.push_back(false);
	// coada de noduri ce urmeaza a fi vizitate
	queue<int> coada;
	coada.push(s);
	visited[s] = true;
	drum[s] = -1;
	// cat timp mai exista noduri de vizitat
	while (!coada.empty()) {
		// se extrage nodul de prioritate cea mai mare
		int nod = coada.front();
		coada.pop();
		// pentru fiecare varf adiacent se verifica daca acesta a fost vizitat
		for (int i = 0; i < V_ford; i++) {
			if (GF_ford[nod][i] > 0 && visited[i] == false) {
				coada.push(i);
				drum[i] = nod;
				visited[i] = true;
			}
		}
	}
	// returnam true daca exista drum intre sursa si destinatie
	return visited[V_ford - 1] == true;
}

void ford_fulkerson() {
	vector<int> drum;
	for (int i = 0; i < V_ford; i++) {
		drum.push_back(0);
	}
	for (int i = 0; i < V_ford; i++) {
		vector<int> Gi;
		for (int j = 0; j < V_ford; j++) {
			Gi.push_back(G_ford[i][j]);
		}
		GF_ford.push_back(Gi);
	}
	int nod, cp;
	while (bfs(0, drum)) {
		// determinare capacitate reziduala
		cp = INT_MAX;
		for (int varf = V_ford - 1; varf != 0; varf = drum[varf]) {
			nod = drum[varf];
			cp = min(cp, GF_ford[nod][varf]);
		}
		// parcurgere muchii din calea reziduala
		for (int varf = V_ford - 1; varf != 0; varf = drum[varf]) {
			nod = drum[varf];
			GF_ford[nod][varf] -= cp;
			GF_ford[varf][nod] += cp;
		}
		maxFlux += cp;
	}
}

void afisare_ford(char* filename) {
	ofstream out(filename);
	if (out.fail()) {
		cout << "error opening output file";
		throw exception();
	}
	out << maxFlux << '\n';
	out.close();
}

int main_ford(int argc, char* argv[]) {
	citire_matrice_ford(argv[1]);
	ford_fulkerson();
	afisare_ford(argv[2]);
	return 0;
}