#pragma once

#include "domain.h"

typedef Offer* Element;


/*
	Data structure for offers
*/
struct List
{
	Element* elements;
	int length, capacity;
};


void list_init(List* list);


void list_destroy(List* list);


int list_size(List* list);


Element list_get_element(List* list, int index);


void list_add(List* list, Element element);


int list_remove(List* list, int index);


int list_set_element(List* list, int index, Element element);


List list_copy(List* list);