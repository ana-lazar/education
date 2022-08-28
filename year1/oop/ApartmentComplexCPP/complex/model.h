#pragma once

#include <vector>
#include <QBrush>

#include "domain.h"

using namespace std;

class MyModel : public QAbstractTableModel {
private:
	const vector<Tenant> tenants;
public:
	MyModel(const vector<Tenant>& tenants);

	int rowCount(const QModelIndex& parent = QModelIndex()) const override;

	int columnCount(const QModelIndex& parent = QModelIndex()) const override;

	QVariant data(const QModelIndex& index, int role = Qt::DisplayRole) const override;

	bool setData(const QModelIndex& index, const QVariant& value, int role);

	Qt::ItemFlags flags(const QModelIndex& index) const;

	QVariant headerData(int section, Qt::Orientation orientation, int role) const;
};