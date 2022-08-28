#include <iostream>
#include <fstream>
#include <vector>
#include <queue>

#include "comun.h"

using namespace std;

vector<vector<pair<int, int>>> G_jo;
int V_jo, E_jo, s_jo;
vector<int> LJ;
vector<int> PJ;

bool bellman_john() {
	for (int i = 0; i < V_jo; i++) {
		LJ.push_back(INFINIT);
		PJ.push_back(NIL);
	}
	LJ[s_jo] = 0;
	for (int i = 0; i < V_jo; i++) {
		for (int j = 0; j < V_jo; j++) {
			if (LJ[j] != INFINIT) {
				for (auto p : G_jo[j]) {
					if (LJ[p.first] > LJ[j] + p.second) {
						LJ[p.first] = LJ[j] + p.second;
						PJ[p.first] = j;
					}
				}
			}
		}
	}
	for (int i = 0; i < V_jo; i++) {
		if (LJ[i] != INFINIT) {
			for (auto p : G_jo[i]) {
				if (LJ[p.first] > LJ[i] + p.second) {
					return false;
				}
			}
		}
	}
	return true;
}

void citire_lista_johnson(char* filename) {
	ifstream in(filename);
	if (in.fail()) {
		cout << "error opening input file";
		throw exception();
	}
	in >> V_jo;
	in >> E_jo;
	in >> s_jo;
	// initializare lista
	for (int i = 0; i < V_jo; i++) {
		vector<pair<int, int>> Gi;
		G_jo.push_back(Gi);
	}
	// citire muchii din fisier
	int x, y, c;
	for (int i = 0; i < E_jo; i++) {
		in >> x;
		in >> y;
		in >> c;
		G_jo.at(x).push_back(std::make_pair(y, c));
	}
	in.close();
}

int meani(vector<bool>& visited) {
	int min = INFINIT;
	for (int i = 0; i < V_jo; i++) {
		if (visited[i] == false && LJ[i] < min) {
			min = i;
		}
	}
	return min;
}

void johnson() {

}

void afisare_johnson(char* filename) {
	ofstream out(filename);
	if (out.fail()) {
		cout << "error opening output file";
		throw exception();
	}
	for (int i = 0; i < V_jo; i++) {
		if (LJ[i] == INFINIT) {
			out << "INF ";
		}
		else {
			out << LJ[i] << ' ';
		}
	}
	out.close();
}

int main_johnson(int argc, char* argv[]) {
	citire_lista_johnson(argv[1]);
	johnson();
	afisare_johnson(argv[2]);
	return 0;
}