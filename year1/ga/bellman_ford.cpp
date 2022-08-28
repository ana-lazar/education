#include <iostream>
#include <fstream>
#include <vector>
#include <queue>

#include "comun.h"

using namespace std;

vector<vector<pair<int, int>>> G_bell;
int V_bell, E_bell, s_bell;
vector<int> edgeTo;
vector<int> lengthTo;

void citire_lista_bellman(char* filename) {
	ifstream in(filename);
	if (in.fail()) {
		cout << "error opening input file";
		throw exception();
	}
	in >> V_bell;
	in >> E_bell;
	in >> s_bell;
	// initializare lista
	for (int i = 0; i < V_bell; i++) {
		vector<pair<int, int>> Gi;
		G_bell.push_back(Gi);
	}
	// citire muchii din fisier
	int x, y, c;
	for (int i = 0; i < E_bell; i++) {
		in >> x;
		in >> y;
		in >> c;
		G_bell.at(x).push_back(std::make_pair(y, c));
	}
	in.close();
}

bool bellman_ford() {
	for (int i = 0; i < V_bell; i++) {
		lengthTo.push_back(INFINIT);
		edgeTo.push_back(NIL);
	}
	lengthTo[s_bell] = 0;
	for (int i = 0; i < V_bell; i++) {
		for (int j = 0; j < V_bell; j++) {
			if (lengthTo[j] != INFINIT) {
				for (auto p : G_bell[j]) {
					if (lengthTo[p.first] > lengthTo[j] + p.second) {
						lengthTo[p.first] = lengthTo[j] + p.second;
						edgeTo[p.first] = j;
					}
				}
			}
		}
	}
	for (int i = 0; i < V_bell; i++) {
		if (lengthTo[i] != INFINIT) {
			for (auto p : G_bell[i]) {
				if (lengthTo[p.first] > lengthTo[i] + p.second) {
					return false;
				}
			}
		}
	}
	return true;
}

void afisare_bellman(char* filename) {
	ofstream out(filename);
	if (out.fail()) {
		cout << "error opening output file";
		throw exception();
	}
	for (int i = 0; i < V_bell; i++) {
		if (lengthTo[i] == INFINIT) {
			out << "INF ";
		}
		else {
			out << lengthTo[i] << ' ';
		}
	}
	out.close();
}

int main_bellman(int argc, char* argv[]) {
	citire_lista_bellman(argv[1]);
	if (bellman_ford() != false) {
		afisare_bellman(argv[2]);
	}
	return 0;
}