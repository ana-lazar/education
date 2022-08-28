#include "ReadOnlyWindow.h"

#include <random>

void ReadOnlyWindow::paintEvent(QPaintEvent* event) {
	QPainter p(this);
	int x = 10;
	for (const auto& notif : service.getNotifications()) {
		p.drawImage(x, 20 + rand() % (this->height() - 80), QImage("house.jpg"));
		x += 80;
	}
	if (x > width()) {
		resize(x, height());
	}
}