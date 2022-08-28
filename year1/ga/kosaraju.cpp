#include <iostream>
#include <fstream>
#include <vector>
#include <stack>
#include <algorithm>

#include "comun.h"

using namespace std;

struct Var {
	int pi, d, f;
	string colour;
	Var(string colour, int pi, int d, int f) : colour(colour), pi(pi), d(d), f(f) { }
};

vector<vector<int>> G_kosaraju; // reprezentare: lista de adiacenta
vector<vector<int>> GT; // reprezentare: lista de adiacenta
int V_kosaraju, E_kosaraju; // numarul de noduri, respectiv muchii
vector<Var> R_kosaraju; // rezultat
vector<Var> RT; // rezultat

void citire_lista_kosaraju(char* filename) {
	ifstream in(filename);
	if (in.fail()) {
		cout << "error opening input file";
		throw exception();
	}
	in >> V_kosaraju;
	in >> E_kosaraju;
	// initializare lista
	for (int i = 0; i < V_kosaraju; i++) {
		vector<int> Gi;
		G_kosaraju.push_back(Gi);
	}
	// citire muchii din fisier
	int x, y;
	for (int i = 0; i < E_kosaraju; i++) {
		in >> x;
		in >> y;
		G_kosaraju.at(x).push_back(y);
	}
	in.close();
}

void DFS_visit_first(int u, int time) {
	time++;
	R_kosaraju[u].d = time;
	R_kosaraju[u].colour = "gri";
	for (const int v : G_kosaraju[u]) {
		if (R_kosaraju[v].colour == "alb") {
			R_kosaraju[v].pi = u;
			DFS_visit_first(v, time);
		}
	}
	R_kosaraju[u].colour = "negru";
	time++;
	R_kosaraju[u].f = time;
}

void DFS_first(int s) {
	for (int i = 0; i < V_kosaraju; i++) {
		R_kosaraju.push_back(Var("alb", NIL, INFINIT, INFINIT));
	}
	R_kosaraju[s].colour = "gri";
	R_kosaraju[s].d = 0;
	R_kosaraju[s].f = 0;
	R_kosaraju[s].pi = NIL;
	int time = 0;
	for (int u = 0; u < V_kosaraju; u++) {
		if (R_kosaraju[u].colour == "alb") {
			DFS_visit_first(u, time);
		}
	}
}

void DFS_visit_second(int u, int time) {
	time++;
	RT[u].d = time;
	RT[u].colour = "gri";
	for (const int v : GT[u]) {
		if (RT[v].colour == "alb") {
			RT[v].pi = u;
			DFS_visit_second(v, time);
		}
	}
	RT[u].colour = "negru";
	time++;
	RT[u].f = time;
}

void DFS_second(int s) {
	for (int i = 0; i < V_kosaraju; i++) {
		RT.push_back(Var("alb", NIL, INFINIT, INFINIT));
	}
	RT[s].colour = "gri";
	RT[s].d = 0;
	RT[s].f = 0;
	RT[s].pi = NIL;
	sort(R_kosaraju.begin(), R_kosaraju.end(), [](const Var& v1, const Var& v2) {
		return v1.f < v2.f;
	});
	int time = 0;
	for (int u = 0; u < V_kosaraju; u++) {
		if (RT[u].colour == "alb") {
			DFS_visit_second(u, time);
		}
	}
}

void determinare_gt() {
	for (int i = 0; i < V_kosaraju; i++) {
		vector<int> GTi;
		GT.push_back(GTi);
	}
	for (int i = 0; i < V_kosaraju; i++) {
		for (const int u : G_kosaraju[i]) {
			GT[u].push_back(i);
		}
	}
}

void kosaraju_sharir() {
	DFS_first(0);
	determinare_gt();
	DFS_second(0);
}

void afisare_kosaraju(char* filename) {
	ofstream out(filename);
	if (out.fail()) {
		cout << "error opening output file";
		throw exception();
	}

	out.close();
}

int main_kosaraju(int argc, char* argv[]) {
	citire_lista_kosaraju(argv[1]);
	kosaraju_sharir();
	afisare_kosaraju(argv[2]);
	return 0;
}