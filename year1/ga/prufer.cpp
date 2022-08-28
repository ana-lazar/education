#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>

#include "comun.h"

using namespace std;

vector<int> PP;
int N_prf;
vector<int> CP;

void citire_lista_prufer(char* filename) {
	ifstream in(filename);
	if (in.fail()) {
		cout << "error opening input file";
		throw exception();
	}
	in >> N_prf;
	// initializare lista
	int x;
	for (int i = 0; i < N_prf; i++) {
		in >> x;
		PP.push_back(x);
	}
	in.close();
}

void codare_prufer() {
	int v;
	for (int i = 1; i < N_prf; i++) {
		for (v = 0; v < N_prf; v++) {
			if (PP[v] != -2 && find(PP.begin(), PP.end(), v) == PP.end()) {
				break;
			}
		}
		CP.push_back(PP[v]);
		PP[v] = -2;
	}
}

void decodare_prufer() {
	int x, y;
	for (int i = 1; i < N_prf; i++) {
		x = CP[0];
		y = 0;
		while (true) {
			if (find(CP.begin(), CP.end(), y) == CP.end()) {
				break;
			}
			y++;
		}
		PP[y] = x;
		CP.erase(CP.begin());
		CP.push_back(y);
	}
}

void afisare_prufer(char* filename) {
	ofstream out(filename);
	if (out.fail()) {
		cout << "error opening output file";
		throw exception();
	}
	out << N_prf << '\n';
	for (int i = 0; i < N_prf; i++) {
		out << PP[i] << ' ';
	}
	out << "\n\n" << CP.size() << '\n';
	for (int i = 0; i < CP.size(); i++) {
		out << CP[i] << ' ';
	}
	out.close();
}

int main_prufer(int argc, char* argv[]) {
	citire_lista_prufer(argv[1]);
	codare_prufer();
	decodare_prufer();
	afisare_prufer(argv[2]);
	return 0;
}