#include <QtWidgets/qboxlayout.h>
#include <QtWidgets/qformlayout.h>
#include <QtWidgets/qgridlayout.h>
#include <QtWidgets/qframe.h>
#include <QtWidgets/qmessagebox.h>
#include <qdebug.h>

#include "MainWindow.h"
#include "NotificationWindow.h"
#include "ReadOnlyWindow.h"
#include "Service.h"
#include "domain.h"

using std::vector;
using std::string;

QHBoxLayout* MainWindow::createCrudLayout() {
	QHBoxLayout* crudLayout = new QHBoxLayout;
	QPushButton* addButton = new QPushButton;
	addButton->setText("Add");
	QObject::connect(addButton, &QPushButton::clicked, this, &MainWindow::addTenant);
	crudLayout->addWidget(addButton);
	QPushButton* deleteButton = new QPushButton;
	deleteButton->setText("Delete");
	QObject::connect(deleteButton, &QPushButton::clicked, this, &MainWindow::deleteTenant);
	crudLayout->addWidget(deleteButton);
	QPushButton* editButton = new QPushButton;
	editButton->setText("Edit");
	QObject::connect(editButton, &QPushButton::clicked, this, &MainWindow::editTenant);
	crudLayout->addWidget(editButton);
	return crudLayout;
}

QHBoxLayout* MainWindow::createSortLayout() {
	QHBoxLayout* sortLayout = new QHBoxLayout;
	QPushButton* sortButton = new QPushButton;
	sortButton->setText("Sort");
	QObject::connect(sortButton, &QPushButton::clicked, this, &MainWindow::sortBy);
	sortLayout->addWidget(sortButton);
	sortCriteriaCombo = new QComboBox;
	sortCriteriaCombo->addItem("name");
	sortCriteriaCombo->addItem("size");
	sortCriteriaCombo->addItem("type & size");
	sortLayout->addWidget(sortCriteriaCombo);
	sortOrderCombo = new QComboBox;
	sortOrderCombo->addItem("asc");
	sortOrderCombo->addItem("desc");
	sortLayout->addWidget(sortOrderCombo);
	return sortLayout;
}

QHBoxLayout* MainWindow::createFilterLayout() {
	QHBoxLayout* filterLayout = new QHBoxLayout;
	QPushButton* filterButton = new QPushButton;
	filterButton->setText("Filter");
	QObject::connect(filterButton, &QPushButton::clicked, this, &MainWindow::filterBy);
	filterLayout->addWidget(filterButton);
	filterCriteriaCombo = new QComboBox;
	filterCriteriaCombo->addItem("all");
	filterCriteriaCombo->addItem("type");
	filterCriteriaCombo->addItem("size");
	filterLayout->addWidget(filterCriteriaCombo);
	filterTextLine = new QLineEdit;
	filterLayout->addWidget(filterTextLine);
	return filterLayout;
}

QVBoxLayout* MainWindow::createMasterButtonLayout() {
	QVBoxLayout* buttonLayout = new QVBoxLayout;
	buttonLayout->addLayout(createCrudLayout());
	buttonLayout->addLayout(createSortLayout());
	buttonLayout->addLayout(createFilterLayout());
	return buttonLayout;
}

QVBoxLayout* MainWindow::createMasterLayout() {
	QVBoxLayout* masterLayout = new QVBoxLayout;
	QLabel* masterLabel = new QLabel;
	masterLabel->setText("TENANT LIST");
	masterLabel->setStyleSheet("background-color: white; inset grey; min-height: 25px;");
	masterLabel->setAlignment(Qt::AlignCenter);
	masterLayout->addWidget(masterLabel);
	master = new QListWidget;
	loadMaster(service.getAllTenants());
	QObject::connect(master, &QListWidget::itemSelectionChanged, this, &MainWindow::showDetails);
	masterLayout->addWidget(master);
	masterLayout->addLayout(createMasterButtonLayout());
	return masterLayout;
}

QFormLayout* MainWindow::createFormLayout() {
	QFormLayout* formLayout = new QFormLayout;
	nameLine = new QLineEdit;
	formLayout->addRow("Name: ", nameLine);
	nameLine->setReadOnly(true);
	flatnumberLine = new QLineEdit;
	formLayout->addRow("Number: ", flatnumberLine);
	flatnumberLine->setReadOnly(true);
	flattypeLine = new QLineEdit;
	formLayout->addRow("Type: ", flattypeLine);
	flattypeLine->setReadOnly(true);
	flatsizeLine = new QLineEdit;
	formLayout->addRow("Size: ", flatsizeLine);
	flatsizeLine->setReadOnly(true);
	return formLayout;
}

QHBoxLayout* MainWindow::createDetailsButtonLayout() {
	QHBoxLayout* buttonLayout = new QHBoxLayout;
	okButton = new QPushButton;
	okButton->setText("Ok");
	buttonLayout->addWidget(okButton);
	QObject::connect(okButton, &QPushButton::clicked, this, &MainWindow::saveChange);
	okButton->setEnabled(false);
	cancelButton = new QPushButton;
	cancelButton->setText("Cancel");
	buttonLayout->addWidget(cancelButton);
	QObject::connect(cancelButton, &QPushButton::clicked, this, &MainWindow::cancelChange);
	cancelButton->setEnabled(false);
	return buttonLayout;
}

QVBoxLayout* MainWindow::createDetailsLayout() {
	QVBoxLayout* dataLayout = new QVBoxLayout;
	QLabel* dataLabel = new QLabel;
	dataLabel->setText("DETAILS");
	dataLabel->setStyleSheet("background-color: white; inset grey;");
	dataLabel->setAlignment(Qt::AlignCenter);
	dataLayout->addWidget(dataLabel);
	dataLayout->addLayout(createFormLayout());
	dataLayout->addLayout(createDetailsButtonLayout());
	return dataLayout;
}

QWidget* MainWindow::createAboutWidget() {
	QWidget* aboutWidget = new QWidget;
	QHBoxLayout* aboutLayout = new QHBoxLayout;
	aboutWidget->setLayout(aboutLayout);
	QPushButton* notificationsButton = new QPushButton;
	notificationsButton->setText("Notifications");
	QObject::connect(notificationsButton, &QPushButton::clicked, this, &MainWindow::manageNotifications);
	aboutLayout->addWidget(notificationsButton);
	QPushButton* readOnlyButton = new QPushButton;
	readOnlyButton->setText("Read-Only");
	QObject::connect(readOnlyButton, &QPushButton::clicked, this, &MainWindow::readOnlyNotifs);
	aboutLayout->addWidget(readOnlyButton);
	QPushButton* undoButton = new QPushButton;
	undoButton->setText("Undo");
	QObject::connect(undoButton, &QPushButton::clicked, this, &MainWindow::undo);
	aboutLayout->addWidget(undoButton);
	QPushButton* exitButton = new QPushButton;
	exitButton->setText("Exit");
	QObject::connect(exitButton, &QPushButton::clicked, [&]() {
		service.removeObserver(this);
		close();
	});
	aboutLayout->addWidget(exitButton);
	return aboutWidget;
}

QHBoxLayout* MainWindow::createNotifsLayout() {
	QHBoxLayout* notifsLayout = new QHBoxLayout;
	QLabel* dataLabel = new QLabel;
	dataLabel->setText("Notification\nfunctionalities");
	dataLabel->setStyleSheet("background-color: white; inset grey;");
	dataLabel->setAlignment(Qt::AlignCenter);
	notifsLayout->addWidget(dataLabel);
	QPushButton* addButton = new QPushButton;
	addButton->setText("Add");
	QObject::connect(addButton, &QPushButton::clicked, this, &MainWindow::createAddDialog);
	notifsLayout->addWidget(addButton);
	QPushButton* clearButton = new QPushButton;
	clearButton->setText("Clear");
	QObject::connect(clearButton, &QPushButton::clicked, this, &MainWindow::clearNotifications);
	notifsLayout->addWidget(clearButton);
	QPushButton* generateButton = new QPushButton;
	generateButton->setText("Generate");
	QObject::connect(generateButton, &QPushButton::clicked, this, &MainWindow::createGenerateDialog);
	notifsLayout->addWidget(generateButton);
	QPushButton* exportButton = new QPushButton;
	exportButton->setText("Export");
	QObject::connect(exportButton, &QPushButton::clicked, this, &MainWindow::createExportDialog);
	notifsLayout->addWidget(exportButton);
	return notifsLayout;
}

QHBoxLayout* MainWindow::createTableView() {
	QHBoxLayout* tableLayout = new QHBoxLayout;
	table = new QTableView;
	model = new MyModel(service.getAllTenants());
	table->setModel(model);
	tableLayout->addWidget(table);
	return tableLayout;
}

QHBoxLayout* MainWindow::createSecondaryLayout() {
	QHBoxLayout* secondaryLayout = new QHBoxLayout;
//	secondaryLayout->addLayout(createMasterLayout());
	secondaryLayout->addLayout(createTableView());
	secondaryLayout->addLayout(createDetailsLayout());
	return secondaryLayout;
}

QWidget* MainWindow::createDynamicWidget() {
	QWidget* dynamicButtons = new QWidget;
	dynamicLayout = new QVBoxLayout;
	dynamicButtons->setLayout(dynamicLayout);
	return dynamicButtons;
}

void MainWindow::initLayout() {
	QVBoxLayout* mainLayout = new QVBoxLayout;
	this->setLayout(mainLayout);
	mainLayout->setMenuBar(createAboutWidget());
	mainLayout->addLayout(createSecondaryLayout());
	mainLayout->addLayout(createNotifsLayout());
}

void MainWindow::loadMaster(const vector<Tenant>& tenants) {
	master->clear();
	for (const Tenant& tenant : tenants) {
		master->addItem(QString::fromStdString(tenant.to_string()));
	}
}

void MainWindow::loadButtons(const vector<Tenant>& tenants) {
	while (auto button = dynamicLayout->takeAt(0)) {
		delete button->widget();
		delete button;
	}
	for (const Tenant& t : tenants) {
		QPushButton* newButton = new QPushButton;
		newButton->setText(QString::fromStdString(t.get_name()));
		QObject::connect(newButton, &QPushButton::clicked, [this, newButton, t]() {
			service.removeTenant(t.get_flat_number());
			dynamicLayout->removeWidget(newButton);
			delete newButton;
		});
		dynamicLayout->addWidget(newButton);
	}
}

void MainWindow::showDetails() {
	if (master->selectedItems().isEmpty()) {
		nameLine->setText("");
		flattypeLine->setText("");
		flatsizeLine->setText("");
		flatnumberLine->setText("");
		return;
	}
	const QString currentText = master->currentItem()->text();
	const auto fields = currentText.split(" ");
	const Tenant tenant = service.find(fields.at(0).toInt());
	nameLine->setText(QString::fromStdString(tenant.get_name()));
	flattypeLine->setText(QString::fromStdString(tenant.get_flat_type()));
	flatsizeLine->setText(QString::number(tenant.get_flat_size()));
	flatnumberLine->setText(QString::number(tenant.get_flat_number()));
}

void MainWindow::addTenant() {
	clearLines();
	nameLine->setReadOnly(false);
	flattypeLine->setReadOnly(false);
	flatsizeLine->setReadOnly(false);
	flatnumberLine->setReadOnly(false);
	okButton->setEnabled(true);
	cancelButton->setEnabled(true);
}

void MainWindow::deleteTenant() {
	if (!master->selectedItems().isEmpty()) {
		try {
			const QString currentText = master->currentItem()->text();
			const auto fields = currentText.split(" ");
			master->clear();
			service.removeTenant(fields.at(0).toInt());
		}
		catch (mystd::Exception& err) {
			QMessageBox::information(nullptr, "WARNING!", QString::fromStdString(err.what()));
		}
	}
	else {
		QMessageBox::information(nullptr, "WARNING!", "Please select a tenant");
	}
}

void MainWindow::editTenant() {
	if (!master->selectedItems().isEmpty()) {
		isEditing = true;
		master->setDisabled(true);
		nameLine->setReadOnly(false);
		okButton->setEnabled(true);
		cancelButton->setEnabled(true);
	}
	else {
		QMessageBox::information(nullptr, "WARNING!", "Please select a tenant");
	}
}

void MainWindow::undo() {
	try {
		service.undo();
	}
	catch (mystd::Exception& err) {
		QMessageBox::information(nullptr, "WARNING!", QString::fromStdString(err.what()));
	}
}

void MainWindow::clearLines() {
	nameLine->clear();
	flattypeLine->clear();
	flatsizeLine->clear();
	flatnumberLine->clear();
}

void MainWindow::saveChange() {
	if (isEditing) {
		int rowCurrent = master->currentRow();
		int flatnumber = toInt(flatnumberLine->text().toStdString(), "flat number must be a numeric value;");
		string name = nameLine->text().toStdString();
		try {
			service.modifyTenant(flatnumber, name);
		}
		catch (mystd::Exception& err) {
			QMessageBox::information(nullptr, "WARNING!", QString::fromStdString(err.what()));
		}
		master->setCurrentRow(rowCurrent);
		isEditing = false;
		master->setDisabled(false);
		nameLine->setReadOnly(true);
	}
	else {
		string name = nameLine->text().toStdString();
		string flattype = flattypeLine->text().toStdString();
		try {
			int flatnumber = toInt(flatnumberLine->text().toStdString(), "flat number must be a numeric value;");
			int flatsize = toInt(flatsizeLine->text().toStdString(), "flat size must be a numeric value;");
			service.addTenant(name, flatnumber, flatsize, flattype);
		}
		catch (mystd::Exception& err) {
			QMessageBox::information(nullptr, "WARNING!", QString::fromStdString(err.what()));
		}
		nameLine->setReadOnly(true);
		flattypeLine->setReadOnly(true);
		flatsizeLine->setReadOnly(true);
		flatnumberLine->setReadOnly(true);
	}
	okButton->setEnabled(false);
	cancelButton->setEnabled(false);
}

void MainWindow::cancelChange() {
	if (isEditing) {
		isEditing = false;
		const int currentIndex = master->currentRow();
		const Tenant tenant = service.getAllTenants().at(currentIndex);
		nameLine->setText(QString::fromStdString(tenant.get_name()));
		nameLine->setReadOnly(true);
		master->setDisabled(false);
	}
	else {
		clearLines();
		nameLine->setReadOnly(true);
		flattypeLine->setReadOnly(true);
		flatsizeLine->setReadOnly(true);
		flatnumberLine->setReadOnly(true);
	}
	okButton->setEnabled(false);
	cancelButton->setEnabled(false);
}

void MainWindow::sortBy() {
	const QString criteria = sortCriteriaCombo->currentText();
	vector<Tenant> tenants;
	if (criteria == "name") {
		tenants = service.sortByName(sortOrderCombo->currentText().toStdString());
	}
	else if (criteria == "size") {
		tenants = service.sortBySize(sortOrderCombo->currentText().toStdString());
	}
	else if (criteria == "type & size") {
		tenants = service.sortByTypeSize(sortOrderCombo->currentText().toStdString());
	}
	loadMaster(tenants);
}

void MainWindow::filterBy() {
	const string criteria = filterCriteriaCombo->currentText().toStdString();
	vector<Tenant> tenants;
	try {
		if (criteria == "type") {
			const string type = filterTextLine->text().toStdString();
			if (type != "garsoniera" && type != "o camera" && type != "doua camere" && type != "trei camere") {
				throw mystd::LogicError("type must be: garsoniera, o camera, doua camere, trei camere \n");
			}
			service.filterByType(tenants, type);
		}
		else if (criteria == "size") {
			int flatsize = toInt(filterTextLine->text().toStdString(), "flat size must be a numeric value;");
			service.filterBySize(tenants, flatsize);
		}
		else if (criteria == "all") {
			tenants = service.getAllTenants();
		}
		loadMaster(tenants);
		filterTextLine->setText("");
	}
	catch (mystd::Exception& err) {
		QMessageBox::information(nullptr, "WARNING!", QString::fromStdString(err.what()));
	}
}

void MainWindow::manageNotifications() {
	NotificationWindow* notificationWindow = new NotificationWindow(service);
	service.addObserver(notificationWindow);
	notificationWindow->show();
}

void MainWindow::readOnlyNotifs() {
	ReadOnlyWindow* readOnlyWindow = new ReadOnlyWindow(service);
	service.addObserver(readOnlyWindow);
	readOnlyWindow->show();
}

void MainWindow::clearNotifications() {
	try {
		service.clearNotifications();
	}
	catch (mystd::Exception& err) {
		QMessageBox::information(nullptr, "WARNING!", QString::fromStdString(err.what()));
	}
}

void MainWindow::createAddDialog() {
	QDialog* addDialog = new QDialog(this);
	QObject::connect(addDialog, &QDialog::accepted, this, &MainWindow::addNotification);
	addDialog->setWindowModality(Qt::WindowModal);
	QGridLayout* mainLayout = new QGridLayout;
	addDialog->setLayout(mainLayout);
	QLabel* flatNumberLabel = new QLabel;
	flatNumberLabel->setText("Flat number:");
	mainLayout->addWidget(flatNumberLabel, 0, 0);
	optionLine = new QLineEdit;
	mainLayout->addWidget(optionLine, 0, 1);
	QPushButton* okButton = new QPushButton;
	okButton->setText("Ok");
	mainLayout->addWidget(okButton, 1, 0);
	QObject::connect(okButton, &QPushButton::clicked, addDialog, &QDialog::accept);
	QPushButton* cancelButton = new QPushButton;
	cancelButton->setText("Cancel");
	mainLayout->addWidget(cancelButton, 1, 1);
	QObject::connect(cancelButton, &QPushButton::clicked, addDialog, &QDialog::reject);
	addDialog->show();
}

void MainWindow::addNotification() {
	try {
		const int flat_number = toInt(optionLine->text().toStdString(), "flat number must be an integer \n");
		service.addNotification(flat_number);
	}
	catch (mystd::Exception& err) {
		QMessageBox::information(nullptr, "WARNING!", QString::fromStdString(err.what()));
	}
}

void MainWindow::createGenerateDialog() {
	QDialog* generateDialog = new QDialog(this);
	QObject::connect(generateDialog, &QDialog::accepted, this, &MainWindow::generateNotifications);
	generateDialog->setWindowModality(Qt::WindowModal);
	QGridLayout* mainLayout = new QGridLayout;
	generateDialog->setLayout(mainLayout);
	QLabel* numberOfLabel = new QLabel;
	numberOfLabel->setText("Number of generated:");
	mainLayout->addWidget(numberOfLabel, 0, 0);
	optionLine = new QLineEdit;
	mainLayout->addWidget(optionLine, 0, 1);
	QPushButton* okButton = new QPushButton;
	okButton->setText("Ok");
	mainLayout->addWidget(okButton, 1, 0);
	QObject::connect(okButton, &QPushButton::clicked, generateDialog, &QDialog::accept);
	QPushButton* cancelButton = new QPushButton;
	cancelButton->setText("Cancel");
	mainLayout->addWidget(cancelButton, 1, 1);
	QObject::connect(cancelButton, &QPushButton::clicked, generateDialog, &QDialog::reject);
	generateDialog->show();
}

void MainWindow::generateNotifications() {
	try {
		const int number_of = toInt(optionLine->text().toStdString(), "number of must be an integer \n");
		service.generate(number_of);
	}
	catch (mystd::Exception& err) {
		QMessageBox::information(nullptr, "WARNING!", QString::fromStdString(err.what()));
	}
}

void MainWindow::createExportDialog() {
	QDialog* exportDialog = new QDialog(this);
	QObject::connect(exportDialog, &QDialog::accepted, this, &MainWindow::exportNotifications);
	exportDialog->setWindowModality(Qt::WindowModal);
	QGridLayout* mainLayout = new QGridLayout;
	exportDialog->setLayout(mainLayout);
	QLabel* numberOfLabel = new QLabel;
	numberOfLabel->setText("File name:");
	mainLayout->addWidget(numberOfLabel, 0, 0);
	optionLine = new QLineEdit;
	mainLayout->addWidget(optionLine, 0, 1);
	QPushButton* okButton = new QPushButton;
	okButton->setText("Ok");
	mainLayout->addWidget(okButton, 1, 0);
	QObject::connect(okButton, &QPushButton::clicked, exportDialog, &QDialog::accept);
	QPushButton* cancelButton = new QPushButton;
	cancelButton->setText("Cancel");
	mainLayout->addWidget(cancelButton, 1, 1);
	QObject::connect(cancelButton, &QPushButton::clicked, exportDialog, &QDialog::reject);
	exportDialog->show();
}

void MainWindow::exportNotifications() {
	try {
		const string filename = optionLine->text().toStdString();
		service.exportNotifications(filename);
	}
	catch (mystd::Exception& err) {
		QMessageBox::information(nullptr, "WARNING!", QString::fromStdString(err.what()));
	}
}