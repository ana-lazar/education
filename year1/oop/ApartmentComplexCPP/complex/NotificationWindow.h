#pragma once

#include <QtWidgets/qlistwidget.h>
#include <QtWidgets/qboxlayout.h>
#include <QtWidgets/qlineedit.h>

#include "Service.h"
#include "observer.h"

class NotificationWindow : public QWidget, public Observer
{
private:
	Service& service;

	QListWidget* master;
	QLineEdit* optionLine;

	/*
		Creates the Add, Clear, Export, Generate layout
		Returns a pointer to the QHBoxLayout object
	*/
	QHBoxLayout* createActionLayout();

	/*
		Creates the master layout
		Returns a pointer to the QVBoxLayout object
	*/
	QVBoxLayout* createMasterLayout();

	/*
		Creates the about layout
		Returns a pointer to the QWidget object
	*/
	QWidget* createAboutWidget();

	/*
		Initializes the layouts and the bonds between them
	*/
	void initLayout();

	/*
		Loads the master according to the service notification list
	*/
	void loadMaster();

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
		loadMaster();
	}
public:
	/*
		Constructor for the MainWindow object
		service - reference to a Service object
		Initializes the whole layout
	*/
	NotificationWindow(Service& service) : service(service) {
		setWindowIcon(QIcon("icon.ico"));
		setWindowTitle("NotificationManager");
		initLayout();
	}
};