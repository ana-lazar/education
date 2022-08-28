#include "domain.h"
#include "exceptions.h"

#include <iostream>
#include <string>

using namespace std;

int toInt(string value, string err_text) {
	try {
		return stoi(value);
	}
	catch (std::invalid_argument) {
		throw mystd::LogicError(err_text);
	}
}

string Tenant::to_string() const {
	return std::to_string(flat_number) + " " + name + " " + std::to_string(flat_size) + " " + flat_type;
}

string Tenant::to_csv() const {
	return name + "," + std::to_string(flat_size) + "," + flat_type;
}

int Tenant::get_flat_number() const noexcept {
	return flat_number;
}

void Tenant::set_name(const string new_name) {
	this->name = new_name;
}

string Tenant::get_name() const {
	return name;
}

int Tenant::get_flat_size() const noexcept {
	return flat_size;
}

string Tenant::get_flat_type() const {
	return flat_type;
}

void Tenant::operator=(const Tenant& t) {
	name = t.name;
	flat_type = t.flat_type;
	flat_size = t.flat_size;
	flat_number = t.flat_number;
}