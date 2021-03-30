#include "Ui.h"
#include "Service.h"
#include "exceptions.h"
#include "domain.h"

#include <iostream>
#include <string>

using namespace std;

void Ui::addTenant() const {
	cout << "\nNEW tenant \n";
	cout << "Tenant name: ";
	string name;
	getline(cin, name, '\n');
	getline(cin, name, '\n');
	cout << "Flat number: ";
	string number;
	cin >> number;
	const int flat_number = toInt(number, "flat number must be an integer \n");
	cout << "Flat size: ";
	string size;
	cin >> size;
	const int flat_size = toInt(size, "flat size must be an integer \n");
	cout << "Flat type: ";
	string flat_type;
	getline(cin, flat_type, '\n');
	getline(cin, flat_type, '\n');
	service.addTenant(name, flat_number, flat_size, flat_type);
	cout << "\nSuccessfully added !\n";
}

void Ui::deleteTenant() const {
	cout << "\nFlat number: ";
	string number;
	cin >> number;
	const int flat_number = toInt(number, "flat number must be an integer \n");
	service.removeTenant(flat_number);
	cout << "\nSuccessfully deleted !\n";
}

void Ui::modifyTenant() const {
	cout << "\nFlat number: ";
	string number;
	cin >> number;
	const int flat_number = toInt(number, "flat number must be an integer \n");
	string name;
	cout << "Name: ";
	getline(cin, name, '\n');
	getline(cin, name, '\n');
	service.modifyTenant(flat_number, name);
	cout << "\nSuccessfully modified !\n";
}

void Ui::searchForFlat() const {
	cout << "\nName: ";
	string name;
	getline(cin, name, '\n');
	getline(cin, name, '\n');
	const int flat_number = service.findFlat(name);
	if (flat_number == -1) {
		cout << "\nTenant not found ! \n";
	}
	else {
		cout << "\n" << name << " lives at flat: " << flat_number << "\n";
	}
}

void Ui::filterTenants() const {
	cout << "\nFilter by (type, size): ";
	string option;
	cin >> option;
	if (option == "type") {
		cout << "Type: ";
		string type;
		getline(cin, type, '\n');
		getline(cin, type, '\n');
		if (type != "garsoniera" && type != "o camera" && type != "doua camere" && type != "trei camere") {
			throw mystd::LogicError("type must be: garsoniera, o camera, doua camere, trei camere \n");
		}
		vector<Tenant> filtered;
		service.filterByType(filtered, type);
		printTenants(filtered);
	}
	else if (option == "size") {
		cout << "Size: ";
		string size;
		cin >> size;
		const int flat_size = toInt(size, "flat number must be an integer \n");
		vector<Tenant> filtered;
		service.filterBySize(filtered, flat_size);
		printTenants(filtered);
	}
	else {
		cout << "\nOption unavailable ! \n";
	}
}

void Ui::sortTenants() const {
	cout << "\nSort by (name, size, type and size): ";
	string option, direction;
	getline(cin, option, '\n');
	getline(cin, option, '\n');
	cout << "Direction (asc, desc): ";
	cin >> direction;
	if (direction != "asc" && direction != "desc") {
		throw mystd::LogicError("direction must be: asc, desc \n");
	}
	if (option == "name") {
		printTenants(service.sortByName(direction));
	}
	else if (option == "size") {
		printTenants(service.sortBySize(direction));
	}
	else if (option == "type and size") {
		printTenants(service.sortByTypeSize(direction));
	}
	else {
		cout << "\nOption unavailable ! \n";
	}
}

void Ui::manageNotifiedList() const {
	cout << "Commands available on the list: empty, add, generate, export, undo \nOption: ";
	string option;
	getline(cin, option, '\n');
	getline(cin, option, '\n');
	if (option == "empty") {
		service.clearNotifications();
		cout << "\nList emptied successfully ! \n";
	}
	else if (option == "add") {
		cout << "Flat number: ";
		string number;
		cin >> number;
		const int flat_number = toInt(number, "flat number must be an integer \n");
		service.addNotification(flat_number);
		cout << "\nTenant added successfully ! \n";
	}
	else if (option == "generate") {
		cout << "Number of: ";
		string number;
		cin >> number;
		const unsigned int number_of = toInt(number, "flat number must be an integer \n");
		service.generate(number_of);
		cout << "\nGenerated successfully ! \n";
	}
	else if (option == "export") {
		auto notifications = service.getNotifications();
		// export to file
		cout << "File name: ";
		string file;
		cin >> file;
		service.exportNotifications(file);
	}
	else {
		cout << "\nOption unavailable ! \n";
		return;
	}
	cout << "\nSize of the notified list: " << service.getNotificationsSize() << "\n";
}

void Ui::typeReport() const {
	vector<TypeDto> dtos = service.typeReport();
	for (const auto& dto : dtos) {
		cout << "Nume: " << dto.get_id() << ", Tip: " << dto.get_criteria() << ", Type count: " << dto.get_count() << "\n";
	}
}

void Ui::calculateSum() const {
	cout << "\nTotal size of the complex: " << service.calculateSum() << "\n";
}

void Ui::undo() const {
	service.undo();
	cout << "\nSuccessful Undo ! \n";
}

void Ui::printTenants(const vector<Tenant>& tenants) const {
	cout << "\nTenants (name, flat number, flat size, flat type): \n";
	for (const auto& tenant : tenants) {
		cout << tenant.to_string() << "\n";
	}
}

void Ui::printMenu() const {
	cout << "\nMenu: \n";
	cout << "1. Print all tenants \n";
	cout << "2. Add a new tenant \n";
	cout << "3. Delete a tenant \n";
	cout << "4. Modify a tenant \n";
	cout << "5. Find a flat by tenant \n";
	cout << "6. Filter tenants \n";
	cout << "7. Sort tenants \n";
	cout << "8. Manage notified tenants\n";
	cout << "9. Calculate sum \n";
	cout << "10. Type report \n";
	cout << "11. Undo last operation \n";
	cout << "12. Exit \n";
}

void Ui::run() const {
	int option = 0;
	string str;
	while (1) {
		printMenu();
		cout << "\nOption: ";
		cin >> str;
		try {
			option = toInt(str, "option must be an integer \n");
			switch (option) {
			case 1:
				printTenants(service.getAllTenants());
				break;
			case 2:
				addTenant();
				break;
			case 3:
				deleteTenant();
				break;
			case 4:
				modifyTenant();
				break;
			case 5:
				searchForFlat();
				break;
			case 6:
				filterTenants();
				break;
			case 7:
				sortTenants();
				break;
			case 8:
				manageNotifiedList();
				break;
			case 9:
				calculateSum();
				break;
			case 10:
				typeReport();
				break;
			case 11:
				undo();
				break;
			case 12:
				return;
			default:
				cout << "\nOption is unspecified\n";
			}
		}
		catch (mystd::Exception err) {
			cout << "\nErrors: \n" << err.what() << "RETRY !\n";
		}
	}
}