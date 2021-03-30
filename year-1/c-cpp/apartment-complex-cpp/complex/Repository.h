#pragma once

#include "domain.h"

#include <vector>
#include <map>
#include <string>
#include <iterator>

using namespace std;
using std::string;

class Repository
{
public:
	virtual void add(const Tenant& tenant) = 0;

	virtual void remove(const int flat_number) = 0;

	virtual void modify(const int flat_number, const string name) = 0;

	virtual const Tenant* find(const int flat_number) = 0;

	virtual int findByName(const string name) const = 0;

	virtual const vector<Tenant> findAll() const = 0;

	virtual const int sum() const = 0;

	virtual ~Repository() = default;
};

class TenantRepository : public Repository
{
private:
	map<int, Tenant> tenants;
public:
	/*
		Adds a new tenant to the repository
		tenant - a Tenant object
	*/
	void add(const Tenant& tenant) override;

	/*
		Removes a tenant from the repository
		flat_number - integer number
	*/
	void remove(const int flat_number) override;

	/*
		Modifies a tenant's name, by flat number
		flat_number - integer number
		name - string
	*/
	void modify(const int flat_number, const string name) override;

	/*
		Finds a tenant in the repository by flat number
		flat_number - vonstant integer value
		Returns a pointer to a Tenant object
	*/
	const Tenant* find(const int flat_number) override;

	/*
		Finds a flat's number by the tenant's name
		name - constant string
		Returns the flat integer number
	*/
	int findByName(const string name) const override;

	/*
		Returns all the tenants
	*/
	const vector<Tenant> findAll() const override;

	/*
		Computes the sum of all the flat sizes
		Returns an integer number
	*/
	const int sum() const override;

	/*
		Default virtual constructor
	*/
	~TenantRepository() = default;
};

class FileTenantRepository : public TenantRepository
{
private:
	const string filename;

	/*
		Adds the tenants present in the file to repository when object is created
	*/
	void loadFromFile();

	/*
		Saves the tenants to the file
	*/
	void saveToFile();
public:
	/*
		Constructor for the FileTenantRepository object
		file - a string
	*/
	FileTenantRepository(const string filename) : filename{ filename }, TenantRepository() {
		loadFromFile();
	}

	/*
		Override for the add function in TenantRepository, also saves to file
		tenant - a Tenant object
	*/
	void add(const Tenant& tenant) override {
		TenantRepository::add(tenant);
		saveToFile();
	}

	/*
		Override for the remove function in TenantRepository, also removes from file
		flat_number - integer number
	*/
	void remove(const int flatnumber) override {
		TenantRepository::remove(flatnumber);
		saveToFile();
	}

	/*
		Override for the modify function in TenantRepository, also modifies in file
		flat_number - integer number
		name - string
	*/
	void modify(const int flat_number, const string name) override {
		TenantRepository::modify(flat_number, name);
		saveToFile();
	}
};

class ProbabilityRepository : public TenantRepository
{
private:
	const double probability;

	void raiseIf() const;
public:
	ProbabilityRepository(const double probability) : probability{ probability }, TenantRepository() { }

	/*
		Adds a new tenant to the repository
		tenant - a Tenant object
	*/
	void add(const Tenant& tenant) override {
		raiseIf();
		TenantRepository::add(tenant);
	}

	/*
		Removes a tenant from the repository
		flat_number - integer number
	*/
	void remove(const int flat_number) override {
		raiseIf();
		TenantRepository::remove(flat_number);
	}

	/*
		Modifies a tenant's name, by flat number
		flat_number - integer number
		name - string
	*/
	void modify(const int flat_number, const string name) override {
		raiseIf();
		TenantRepository::modify(flat_number, name);
	}

	/*
		Finds a tenant in the repository by flat number
		flat_number - vonstant integer value
		Returns a pointer to a Tenant object
	*/
	const Tenant* find(const int flat_number) override {
		raiseIf();
		return TenantRepository::find(flat_number);
	}

	/*
		Finds a flat's number by the tenant's name
		name - constant string
		Returns the flat integer number
	*/
	int findByName(const string name) const override {
		raiseIf();
		return TenantRepository::findByName(name);
	}

	/*
		Computes the sum of all the flat sizes
		Returns an integer number
	*/
	const int sum() const override {
		raiseIf();
		return TenantRepository::sum();
	}
};

class NotificationRepository
{
private:
	vector<Notification> notifications;
public:
	/*
		Adds a new Notification object to the repository
		notif - a Notification object
	*/
	void add(const Notification& notif);

	/*
		Deletes all the notifications in the repository
	*/
	void clear() noexcept;

	/*
		Gets the size of the notifications list
		Returns an integer number
	*/
	int size() const noexcept;

	/*
		Returns all the notifications
	*/
	vector<Notification>& find_all() noexcept;
};