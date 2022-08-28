#pragma once

#include <QtWidgets/qwidget.h>
#include <QtWidgets/qlistwidget.h>
#include <QtWidgets/qpushbutton.h>
#include <QtWidgets/qboxlayout.h>
#include <QtWidgets/qstackedlayout.h>
#include <QtWidgets/qcombobox.h>
#include <QtWidgets/qformlayout.h>
#include <QtWidgets/qlineedit.h>
#include <QtWidgets/qlabel.h>
#include <QtWidgets/qtableview.h>

#include "Service.h"
#include "model.h"
#include "observer.h"

using std::vector;

class MainWindow : public QWidget, public Observer
{
private:
	Service& service;

	MyModel* model;
	QTableView* table;
	QListWidget* master;
	QPushButton* okButton;
	QPushButton* cancelButton;
	QLineEdit* nameLine;
	QLineEdit* flatnumberLine;
	QLineEdit* flattypeLine;
	QLineEdit* flatsizeLine;
	QComboBox* sortCriteriaCombo;
	QComboBox* sortOrderCombo;
	QComboBox* filterCriteriaCombo;
	QLineEdit* filterTextLine;
	QLineEdit* optionLine;
	QVBoxLayout* dynamicLayout;
	bool isEditing;

	/*
		Creates the Add, Delete, Edit layout
		Returns a pointer to the QHBoxLayout object
	*/
	QHBoxLayout* createCrudLayout();

	/*
		Creates the sort layout
		Returns a pointer to the QHBoxLayout object
	*/
	QHBoxLayout* createSortLayout();

	/*
		Creates the filter layout
		Returns a pointer to the QHBoxLayout object
	*/
	QHBoxLayout* createFilterLayout();

	/*
		Creates the details buttons layout
		Returns a pointer to the QHBoxLayout object
	*/
	QHBoxLayout* createDetailsButtonLayout();

	/*
		Creates the master buttons layout
		Returns a pointer to the QVBoxLayout object
	*/
	QVBoxLayout* createMasterButtonLayout();

	/*
		Creates the master layout
		Returns a pointer to the QVBoxLayout object
	*/
	QVBoxLayout* createMasterLayout();

	/*
		Creates the details layout
		Returns a pointer to the QvBoxLayout object
	*/
	QVBoxLayout* createDetailsLayout();

	/*
		Creates the notification buttons layout
		Returns a pointer to the QHBoxLayout object
	*/
	QHBoxLayout* createNotifsLayout();

	/*
		Creates the form layout
		Returns a pointer to the QFormLayout object
	*/
	QFormLayout* createFormLayout();

	/*
		Creates the about layout
		Returns a pointer to the QVBoxLayout object
	*/
	QWidget* createAboutWidget();
	
	/*
		Creates the dynamic buttons layout
		Returns a pointer to the QVBoxLayout object
	*/
	QWidget* createDynamicWidget();

	QHBoxLayout* createTableView();
	
	/*
		Creates the secondary layout
		Returns a pointer to the QHBoxLayout object
	*/
	QHBoxLayout* createSecondaryLayout();

	/*
		Initializes the layouts and the bonds between them
	*/
	void initLayout();

	/*
		Loads the master according to a tenant list
	*/
	void loadMaster(const vector<Tenant>& tenants);

	/*
		Loads the dunamic buttons according to a tenant list
	*/
	void loadButtons(const vector<Tenant>& tenants);

	/*
		Shows details to the current item in master
	*/
	void showDetails();

	/*
		Updates the GUI according to the add functionality
	*/
	void addTenant();

	/*
		Deletes a tenant from the tenant list and master
	*/
	void deleteTenant();

	/*
		Updates the GUI according to the edit functionality
	*/
	void editTenant();

	/*
		Undoes the last operation made
	*/
	void undo();
	
	/*
		Opens the notifications window
	*/
	void manageNotifications();

	/*
		Opens the read only notifications window
	*/
	void readOnlyNotifs();

	/*
		Clears the form layout lines
	*/
	void clearLines();

	/*
		Saves changes made in add and edit mode
	*/
	void saveChange();

	/*
		Cancels changes made in add and edit mode
	*/
	void cancelChange();

	/*
		Sorts the master by a criteria
	*/
	void sortBy();

	/*
		Filters the master by a criteria
	*/
	void filterBy();

	/*
		Creates the add dialog
	*/
	void createAddDialog();

	/*
		Adds a notification to the list
	*/
	void addNotification();

	/*
		Clears the notification list
	*/
	void clearNotifications();

	/*
		Creates the generate dialog
	*/
	void createGenerateDialog();

	/*
		Generates a number of notifications and adds them to the list
	*/
	void generateNotifications();

	/*
		Creates the export dialog
	*/
	void createExportDialog();

	/*
		Exports the notifications to a given file
	*/
	void exportNotifications();

	/*
		Updates the gui data
	*/
	void update() override {
		loadMaster(service.getAllTenants());
	}
public:
	/*
		Constructor for the MainWindow object
		service - reference to a Service object
		Initializes the whole layout
	*/
	MainWindow(Service& service) : service(service) {
		setWindowTitle("Tenant Manager APP");
		setWindowIcon(QIcon("icon.ico"));
		initLayout();
		isEditing = false;
	}
};