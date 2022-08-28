#include <iostream>
#include <fstream>
#include <vector>

#include "comun.h"

using namespace std;

vector<vector<int>> G_floyd;
int V_floyd;
vector<vector<int>> R_floyd;

void citire_matrice_floyd(char* filename) {
	ifstream in(filename);
	if (in.fail()) {
		cout << "error opening the input file";
		throw exception();
	}
	in >> V_floyd;
	for (int i = 0; i < V_floyd; i++) {
		vector<int> Gi;
		for (int j = 0; j < V_floyd; j++) {
			Gi.push_back(0);
		}
		G_floyd.push_back(Gi);
	}
	int x, y, c;
	while (!in.eof()) {
		in >> x;
		in >> y;
		in >> c;
		G_floyd[x][y] = c;
		G_floyd[y][x] = c;
	}
	in.close();
}

void floyd_warshall() {
	for (int i = 0; i < V_floyd; i++) {
		vector<int> Ri;
		for (int j = 0; j < V_floyd; j++) {
			if (G_floyd[i][j] == 0) {
				Ri.push_back(INFINIT);
			}
			else {
				Ri.push_back(G_floyd[i][j]);
			}
		}
		R_floyd.push_back(Ri);
	}
	for (int k = 0; k < V_floyd; k++) {
		for (int j = 0; j < V_floyd; j++) {
			for (int i = 0; i < V_floyd; i++) {
				if (R_floyd[i][k] != INFINIT && R_floyd[k][j] != INFINIT && R_floyd[i][j] > R_floyd[i][k] + R_floyd[k][j]) {
					R_floyd[i][j] = R_floyd[i][k] + R_floyd[k][j];
				}
			}
		}
	}
}

void afisare_matrice_floyd(char* filename) {
	ofstream out(filename);
	if (out.fail()) {
		cout << "error opening the output file";
		throw exception();
	}
	for (int i = 0; i < V_floyd; i++) {
		for (int j = 0; j < V_floyd; j++) {
			out << R_floyd[i][j] << ' ';
		}
		out << '\n';
	}
	out.close();
}

int main_floyd(int argc, char* argv[]) {
	citire_matrice_floyd(argv[1]);
	floyd_warshall();
	afisare_matrice_floyd(argv[2]);
	return 0;
}