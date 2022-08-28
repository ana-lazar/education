#include <iostream>
#include <fstream>
#include <vector>
#include <queue>

#include "comun.h"

using namespace std;

vector<vector<pair<int, int>>> G_djk;
int V_djk, E_djk, s_djk;
vector<int> LD;
vector<int> PD;

void citire_lista_djikstra(char* filename) {
	ifstream in(filename);
	if (in.fail()) {
		cout << "error opening input file";
		throw exception();
	}
	in >> V_djk;
	in >> E_djk;
	in >> s_djk;
	// initializare lista
	for (int i = 0; i < V_djk; i++) {
		vector<pair<int, int>> Gi;
		G_djk.push_back(Gi);
	}
	// citire muchii din fisier
	int x, y, c;
	for (int i = 0; i < E_djk; i++) {
		in >> x;
		in >> y;
		in >> c;
		G_djk.at(x).push_back(std::make_pair(y, c));
	}
	in.close();
}

int mean(vector<bool>& visited) {
	int min = INFINIT;
	for (int i = 0; i < V_djk; i++) {
		if (visited[i] == false && LD[i] < min) {
			min = i;
		}
	}
	return min;
}

void djikstra() {
	vector<bool> visited;
	for (int i = 0; i < V_djk; i++) {
		LD.push_back(INFINIT);
		PD.push_back(NIL);
		visited.push_back(false);
	}
	LD[s_djk] = 0;
	int c, v;
	int u = s_djk;
	while (u != INFINIT) {
		for (const auto p : G_djk[u]) {
			v = p.first;
			c = p.second;
			if (visited[v] == false && LD[v] > LD[u] + c) {
				LD[v] = LD[u] + c;
				PD[v] = u;
			}
		}
		visited[u] = true;
		u = mean(visited);
	}
}

void afisare_djikstra(char* filename) {
	ofstream out(filename);
	if (out.fail()) {
		cout << "error opening output file";
		throw exception();
	}
	for (int i = 0; i < V_djk; i++) {
		if (LD[i] == INFINIT) {
			out << "INF ";
		}
		else {
			out << LD[i] << ' ';
		}
	}
	out.close();
}

int main_djikstra(int argc, char* argv[]) {
	citire_lista_djikstra(argv[1]);
	djikstra();
	afisare_djikstra(argv[2]);
	return 0;
}