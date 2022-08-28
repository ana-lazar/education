#include <QtWidgets/qboxlayout.h>
#include <QtWidgets/qgridlayout.h>
#include <QtWidgets/qmessagebox.h>
#include <QtWidgets/qpushbutton.h>
#include <QtWidgets/qlabel.h>

#include "NotificationWindow.h"
#include "Service.h"
#include "domain.h"

using std::vector;
using std::string;

QHBoxLayout* NotificationWindow::createActionLayout() {
	QHBoxLayout* crudLayout = new QHBoxLayout;
	QPushButton* addButton = new QPushButton;
	addButton->setText("Add");
	QObject::connect(addButton, &QPushButton::clicked, this, &NotificationWindow::createAddDialog);
	crudLayout->addWidget(addButton);
	QPushButton* clearButton = new QPushButton;
	clearButton->setText("Clear");
	QObject::connect(clearButton, &QPushButton::clicked, this, &NotificationWindow::clearNotifications);
	crudLayout->addWidget(clearButton);
	QPushButton* generateButton = new QPushButton;
	generateButton->setText("Generate");
	QObject::connect(generateButton, &QPushButton::clicked, this, &NotificationWindow::createGenerateDialog);
	crudLayout->addWidget(generateButton);
	QPushButton* exportButton = new QPushButton;
	exportButton->setText("Export");
	QObject::connect(exportButton, &QPushButton::clicked, this, &NotificationWindow::createExportDialog);
	crudLayout->addWidget(exportButton);
	return crudLayout;
}

QVBoxLayout* NotificationWindow::createMasterLayout() {
	QVBoxLayout* masterLayout = new QVBoxLayout;
	QLabel* masterLabel = new QLabel;
	masterLabel->setText("NOTIFICATION LIST");
	masterLabel->setStyleSheet("background-color: white; inset grey; min-height: 25px;");
	masterLabel->setAlignment(Qt::AlignCenter);
	masterLayout->addWidget(masterLabel);
	master = new QListWidget;
	loadMaster();
	masterLayout->addWidget(master);
	masterLayout->addLayout(createActionLayout());
	return masterLayout;
}

QWidget* NotificationWindow::createAboutWidget() {
	QWidget* aboutWidget = new QWidget;
	QHBoxLayout* aboutLayout = new QHBoxLayout;
	aboutWidget->setLayout(aboutLayout);
	QPushButton* exitButton = new QPushButton;
	exitButton->setText("Exit");
	QObject::connect(exitButton, &QPushButton::clicked, [&]() {
		service.removeObserver(this);
		close();
	});
	aboutLayout->addWidget(exitButton);
	return aboutWidget;
}

void NotificationWindow::initLayout() {
	QHBoxLayout* mainLayout = new QHBoxLayout;
	this->setLayout(mainLayout);
	mainLayout->addLayout(createMasterLayout());
	mainLayout->setMenuBar(createAboutWidget());
}

void NotificationWindow::loadMaster() {
	master->clear();
	auto notifs = service.getNotifications();
	for (const Notification& notif : notifs) {
		const Tenant& tenant = service.find(notif.get_flat_number());
		if (tenant.get_name() == "") {
			master->addItem(QString::fromStdString(to_string(notif.get_flat_number())));
		}
		else {
			master->addItem(QString::fromStdString(tenant.to_string()));
		}
	}
}

void NotificationWindow::clearNotifications() {
	try {
		service.clearNotifications();
		loadMaster();
	}
	catch (mystd::Exception& err) {
		QMessageBox::information(nullptr, "WARNING!", QString::fromStdString(err.what()));
	}
}

void NotificationWindow::createAddDialog() {
	QDialog* addDialog = new QDialog(this);
	QObject::connect(addDialog, &QDialog::accepted, this, &NotificationWindow::addNotification);
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

void NotificationWindow::addNotification() {
	try {
		const int flat_number = toInt(optionLine->text().toStdString(), "flat number must be an integer \n");
		service.addNotification(flat_number);
		loadMaster();
	}
	catch (mystd::Exception& err) {
		QMessageBox::information(nullptr, "WARNING!", QString::fromStdString(err.what()));
	}
}

void NotificationWindow::createGenerateDialog() {
	QDialog* generateDialog = new QDialog(this);
	QObject::connect(generateDialog, &QDialog::accepted, this, &NotificationWindow::generateNotifications);
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

void NotificationWindow::generateNotifications() {
	try {
		const int number_of = toInt(optionLine->text().toStdString(), "number of must be an integer \n");
		service.generate(number_of);
		loadMaster();
	}
	catch (mystd::Exception& err) {
		QMessageBox::information(nullptr, "WARNING!", QString::fromStdString(err.what()));
	}
}

void NotificationWindow::createExportDialog() {
	QDialog* exportDialog = new QDialog(this);
	QObject::connect(exportDialog, &QDialog::accepted, this, &NotificationWindow::exportNotifications);
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

void NotificationWindow::exportNotifications() {
	try {
		const string filename = optionLine->text().toStdString();
		service.exportNotifications(filename);
	}
	catch (mystd::Exception& err) {
		QMessageBox::information(nullptr, "WARNING!", QString::fromStdString(err.what()));
	}
}