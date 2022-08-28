#include "Service.h"
#include "undos.h"
#include "domain.h"

#include <fstream>
#include <algorithm>
#include <random>
#include <memory>

using namespace std;

vector<Tenant> Service::getAllTenants() const {
	return tenantRepo.findAll();
}

void Service::addTenant(const string name, const int flat_number, const int flat_size, const string flat_type) {
	Tenant newTenant(name, flat_number, flat_size, flat_type);
	tenantValidator.validate(newTenant);
	tenantRepo.add(newTenant);
	undoList.push_back(make_unique<AddAction>(newTenant, tenantRepo));
	notifyObservers();
}

void Service::removeTenant(const int flat_number) {
	const Tenant* deleted = tenantRepo.find(flat_number);
	if (deleted != nullptr) {
		undoList.push_back(make_unique<UndoRemove>(*deleted, tenantRepo));
	}
	tenantRepo.remove(flat_number);
	notifyObservers();
}

void Service::modifyTenant(const int flat_number, const string name) {
	const Tenant* modified = tenantRepo.find(flat_number);
	if (modified != nullptr) {
		undoList.push_back(make_unique<UndoModify>(*modified, tenantRepo));
	}
	tenantRepo.modify(flat_number, name);
	notifyObservers();
}

int Service::findFlat(const string name) const {
	return tenantRepo.findByName(name);
}

Tenant Service::find(const int flat_number) const {
	const Tenant* t = tenantRepo.find(flat_number);
	if (t != nullptr) {
		return *tenantRepo.find(flat_number);
	}
	else {
		return Tenant("", 0, 0, "");
	}
}

void Service::filterByType(vector<Tenant>& filtered, const string type) const {
	const auto& tenants = getAllTenants();
	copy_if(tenants.begin(), tenants.end(), back_inserter(filtered), [type](const Tenant& tenant) { return tenant.get_flat_type() == type; });
}

void Service::filterBySize(vector<Tenant>& filtered, const int size) const {
	const auto& tenants = getAllTenants();
	copy_if(tenants.begin(), tenants.end(), back_inserter(filtered), [size](const Tenant& tenant) noexcept { return tenant.get_flat_size() == size; });
}

vector<Tenant> Service::sortByName(const string direction) const {
	const int dir = direction == "asc" ? 1 : -1;
	vector<Tenant> tenants = getAllTenants();
	sort(tenants.begin(), tenants.end(), [dir](const Tenant& t1, const Tenant& t2) { return dir * t1.get_name().compare(t2.get_name()) < 0; });
	return tenants;
}

vector<Tenant> Service::sortBySize(const string direction) const {
	const int dir = direction == "asc" ? 1 : -1;
	vector<Tenant> tenants = getAllTenants();
	sort(tenants.begin(), tenants.end(), [dir](const Tenant& t1, const Tenant& t2) { return dir * (t1.get_flat_size() - t2.get_flat_size()) < 0; });
	return tenants;
}

int cmp_by_type_size(const Tenant& t1, const Tenant& t2) {
	int rez = t1.get_flat_type().compare(t2.get_flat_type());
	if (rez != 0) {
		return rez;
	}
	return t1.get_flat_size() - t2.get_flat_size();
}

vector<Tenant> Service::sortByTypeSize(const string direction) const {
	const int dir = direction == "asc" ? 1 : -1;
	vector<Tenant> tenants = getAllTenants();
	sort(tenants.begin(), tenants.end(), [dir](const Tenant& t1, const Tenant& t2) { return dir * cmp_by_type_size(t1, t2) < 0; });
	return tenants;
}

void Service::clearNotifications() noexcept {
	notificationRepo.clear();
	notifyObservers();
}

void Service::addNotification(const int flat_number) {
	const Notification new_notif(flat_number);
	undoList.push_back(make_unique<UndoAddNotif>(new_notif, notificationRepo));
	notificationRepo.add(new_notif);
	notifyObservers();
}

int Service::getNotificationsSize() const noexcept {
	return notificationRepo.size();
}

const vector<Notification>& Service::getNotifications() const noexcept {
	return notificationRepo.find_all();
}

void Service::generate(const unsigned int number_of) {
	notificationRepo.clear();
	const auto& tenants = getAllTenants();
	if (number_of > tenants.size()) {
		throw mystd::LogicError("number is greater than the size of list");
	}
	std::mt19937 mt{ std::random_device{}() };
	const std::uniform_int_distribution<> dist(0, tenants.size() - 1);
	int pos = 0;
	for (unsigned int i = 0; i < number_of; i++) {
		bool added = false;
		while (!added) {
			try {
				pos = dist(mt);
				notificationRepo.add(Notification(tenants.at(pos).get_flat_number()));
				added = true;
			}
			catch (const mystd::RepositoryError&) {
				added = false;
			}
		}
	}
	notifyObservers();
}

void Service::exportNotifications(const string file) const {
	ofstream out(file);
	if (out.fail()) {
		throw mystd::Exception("error opening file");
	}
	const auto& notifications = notificationRepo.find_all();
	for (const auto& notification : notifications) {
		out << notification.to_csv();
		const auto* tenant = tenantRepo.find(notification.get_flat_number());
		if (tenant != nullptr) {
			out << "," << tenant->to_csv();
		}
		out << endl;
	}
	out.close();
}

vector<TypeDto> Service::typeReport() const {
	vector<Tenant> tenants = tenantRepo.findAll();
	map<string, TypeDto> counts;
	for (const auto& tenant : tenants) {
		string type = tenant.get_flat_type();
		if (counts.find(type) == counts.end()) {
			counts.insert(pair<string, TypeDto>(type, TypeDto("", type, 1)));
		}
		else {
			counts.find(type)->second.inc_count();
		}
	}
	vector<TypeDto> vect;
	transform(counts.begin(), counts.end(),
		back_inserter(vect),
		[](const std::pair<string, TypeDto> &par) {
		return par.second;
	});
	vector<TypeDto> dtos;
	for (const auto& tenant : tenants) {
		TypeDto dto = counts.find(tenant.get_flat_type())->second;
		dto.set_id(tenant.get_name());
		dtos.push_back(dto);
	}
	return dtos;
}

int Service::calculateSum() const {
	return tenantRepo.sum();
}

void Service::undo() {
	if (undoList.size() == 0) {
		throw mystd::LogicError("no operations to be undone");
	}
	undoList.back()->do_undo();
	undoList.pop_back();
	notifyObservers();
}