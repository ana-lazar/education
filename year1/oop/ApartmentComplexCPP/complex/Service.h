#pragma once

#include "Repository.h"
#include "Validator.h"
#include "undos.h"
#include "observer.h"

using namespace std;

class Service : public Observable
{
private:
	Repository& tenantRepo;
	NotificationRepository& notificationRepo;
	const TenantValidator& tenantValidator;
	vector<unique_ptr<Action>> undoList;
public:
	/*
		Constructor for the Service object
		repository - reference to a Repository object
	*/
	Service(Repository& tenantRepo, NotificationRepository& notificationRepo, const TenantValidator tenantValidator) noexcept : tenantRepo{ tenantRepo }, notificationRepo{ notificationRepo }, tenantValidator{ tenantValidator } { }

	/*
		Returns a vector of all the tenants
	*/
	vector<Tenant> getAllTenants() const;

	/*
		Adds a new tenant to the list
		name, flatType - strings
		flat_number, flat_size - integer numbers
	*/
	void addTenant(const string name, const int flat_number, const int flat_size, const string flat_type);

	/*
		Removes a tenant from the repository by flat_number
		flat_numer - integer number
	*/
	void removeTenant(const int flat_number);

	/*
		Modifies a tenant from the repository by flatNumber
		name - string
	*/
	void modifyTenant(const int flat_number, const string name);

	/*
		Searches for a flat's number by the tenant's name
		name - a string
		Returns an integer number
	*/
	int findFlat(const string name) const;

	/*
		Searches for a flat's fields by the flat number
		flat_number - an integer number
		Returns a pointer to a Tenant object
	*/
	Tenant find(const int flat_number) const;

	/*
		Filters tenants by flat type
		type - constant string
		Returns the filtered vector of Tenant objects
	*/
	void filterByType(vector<Tenant>& filtered, const string type) const;

	/*
		Filters tenants by flat size
		size - integer number
		Returns the filtered vector of Tenant objects
	*/
	void filterBySize(vector<Tenant>& filtered, const int size) const;

	/*
		Sorts the tenants by name
		Returns a vector containing the sorted tenants
	*/
	vector<Tenant> sortByName(const string direction) const;

	/*
		Sorts the tenants by size
		Returns a vector containing the sorted tenants
	*/
	vector<Tenant> sortBySize(const string direction) const;

	/*
		Sorts the tenants by type and size
		Returns a vector containing the sorted tenants
	*/
	vector<Tenant> sortByTypeSize(const string direction) const;

	/*
		Deletes all the tenants in the notified list
	*/
	void clearNotifications() noexcept;

	/*
		Adds a new Tenant object to the notified list
		flat_number - constant integer number
	*/
	void addNotification(const int flat_number);

	/*
		Gets the size of the notified list
		Returns an integer number
	*/
	int getNotificationsSize() const noexcept;

	/*
		Returns all the notified tenants
	*/
	const vector<Notification>& getNotifications() const noexcept;

	/*
		Randomly generates a number of notified tenants
		number_of - constant integer number
	*/
	void generate(const unsigned int number_of);

	/*
		Exports all the notified flats to a given file
		file - constant string
	*/
	void exportNotifications(const string file) const;

	/*
		Computes the sum of the flat sizes
	*/
	int calculateSum() const;

	/*
		Finds how many of each type exist
	*/
	vector<TypeDto> typeReport() const;

	/*
		Undoes the last operation
	*/
	void undo();
};