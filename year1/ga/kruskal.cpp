#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>

#include "comun.h"

using namespace std;

struct Muchie {
	int cp, x, y;
	Muchie(int x, int y, int cp) : x(x), y(y), cp(cp) { }
};

vector<Muchie> G_krk;
int V_krk, E_krk;
vector<Muchie> R_krk;

void citire_lista_kruskal(char* filename) {
	ifstream in(filename);
	if (in.fail()) {
		cout << "error opening input file";
		throw exception();
	}
	in >> V_krk >> E_krk;
	// citire muchii din fisier
	int x, y, c;
	while (!in.eof()) {
		in >> x;
		in >> y;
		in >> c;
		G_krk.push_back(Muchie(x, y, c));
	}
	in.close();
}

void kruskal() {
	vector<int> set;
	for (int i = 0; i < V_krk; i++) {
		set.push_back(i);
	}
	sort(G_krk.begin(), G_krk.end(), [](const Muchie& m1, const Muchie& m2) { return m1.cp < m2.cp; });
	for (const Muchie& m : G_krk) {
		if (set[m.x] != set[m.y]) {
			R_krk.push_back(m);
			int mi = min(set[m.x], set[m.y]);
			for (int i = 0; i < V_krk; i++) {
				if (set[i] == set[m.x] || set[i] == set[m.y]) {
					set[i] = mi;
				}
			}
		}
	}
}

void afisare_kruskal(char* filename) {
	ofstream out(filename);
	if (out.fail()) {
		cout << "error opening output file";
		throw exception();
	}
	int sum = 0;
	for (int i = 0; i < R_krk.size(); i++) {
		sum += R_krk.at(i).cp;
	}
	// afisare
	out << sum << "\n" << R_krk.size() << "\n";
	for (int i = 0; i < R_krk.size(); i++) {
		out << R_krk.at(i).x << " " << R_krk.at(i).y << "\n";
	}
	out.close();
}

int main_kruskal(int argc, char* argv[]) {
	citire_lista_kruskal(argv[1]);
	kruskal();
	afisare_kruskal(argv[2]);
	return 0;
}