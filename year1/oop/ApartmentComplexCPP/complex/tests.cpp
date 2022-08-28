#include "Service.h"
#include "Repository.h"
#include "Validator.h"
#include "exceptions.h"
#include "domain.h"

#include <iostream>
#include <cassert>
#include <fstream>

void test_domain() {
	Tenant tenant("Ana", 23, 100, "garsoniera");
	assert(tenant.get_flat_number() == 23);
	assert(tenant.get_name() == "Ana");
	assert(tenant.get_flat_size() == 100);
	Tenant copy;
	copy = tenant;
	assert(copy.get_flat_number() == 23);
	assert(copy.get_name() == "Ana");
	assert(copy.get_flat_size() == 100);
	tenant.set_name("Patri");
	assert(tenant.get_name() == "Patri");
	assert(tenant.to_string() == "23 Patri 100 garsoniera");
	const Notification notif(23);
	assert(notif.get_flat_number() == 23);
}

void test_validator() {
	TenantValidator validator;
	Tenant tenant("", 23, 100, "garsoniera");
	try {
		validator.validate(tenant);
		assert(false);
	}
	catch (mystd::ValidatorError& err) {
		assert(err.what() == "name must be not empty; ");
	}
	Tenant tenant2("Ana", -1, 0, "papa");
	try {
		validator.validate(tenant2);
		assert(false);
	}
	catch (mystd::ValidatorError& err) {
		assert(err.what() == "flat size must be a positive integer; flat number must be an integer between 1 and 100; flat type must be: garsoniera, o camera, doua camere, trei camere; ");
	}
}

void add_to_repo(TenantRepository& repository) {
	Tenant tenant1("Ana Lazar", 23, 100, "garsoniera");
	Tenant tenant2("Bianca Popescu", 12, 200, "doua camere");
	repository.add(tenant1);
	repository.add(tenant2);
}

void test_find_all() {
	TenantRepository repository;
	add_to_repo(repository);
	vector<Tenant> all = repository.findAll();
	assert(all.size() == 2);
}

void test_add() {
	TenantRepository repository;
	assert(repository.findAll().size() == 0);
	Tenant tenant1("Ana Lazar", 23, 100, "garsoniera");
	Tenant tenant2("Bianca Popescu", 12, 200, "doua camere");
	Tenant tenant3("bibi", 12, 2, "trei camere");
	repository.add(tenant1);
	assert(repository.findAll().size() == 1);
	repository.add(tenant2);
	assert(repository.findAll().size() == 2);
	try {
		repository.add(tenant3);
		assert(false);
	}
	catch (mystd::RepositoryError& err) {
		assert(err.what() == "tenant already existent");
	}
}

void test_remove() {
	TenantRepository repository;
	add_to_repo(repository);
	assert(repository.findAll().size() == 2);
	repository.remove(23);
	assert(repository.findAll().size() == 1);
	try {
		repository.remove(10);
		assert(false);
	}
	catch (mystd::RepositoryError& err) {
		assert(err.what() == "flat number inexistent");
	}
	assert(repository.findAll().size() == 1);
}

void test_modify() {
	TenantRepository repository;
	add_to_repo(repository);
	repository.modify(23, "Maria Gheorghe");
	assert(repository.findByName("Maria Gheorghe") == 23);
	try {
		repository.modify(20, "P");
		assert(false);
	}
	catch (mystd::RepositoryError& err) {
		assert(err.what() == "flat number inexistent");
	}
}

void test_find() {
	TenantRepository repository;
	add_to_repo(repository);
	assert(repository.findByName("Ana Lazar") == 23);
	assert(repository.findByName("Bianca Popescu") == 12);
	assert(repository.findByName("Maria Gheorghe") == -1);
	assert(repository.find(23)->get_name() == "Ana Lazar");
	assert(repository.find(100) == nullptr);
}

void test_notifications() {
	NotificationRepository repository;
	assert(repository.size() == 0);
	repository.add(Notification(10));
	repository.add(Notification(23));
	assert(repository.size() == 2);
	const auto& notifications = repository.find_all();
	assert(notifications.at(0).get_flat_number() == 10);
	assert(notifications.at(1).get_flat_number() == 23);
	repository.clear();
	assert(repository.size() == 0);
	repository.clear();
	assert(repository.size() == 0);
}

void test_repository() {
	test_find_all();
	test_add();
	test_remove();
	test_modify();
	test_find();
	test_notifications();
}

void test_get_all_tenants() {
	TenantRepository tenantRepo;
	NotificationRepository notificationRepo;
	add_to_repo(tenantRepo);
	const TenantValidator validator;
	const Service service(tenantRepo, notificationRepo, validator);
	vector<Tenant> all = service.getAllTenants();
	assert(all.size() == 2);
}

void test_add_tenant() {
	TenantRepository tenantRepo;
	NotificationRepository notificationRepo;
	add_to_repo(tenantRepo);
	const TenantValidator validator;
	Service service(tenantRepo, notificationRepo, validator);
	assert(service.getAllTenants().size() == 2);
	service.addTenant("Glovo Alex", 10, 80, "o camera");
	assert(service.getAllTenants().size() == 3);
	service.addTenant("Farcas Victor", 11, 1200, "trei camere");
	assert(service.getAllTenants().size() == 4);
	assert(service.findFlat("Farcas Victor") == 11);
}

void test_remove_tenant() {
	TenantRepository tenantRepo;
	NotificationRepository notificationRepo;
	add_to_repo(tenantRepo);
	const TenantValidator validator;
	Service service(tenantRepo, notificationRepo, validator);
	assert(service.getAllTenants().size() == 2);
	service.removeTenant(23);
	assert(service.getAllTenants().size() == 1);
	try {
		service.removeTenant(10);
		assert(false);
	}
	catch (mystd::RepositoryError) {
		assert(true);
	}
	assert(service.getAllTenants().size() == 1);
}

void test_modify_tenant() {
	TenantRepository tenantRepo;
	NotificationRepository notificationRepo;
	add_to_repo(tenantRepo);
	const TenantValidator validator;
	Service service(tenantRepo, notificationRepo, validator);
	service.modifyTenant(23, "Maria Gheorghe");
	assert(service.findFlat("Maria Gheorghe") == 23);
	try {
		tenantRepo.modify(20, "P");
		assert(false);
	}
	catch (mystd::RepositoryError& err) {
		assert(err.what() == "flat number inexistent");
	}
}

void test_find_flat() {
	TenantRepository tenantRepo;
	NotificationRepository notificationRepo;
	add_to_repo(tenantRepo);
	const TenantValidator validator;
	const Service service(tenantRepo, notificationRepo, validator);
	assert(service.findFlat("Ana Lazar") == 23);
	assert(service.findFlat("Bianca Popescu") == 12);
	assert(service.findFlat("Maria Gheorghe") == -1);
}

void test_filter_by_type() {
	TenantRepository tenantRepo;
	NotificationRepository notificationRepo;
	add_to_repo(tenantRepo);
	const TenantValidator validator;
	Service service(tenantRepo, notificationRepo, validator);
	service.addTenant("Azul", 10, 90, "doua camere");
	vector<Tenant> filtered1;
	service.filterByType(filtered1, "doua camere");
	assert(filtered1.size() == 2);
	vector<Tenant> filtered2;
	service.filterByType(filtered2, "garsoniera");
	assert(filtered2.size() == 1);
	vector<Tenant> filtered3;
	service.filterByType(filtered3, "abc");
	assert(filtered3.size() == 0);
}

void test_filter_by_size() {
	TenantRepository tenantRepo;
	NotificationRepository notificationRepo;
	add_to_repo(tenantRepo);
	const TenantValidator validator;
	Service service(tenantRepo, notificationRepo, validator);
	service.addTenant("Azul", 10, 100, "doua camere");
	vector<Tenant> filtered1;
	service.filterBySize(filtered1, 100);
	assert(filtered1.size() == 2);
	vector<Tenant> filtered2;
	service.filterBySize(filtered2, 200);
	assert(filtered2.size() == 1);
	vector<Tenant> filtered3;
	service.filterBySize(filtered3, 90);
	assert(filtered3.size() == 0);
}

void test_sort_by_name() {
	TenantRepository tenantRepo;
	NotificationRepository notificationRepo;
	add_to_repo(tenantRepo);
	const TenantValidator validator;
	Service service(tenantRepo, notificationRepo, validator);
	service.addTenant("Azul", 10, 90, "doua camere");
	vector<Tenant> sorted = service.sortByName("asc");
	assert(sorted.at(0).get_flat_number() == 23);
	sorted = service.sortByName("desc");
	assert(sorted.at(0).get_flat_number() == 12);
}

void test_sort_by_size() {
	TenantRepository tenantRepo;
	NotificationRepository notificationRepo;
	add_to_repo(tenantRepo);
	const TenantValidator validator;
	Service service(tenantRepo, notificationRepo, validator);
	service.addTenant("Azul", 10, 90, "doua camere");
	vector<Tenant> sorted = service.sortBySize("asc");
	assert(sorted.at(0).get_flat_number() == 10);
	vector<Tenant> sorted2 = service.sortBySize("desc");
	assert(sorted2.at(0).get_flat_number() == 12);
}

void test_sort_by_type_size() {
	TenantRepository tenantRepo;
	NotificationRepository notificationRepo;
	add_to_repo(tenantRepo);
	const TenantValidator validator;
	Service service(tenantRepo, notificationRepo, validator);
	service.addTenant("Azul", 10, 90, "doua camere");
	vector<Tenant> sorted = service.sortByTypeSize("asc");
	assert(sorted.at(0).get_flat_number() == 10);
	vector<Tenant> sorted2 = service.sortByTypeSize("desc");
	assert(sorted2.at(0).get_flat_number() == 23);
}

void test_notif_list() {
	TenantRepository tenantRepo;
	NotificationRepository notificationRepo;
	add_to_repo(tenantRepo);
	const TenantValidator validator;
	Service service(tenantRepo, notificationRepo, validator);
	service.addNotification(10);
	try {
		service.addNotification(10);
		assert(false);
	}
	catch (const mystd::RepositoryError&) {
		assert(true);
	}
	service.addNotification(23);
	assert(service.getNotificationsSize() == 2);
	const auto& notifications = service.getNotifications();
	assert(notifications.at(0).get_flat_number() == 10);
	assert(notifications.at(1).get_flat_number() == 23);
	service.exportNotifications("testsn.txt");
	try {
		service.exportNotifications("notifi,ca .<|tions.txt");
		assert(false);
	}
	catch (const mystd::Exception&) {
		assert(true);
	}
	ifstream in("testsn.txt");
	if (in.fail()) {
		assert(false);
	}
	string text;
	getline(in, text, '\n');
	assert(text == "10");
	getline(in, text, '\n');
	assert(text == "23,Ana Lazar,100,garsoniera");
	in.close();
	try {
		service.generate(10);
		assert(false);
	}
	catch (const mystd::LogicError&) {
		assert(true);
	}
	service.generate(2);
	service.generate(2);
	service.generate(2);
	assert(service.getNotificationsSize() == 2);
	service.clearNotifications();
	assert(service.getNotificationsSize() == 0);
}

void test_type_report() {
	TenantRepository tenantRepo;
	NotificationRepository notificationRepo;
	add_to_repo(tenantRepo);
	tenantRepo.add(Tenant("ana", 100, 1000, "garsoniera"));
	const TenantValidator validator;
	const Service service(tenantRepo, notificationRepo, validator);
	vector<TypeDto> dtos = service.typeReport();
	const TypeDto dto("ana", "bala", 0);
	assert(dto.get_count() == 0);
	assert(dto.get_criteria() == "bala");
	assert(dto.get_id() == "ana");
}

void test_sum() {
	TenantRepository tenantRepo;
	NotificationRepository notificationRepo;
	add_to_repo(tenantRepo);
	const TenantValidator validator;
	const Service service(tenantRepo, notificationRepo, validator);
	assert(service.calculateSum() == 300);
	assert(tenantRepo.sum() == 300);
}

void test_service() {
	test_get_all_tenants();
	test_add_tenant();
	test_remove_tenant();
	test_modify_tenant();
	test_find_flat();
	test_filter_by_type();
	test_filter_by_size();
	test_sort_by_name();
	test_sort_by_size();
	test_sort_by_type_size();
	test_notif_list();
	test_type_report();
	test_sum();
}

void test_file_repository() {
	ofstream out("tests.txt");
	out << "Ana,10,90,garsoniera\nAna,10,90,garsoniera\n";
	out.close();
	try {
		FileTenantRepository repository(".txt");
		assert(false);
	}
	catch (const mystd::Exception&) {
		assert(true);
	}
	FileTenantRepository repository("tests.txt");
	Tenant tenant1("Ana Lazar", 23, 100, "garsoniera");
	repository.remove(10);
	repository.add(tenant1);
	ifstream in("tests.txt");
	string name, flat_type, num;
	int flat_size = 0, flat_number = 0;
	getline(in, name, ',');
	getline(in, num, ',');
	flat_number = stoi(num);
	getline(in, num, ',');
	flat_size = stoi(num);
	getline(in, flat_type, '\n');
	in.close();
	assert(name == "Ana Lazar");
	assert(flat_number == 23);
	assert(flat_type == "garsoniera");
	assert(flat_size == 100);
	repository.modify(23, "Ana");
	ifstream iiin("tests.txt");
	getline(iiin, name, ',');
	assert(name == "Ana");
	iiin.close();
	repository.remove(23);
	ifstream iin("tests.txt");
	getline(iin, name, '\n');
	assert(name == "");
	iin.close();
	ofstream oout("tests.txt");
	oout.close();
}

void test_undo() {
	TenantRepository tenantRepo;
	NotificationRepository notificationRepo;
	const TenantValidator validator;
	Service service(tenantRepo, notificationRepo, validator);
	try {
		service.undo();
		assert(false);
	}
	catch (const mystd::Exception&) {
		assert(true);
	}
	service.addTenant("Azul", 10, 90, "doua camere");
	service.addTenant("Ana", 19, 100, "garsoniera");
	service.addTenant("Patricia", 6, 210, "doua camere");
	service.undo();
	assert(service.getAllTenants().size() == 2);
	assert(service.findFlat("Patricia") == -1);
	service.removeTenant(19);
	assert(service.getAllTenants().size() == 1);
	assert(service.findFlat("Ana") == -1);
	service.undo();
	assert(service.getAllTenants().size() == 2);
	assert(service.findFlat("Ana") == 19);
	service.modifyTenant(10, "Lazar");
	assert(service.findFlat("Lazar") == 10);
	assert(service.findFlat("Azul") == -1);
	service.undo();
	assert(service.findFlat("Lazar") == -1);
	assert(service.findFlat("Azul") == 10);
}

void test_all() {
	test_domain();
	test_validator();
//	test_repository();
//	test_file_repository();
	test_service();
	test_undo();
}