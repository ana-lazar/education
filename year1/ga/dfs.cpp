#include <iostream>
#include <fstream>
#include <vector>
#include <stack>

#include "comun.h"

using namespace std;

vector<vector<int>> G_dfs; // reprezentare: lista de adiacenta
int V_dfs, E_dfs; // numarul de noduri, respectiv muchii
vector<Varf> R_dfs; // rezultat

void citire_lista_dfs(char* filename) {
	ifstream in(filename);
	if (in.fail()) {
		cout << "error opening input file";
		throw exception();
	}
	in >> V_dfs;
	in >> E_dfs;
	// initializare lista
	for (int i = 0; i < V_dfs; i++) {
		vector<int> Gi;
		G_dfs.push_back(Gi);
	}
	// citire muchii din fisier
	int x, y;
	for (int i = 0; i < E_dfs; i++) {
		in >> x;
		in >> y;
		G_dfs.at(y).push_back(x);
		G_dfs.at(x).push_back(y);
	}
	in.close();
}

void DFS(int s) {
	// initializare rezultat
	for (int i = 0; i < V_dfs; i++) {
		R_dfs.push_back(Varf("alb", NIL, INFINIT));
	}
	// pornim de la sursa
	R_dfs.at(s).colour = "gri";
	R_dfs.at(s).d = 0;
	R_dfs.at(s).pi = NIL;
	// stiva noastra
	stack<int> S;
	S.push(s);
	// parcurgem nodurile in ordinea de pe stiva
	int u;
	while (!S.empty()) {
		u = S.top();
		S.pop();
		// parcurgem nodurile adiacente nevizitate
		for (const int v : G_dfs.at(u)) {
			if (R_dfs.at(v).colour == "alb") {
				R_dfs.at(v).colour = "gri";
				R_dfs.at(v).d = R_dfs.at(u).d + 1;
				R_dfs.at(v).pi = u;
				S.push(v);
			}
		}
		R_dfs.at(u).colour = "negru";
		cout << u << " ";
	}
}

int main_dfs(int argc, char* argv[]) {
	citire_lista_dfs(argv[1]);
	DFS(0);
	return 0;
}