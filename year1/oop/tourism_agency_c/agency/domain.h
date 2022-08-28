#pragma once


/*
	Data structure for offer
*/
struct Offer
{
	char* type;
	char* destination;
	char* date;
	int price;
};


Offer* offer_create(const char* type, const char* destination, const char* date, const int price);


void offer_destroy(Offer* offer);


const char* offer_get_type(Offer* offer);


const char* offer_get_destination(Offer* offer);


const char* offer_get_date(Offer* offer);


const int offer_get_price(Offer* offer);