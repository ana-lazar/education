#pragma once

#include <QtWidgets/qlistwidget.h>
#include <QtWidgets/qboxlayout.h>
#include <QPainter>

#include "Service.h"
#include "observer.h"

class ReadOnlyWindow : public QWidget, public Observer
{
private:
	Service& service;

	/*
		Paints on the window
	*/
	void paintEvent(QPaintEvent* event) override;
	
	/*
		Updates the gui data
	*/
	void update() override {
		repaint();
	}
public:
	/*
		Constructor for the MainWindow object
		service - reference to a Service object
		Initializes the whole layout
	*/
	ReadOnlyWindow(Service& service) : service(service) {
		setWindowIcon(QIcon("icon.ico"));
		setWindowTitle("Read only notifications");
//		QPalette palett = palette();
//		palett.setColor(QPalette::Background, Qt::magenta);
//		setAutoFillBackground(true);
//		setPalette(palett);
	}

	/*
		Destructor for the window
	*/
	~ReadOnlyWindow() {
		service.removeObserver(this);
	}
};