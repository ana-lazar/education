#include <iostream>
#include <fstream>
#include <vector>

#include "comun.h"

using namespace std;

vector<vector<int>> G_tranz;
int V_tranz;
vector<vector<int>> R_tranz;

void citire_matrice_tranz(char* filename) {
	ifstream in(filename);
	if (in.fail()) {
		cout << "error opening the input file";
		throw exception();
	}
	in >> V_tranz;
	for (int i = 0; i < V_tranz; i++) {
		vector<int> Gi;
		for (int j = 0; j < V_tranz; j++) {
			Gi.push_back(0);
		}
		G_tranz.push_back(Gi);
	}
	int x, y, c;
	while (!in.eof()) {
		in >> x;
		in >> y;
		G_tranz[x][y] = 1;
	}
	in.close();
}

void floyd_tranz() {
	for (int i = 0; i < V_tranz; i++) {
		vector<int> Ri;
		for (int j = 0; j < V_tranz; j++) {
			Ri.push_back(G_tranz[i][j]);
		}
		R_tranz.push_back(Ri);
	}
	for (int k = 0; k < V_tranz; k++) {
		for (int j = 0; j < V_tranz; j++) {
			for (int i = 0; i < V_tranz; i++) {
				if (R_tranz[i][j] == 0) {
					R_tranz[i][j] = R_tranz[i][k] && R_tranz[k][j];
				}
			}
		}
	}
}

void afisare_matrice_tranz(char* filename) {
	ofstream out(filename);
	if (out.fail()) {
		cout << "error opening the output file";
		throw exception();
	}
	for (int i = 0; i < V_tranz; i++) {
		for (int j = 0; j < V_tranz; j++) {
			out << R_tranz[i][j] << ' ';
		}
		out << '\n';
	}
	out.close();
}

int main_tranz(int argc, char* argv[]) {
	citire_matrice_tranz(argv[1]);
	floyd_tranz();
	afisare_matrice_tranz(argv[2]);
	return 0;
}