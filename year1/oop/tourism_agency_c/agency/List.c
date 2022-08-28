#include <string.h>
#include <stdlib.h>
#include "domain.h"
#include "List.h"


/*
	Get function for list->len
	list - pointer to a list structure
	Returns a constant positive integer number
*/
int list_size(List* list)
{
	return list->length;
}


/*
	Get function for list->element
	list - pointer to a List structure
	Returns a poiinter to an Offer structure
*/
Element list_get_element(List* list, int index)
{
	return list->elements[index];
}


/*
	Initializes the List structure
	list - pointer to a List structure
*/
void list_init(List* list)
{
	list->length = 0;
	list->capacity = 1;
	list->elements = (Element*)malloc(list->capacity * sizeof(Element));
}


/*
	Resizes a list structure
	offers - pointer to a List structure
*/
void list_resize(List* list)
{
	int new_capacity = list->capacity * 2;
	Element* resized_elements = (Element*)malloc(sizeof(Element) * new_capacity);
	for (int i = 0; i < list->capacity; i++)
	{
		resized_elements[i] = list->elements[i];
	}
	free(list->elements);
	list->elements = resized_elements;
	list->capacity = new_capacity;
}


/*
	Destroys a List structure
	list - pointer to a List structure
*/
void list_destroy(List* list)
{
	free(list->elements);
}


/*
	Adds a new offer to the list
	offers - pointer to a List structure
	offer - pointer to an Offer structure
*/
void list_add(List* list, Element element)
{
	if (list->length >= list->capacity)
	{
		list_resize(list);
	}
	list->elements[list->length++] = element;
}


/*
	Removes a certain offer (if existent) from the list
	offers - pointer to a List structure
	index - integer number
	Returns 0 if the removal has been successfully done and 1 otherwise
*/
int list_remove(List* list, int index)
{
	if ((index >= list->length) || (index < 0))
	{
		return 1;
	}
	for (int i = index; i < list->length - 1; i++)
	{
		list->elements[i] = list->elements[i + 1];
	}
	list->length = list->length - 1;
	return 0;
}


/*
	Updates an offer
	offers - pointer to a List structure
	index, price - integer numbers
	type, destination, date - strings
	Returns 0 if the update has been successfully done and 1 otherwise
*/
int list_set_element(List* list, int index, Element element)
{
	if ((index >= list->length) || (index < 0))
	{
		return 1;
	}
	list->elements[index] = element;
	return 0;
}


/*
	Returns a copy of a list
	list1 - pointer to a List structure
*/
List list_copy(List* list)
{
	List copy_list;
	list_init(&copy_list);
	for (int i = 0; i < list->length; i++)
	{
		list_add(&copy_list, list_get_element(list, i));
	}
	return copy_list;
}