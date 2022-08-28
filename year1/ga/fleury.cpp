#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>
#include <stack>

#include "comun.h"

using namespace std;

vector<vector<int>> G_fleu;
int V_fleu, E_fleu;
vector<int> C_fleu;

void citire_lista_fleury(char* filename) {
	ifstream in(filename);
	if (in.fail()) {
		cout << "error when opening input file";
		throw exception();
	}
	in >> V_fleu >> E_fleu;
	for (int i = 0; i < V_fleu; i++) {
		vector<int> Gi;
		G_fleu.push_back(Gi);
	}
	int x, y;
	for (int i = 0; i < E_fleu; i++) {
		in >> x >> y;
		G_fleu[x].push_back(y);
		G_fleu[y].push_back(x);
	}
	in.close();
}

void fleury() {
	vector<bool> visited;
	for (int i = 0; i < V_fleu; i++) {
		visited.push_back(false);
	}
	int u = 0;
	C_fleu.push_back(u);
	int aux;
	while (E_fleu != 0) {
		for (const int v : G_fleu[u]) {
			aux = v;
			bool ok = true;
			for (int i = 0; i < V_fleu; i++) {
				visited[i] = false;
			}
			stack<int> S;
			S.push(u);
			int a;
			visited[u] = true;
			while (!S.empty()) {
				a = S.top();
				S.pop();
				for (int k = 0; k < G_fleu[a].size(); k++) {
					int var = G_fleu[a][k];
					if ((!(a == u && var == v) && !(var == u && a == v)) && visited[var] == false) {
						S.push(var);
						visited[var] = true;
						if (var == v) {
							ok = false;
						}
					}
				}
			}
			if (!ok) {
				break;
			}
		}
		C_fleu.push_back(aux);
		if (u != aux) {
			G_fleu[u].erase(find(G_fleu[u].begin(), G_fleu[u].end(), aux));
			G_fleu[aux].erase(find(G_fleu[aux].begin(), G_fleu[aux].end(), u));
		}
		E_fleu--;
		u = aux;
	}
}

void afisare_ciclu_fleury(char* filename) {
	ofstream out(filename);
	if (out.fail()) {
		cout << "error when opening output file";
		throw exception();
	}
	for (int i = 0; i < C_fleu.size(); i++) {
		out << C_fleu[i] << ' ';
	}
	out.close();
}

int main_fleury(int argc, char* argv[]) {
	citire_lista_fleury(argv[1]);
	fleury();
	afisare_ciclu_fleury(argv[2]);
	return 0;
}