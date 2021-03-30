#include "Repository.h"
#include "exceptions.h"

#include <algorithm>
#include <string>
#include <map>
#include <fstream>
#include <sstream>
#include <numeric>
#include <vector>
#include <random>

#include <QString>
#include <QFile>
#include <QTextStream>
#include <qdebug.h>

using namespace std;
using std::string;

int TenantRepository::findByName(const string name) const {
	const auto it = find_if(tenants.begin(), tenants.end(), [&](const pair<int, Tenant>& par) {
		return par.second.get_name() == name;
	});
	if (it != tenants.end())
	{
		return (*it).second.get_flat_number();
	}
	return -1;
}

const Tenant* TenantRepository::find(const int flat_number) {
	if (tenants.find(flat_number) == tenants.end()) {
		return nullptr;
	}
	return &(tenants[flat_number]);
}

void TenantRepository::add(const Tenant& tenant) {
	if (find_if(tenants.begin(), tenants.end(), [&](const pair<int, Tenant>& par) noexcept {
		return par.second.get_flat_number() == tenant.get_flat_number();
	}) != tenants.end()) {
		throw mystd::RepositoryError("tenant already existent");
	}
	tenants.insert(pair<int, Tenant>(tenant.get_flat_number(), tenant));
}

void TenantRepository::remove(const int flat_number) {
	if (tenants.find(flat_number) == tenants.end()) {
		throw mystd::RepositoryError("flat number inexistent");
	}
	tenants.erase(flat_number);
}

void TenantRepository::modify(const int flat_number, const string name) {
	if (tenants.find(flat_number) == tenants.end()) {
		throw mystd::RepositoryError("flat number inexistent");
	}
	tenants[flat_number].set_name(name);
}

const vector<Tenant> TenantRepository::findAll() const {
	vector<Tenant> vect;
	transform(tenants.begin(), tenants.end(),
		back_inserter(vect),
		[](const std::pair<int, Tenant> &par) {
		return par.second;
	});
	return vect;
}

const int TenantRepository::sum() const {
	return std::accumulate(tenants.begin(), tenants.end(), 0, [&](int tot, const pair<int, Tenant>& par) noexcept { return (tot + par.second.get_flat_size()); });
}

void FileTenantRepository::loadFromFile() {
	ifstream in(filename);
	if (in.fail()) {
		throw mystd::Exception("error opening file");
	}
	string name, flat_type, num;
	int flat_size = 0;
	int flat_number = 0;
	while (!in.eof()) {
		getline(in, name, ',');
		if (name == "") {
			continue;
		}
		getline(in, num, ',');
		flat_number = stoi(num);
		getline(in, num, ',');
		flat_size = stoi(num);
		getline(in, flat_type, '\n');
		Tenant tenant(name, flat_number, flat_size, flat_type);
		try {
			TenantRepository::add(tenant);
		}
		catch (const mystd::RepositoryError) {
			continue;
		}
	}
	in.close();
}

void FileTenantRepository::saveToFile() {
	ofstream out(filename);
	vector<Tenant> all = findAll();
	for (const auto& tenant : all) {
		out << tenant.get_name() << "," << tenant.get_flat_number() << "," << tenant.get_flat_size() << "," << tenant.get_flat_type() << '\n';
	}
	out.close();
}

void NotificationRepository::add(const Notification& notif) {
	if (find_if(notifications.begin(), notifications.end(), [&](const Notification& n) noexcept {
		return notif.get_flat_number() == n.get_flat_number();
	}) != notifications.end()) {
		throw mystd::RepositoryError("notification already existent");
	}
	notifications.push_back(notif);
}

void NotificationRepository::clear() noexcept {
	notifications.clear();
}

int NotificationRepository::size() const noexcept {
	return notifications.size();
}

vector<Notification>& NotificationRepository::find_all() noexcept {
	return notifications;
}

void ProbabilityRepository::raiseIf() const {
	const std::uniform_real_distribution<double> unif(0, 1);
	std::mt19937 mt{ std::random_device{}() };
	const double rnumber = unif(mt);
	if (rnumber < probability) {
		throw mystd::RepositoryError("random exception; " + to_string(rnumber));
	}
}