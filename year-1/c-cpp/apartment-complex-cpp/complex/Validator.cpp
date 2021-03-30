#include "Validator.h"
#include "exceptions.h"

#include <vector>

using namespace std;

void TenantValidator::validate(const Tenant& tenant) const {
	vector<string> errors;
	if (tenant.get_name() == "") {
		errors.push_back("name must be not empty");
	}
	if (tenant.get_flat_size() <= 0) {
		errors.push_back("flat size must be a positive integer");
	}
	if (tenant.get_flat_number() <= 0 || tenant.get_flat_number() > 100) {
		errors.push_back("flat number must be an integer between 1 and 100");
	}
	if (tenant.get_flat_type() != "garsoniera" && tenant.get_flat_type() != "o camera" && tenant.get_flat_type() != "doua camere" && tenant.get_flat_type() != "trei camere") {
		errors.push_back("flat type must be: garsoniera, o camera, doua camere, trei camere");
	}
	if (errors.size() != 0) {
		string all = "";
		for (auto error : errors) {
			all += error + "; ";
		}
		throw mystd::ValidatorError(all);
	}
}