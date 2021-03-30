#include <QAbstractTableModel>

#include "model.h"

MyModel::MyModel(vector<Tenant>& tenants) : QAbstractTableModel(), tenants(tenants) { 
	
}

int MyModel::rowCount(const QModelIndex& parent) const {
	return tenants.size();
}

int MyModel::columnCount(const QModelIndex& parent) const {
	return 4;
}

QVariant MyModel::data(const QModelIndex& index, int role) const {
	if (role == Qt::DisplayRole) {
		if (index.column() == 0) {
			return QString::fromStdString(tenants.at(index.row()).get_name());
		}
		if (index.column() == 1) {
			return QString::number(tenants.at(index.row()).get_flat_number());
		}
		if (index.column() == 2) {
			return QString::number(tenants.at(index.row()).get_flat_size());
		}
		if (index.column() == 3) {
			return QString::fromStdString(tenants.at(index.row()).get_flat_type());
		}
	}
	if (role == Qt::BackgroundRole) {
		if (tenants.at(index.row()).get_flat_size() == 100) {
			return QBrush(Qt::red);
		}
	}
	return QVariant();
}

bool MyModel::setData(const QModelIndex& index, const QVariant& value, int role) {
	if (role == Qt::EditRole) {

		emit dataChanged(createIndex(0, 0), createIndex(index.row(), index.column()));
	}
	return true;
}

Qt::ItemFlags MyModel::flags(const QModelIndex& index) const {
	return Qt::ItemIsSelectable | Qt::ItemIsEditable | Qt::ItemIsEnabled;
}

QVariant MyModel::headerData(int section, Qt::Orientation orientation, int role) const {
	if (role == Qt::DisplayRole) {
		if (orientation == Qt::Horizontal) {
			return QString("Name");
		}
		else {
			return QString("Tenant ");
		}
	}
}