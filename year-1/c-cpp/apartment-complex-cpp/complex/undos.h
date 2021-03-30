#pragma once

#include "domain.h"
#include "Repository.h"

/*
	Abstract class UndoAction
*/
class Action
{
public:
		/*
			Virtual function, to be defined by derivates
		*/
	virtual void do_undo() = 0;

	/*
		Virtual destructor
	*/
	virtual ~Action() = default;
};

class AddAction : public Action
{
private:
	Repository& repository;
	const Tenant tenant;
public:
	/*
		Constructor for UndoAdd object
	*/
	AddAction(const Tenant& tenant, Repository& repository) : tenant{ tenant }, repository{ repository } { }

	/*
		Calls the remove function for my tenant
	*/
	void do_undo() override {
		repository.remove(tenant.get_flat_number());
	}

	/*
		Default destructor for UndoAdd object
	*/
	~AddAction() = default;
};

class UndoRemove : public Action
{
private:
	Repository& repository;
	const Tenant tenant;
public:
	/*
		Constructor for UndoRemove object
	*/
	UndoRemove(const Tenant tenant, Repository& repository) : tenant{ tenant }, repository{ repository } { }

	/*
		Calls the remove function for my tenant
	*/
	void do_undo() override {
		repository.add(tenant);
	}
};

class UndoModify : public Action
{
private:
	Repository& repository;
	const Tenant tenant;
public:
	/*
		Constructor for UndoModify object
	*/
	UndoModify(const Tenant& tenant, Repository& repository) : tenant{ tenant }, repository{ repository } { }

	/*
		Calls the remove function for my tenant
	*/
	void do_undo() override {
		repository.modify(tenant.get_flat_number(), tenant.get_name());
	}
};

class UndoAddNotif : public Action
{
private:
	NotificationRepository& repository;
	const Notification notif;
public:
	/*
		Constructor for UndoAddNotif object
	*/
	UndoAddNotif(const Notification notif, NotificationRepository& repository) noexcept : notif{ notif }, repository{ repository } { }

	/*
		Removes notification from list
	*/
	void do_undo() override {
		vector<Notification>& all = repository.find_all();
		for (unsigned int i = 0; i < all.size(); i++) {
			if (all.at(i).get_flat_number() == notif.get_flat_number()) {
				all.erase(all.begin() + i);
				return;
			}
		}
	}
};