#pragma once

#include "Element.h"

class Vector
{
private:
	int capacity;
	int length;
	TElem* elements;

	void resize();
public:
	Vector();

	void add(TElem elem);

	void remove(int position);

	int size() const;

	TElem get(int index) const;

	void set(int index, TElem elem);

	int find(int position, TElem elem) const;

	~Vector();

};