#include "domain.h"
#include <string.h>
#include <stdlib.h>


/*
	Initializes an offer data type
	offer - pointer to an Offer structure
	type, destination, date - pointers
	price - integer number
*/
Offer* offer_create(const char* type, const char* destination, const char* date, const int price)
{
	Offer* offer = (Offer*)malloc(sizeof(Offer));
	offer->type = (char*)malloc(sizeof(char) * (strlen(type) + 1));
	offer->destination = (char*)malloc(sizeof(char) * (strlen(destination) + 1));
	offer->date = (char*)malloc(sizeof(char) * (strlen(date) + 1));
	strcpy(offer->type, type);
	strcpy(offer->destination, destination);
	strcpy(offer->date, date);
	offer->price = price;
	return offer;
}


/*
	Destroys an offer data type
	offer - pointer to an Offer structure
*/
void offer_destroy(Offer* offer)
{
	free(offer->type);
	free(offer->destination);
	free(offer->date);
	free(offer);
}


/*
	Get function for offer.type
	Returns a pointer
*/
const char* offer_get_type(Offer* offer)
{
	return offer->type;
}


/*
	Get function for offer.destination
	Returns a pointer
*/
const char* offer_get_destination(Offer* offer)
{
	return offer->destination;
}


/*
	Get function for offer.date
	Returns a pointer
*/
const char* offer_get_date(Offer* offer)
{
	return offer->date;
}


/*
	Get function for offer.price
	Returns an integer
*/
const int offer_get_price(Offer* offer)
{
	return offer->price;
}