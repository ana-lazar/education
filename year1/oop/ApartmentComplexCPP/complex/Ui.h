#pragma once

#include "Service.h"

using namespace std;

class Ui
{
private:
	Service& service;

	/*
		Prints the available menu
	*/
	void printMenu() const;

	/*
		Prints tenants
	*/
	void printTenants(const vector<Tenant>& tenants) const;

	/*
		Adds a new tenant to the list
	*/
	void addTenant() const;

	/*
		Deletes a tenant from the list
	*/
	void deleteTenant() const;

	/*
		Modifies a tenant from the list
	*/
	void modifyTenant() const;

	/*
		Searches for a flat's attributes by the tenant's name
	*/
	void searchForFlat() const;

	/*
		Filters tenants by a certain criteria
	*/
	void filterTenants() const;

	/*
		Sorts tenants by a certain criteria
	*/
	void sortTenants() const;

	/*
		Manages the list of notified tenants
	*/
	void manageNotifiedList() const;

	/*
		Computes the sum of the flat sizes
	*/
	void calculateSum() const;

	/*
		Prints how many of each type exist
	*/
	void typeReport() const;

	/*
		Undoes the last operation
	*/
	void undo() const;
public:
	/*
		Constructor for the Ui object
		repository - reference to a Repository object
	*/
	Ui(Service& service) noexcept : service{ service } { };

	/*
		Proceeds with the running of the User Interface
	*/
	void run() const;

	/*
		Destructor for a Ui object
	*/
	~Ui() = default;
};