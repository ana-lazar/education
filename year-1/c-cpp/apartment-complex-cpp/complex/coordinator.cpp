#include <QtWidgets/QApplication>

#include "tests.h"
#include "domain.h"
#include "Service.h"
#include "Repository.h"
#include "Ui.h"
#include "MainWindow.h"
#include "observer.h"

int start(int argc, char *argv[]) {
	QApplication a(argc, argv);
	FileTenantRepository tenantRepo("tenants.txt");
	NotificationRepository notificationRepo;
	const TenantValidator tenantValidator;
	Service service(tenantRepo, notificationRepo, tenantValidator);
	MainWindow mainWindow(service);
	service.addObserver(&mainWindow);
	mainWindow.show();
	return a.exec();
}

int main(int argc, char *argv[]) {
	test_all();
	auto result = start(argc, argv);
	return result;
}