#pragma once

#include <iostream>
#include <string>

using namespace std;

/*
	Converts a string to an integer number, if possible
	value, err_text - std::string's
	Returns an integer number
	Throws LogicError if the string cannot be converted
*/
int toInt(string value, string err_text);

class Tenant
{
private:
	int flat_number = 0;
	int flat_size = 0;
	string name, flat_type;
public:
	/*
		Constructor for a Tenant object with initializing
		name, type - strings
		number, size - integer numbers
	*/
	Tenant(const string name, const int flat_number, const int flat_size, const string flat_type) : name{ name }, flat_type{ flat_type } {
		this->flat_number = flat_number;
		this->flat_size = flat_size;
	}

	// Default constructor for Tenant object
	Tenant() = default;

	// Copy constructor for Tenant object
	Tenant(const Tenant& t) : name{ t.name }, flat_type{ t.flat_type }, flat_number{ t.flat_number }, flat_size{ t.flat_size } { }

	/*
		Overrides the assignment (=) operator for Tenant objects
		t - reference to a Tenant object
	*/
	void operator=(const Tenant& t);

	// Deleting the move constructor and assignment to avoid warnings
	Tenant(Tenant&& t) = default;
	Tenant& operator=(Tenant&& t) = default;

	/*
		Get function for flat_number
		Returns an integer number
	*/
	int get_flat_number() const noexcept;

	/*
		Get function for name
		Returns a string
	*/
	string get_name() const;

	/*
		Get function for flat_size
		Returns an integer number
	*/
	int get_flat_size() const noexcept;

	/*
		Get function for flat_type
		Returns a string
	*/
	string get_flat_type() const;

	/*
		Setter function for name
		name - a string
	*/
	void set_name(const string name);

	/*
		Returns a tenant's fields to be printed
	*/
	string to_string() const;

	/*
		Returns a tenant's fields to be exported
	*/
	string to_csv() const;

	// Default destructor for Tenant object
	~Tenant() = default;
};

class TypeDto
{
private:
	string id;
	const string criteria;
	int count;
public:
	TypeDto(const string id, const string criteria, const int count) : id{ id }, criteria{ criteria }, count{ count } { }

	string get_id() const { return id; }

	string get_criteria() const { return criteria; }

	int get_count() const noexcept { return count; }

	void inc_count() noexcept { count++; }

	void set_id(const string newid) { this->id = newid; }
};

class Notification
{
private:
	int flat_number;
public:
	// Constructor for a Notification object
	Notification(const int flat_number) noexcept : flat_number{ flat_number } { }

	/*
		Get function for flat_number
		Returns an integer number
	*/
	int get_flat_number() const noexcept { return flat_number; }

	/*
		Returns a notification's fields to be exported
	*/
	string to_csv() const { return to_string(flat_number); }
};