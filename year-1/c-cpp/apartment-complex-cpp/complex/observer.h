#pragma once

#include <vector>
#include <algorithm>

using namespace std;

class Observer {
public:
	virtual void update() = 0;
};

class Observable {
protected:
	vector<Observer*> observers;
public:
	void addObserver(Observer* observer) {
		observers.push_back(observer);
	}

	void removeObserver(Observer* observer) {
		observers.erase(find_if(observers.begin(), observers.end(), [&](const Observer* o) {
			return o == observer;
		}));
	}

	void notifyObservers() {
		for (Observer* obs : observers) {
			obs->update();
		}
	}
};