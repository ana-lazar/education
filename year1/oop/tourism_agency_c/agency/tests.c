#include <assert.h>
#include <stdlib.h>
#include <string.h>
#include "domain.h"
#include "service.h"
#include "validator.h"


void free_offers(List* offers)
{
	for (int i = 0; i < list_size(offers); i++)
	{
		offer_destroy(offers->elements[i]);
	}
}


/*
	Test function for validate()
*/
void test_validate()
{
	Offer* o = offer_create("abc", "Cluj-Napoca", "12decembrie", 100);
	assert(validate(o) == 1);
	Offer* o2 = offer_create("mare", "Bucuresti", "1mai", 1);
	assert(validate(o2) == 0);
	strcpy(o2->destination, "");
	assert(validate(o2) == 1);
	strcpy(o2->date, "");
	strcpy(o2->destination, "Bucuresti");
	assert(validate(o2) == 1);
	offer_destroy(o);
	offer_destroy(o2);

}


/*
	Runs validator tests
*/
void run_validator_tests()
{
	test_validate();
}


/*
	Test function for add_service()
*/
void test_add_service()
{
	List offers;
	list_init(&offers);
	assert(service_add(&offers, "mare", "Bucuresti", "1ianuarie", 100) == 0);
	assert(offers.length == 1);
	assert(strcmp("mare", offer_get_type(offers.elements[0])) == 0);
	assert(strcmp("Bucuresti", offer_get_destination(offers.elements[0])) == 0);
	assert(strcmp("1ianuarie", offer_get_date(offers.elements[0])) == 0);
	assert(100 == offer_get_price(offers.elements[0]));
	assert(service_add(&offers, "abc", "Bucuresti", "1ianuarie", 100) == 1);
	assert(service_add(&offers, "citybreak", "Cluj-Napoca", "1mai", 50) == 0);
	assert(offers.length == 2);
	free_offers(&offers);
	list_destroy(&offers);
}


/*
	Test function for remove_service()
*/
void test_remove_service()
{
	List offers;
	list_init(&offers);
	Offer* o = offer_create("munte", "Brasov", "10mai", 100);
	Offer* o2 = offer_create("mare", "Bucuresti", "1mai", 1);
	list_add(&offers, o);
	list_add(&offers, o2);
	assert(service_remove(&offers, 4) == 1);
	assert(service_remove(&offers, 3) == 1);
	assert(service_remove(&offers, -6) == 1);
	assert(offers.length == 2);
	assert(service_remove(&offers, 2) == 0);
	assert(offers.length == 1);
	assert(service_remove(&offers, 1) == 0);
	assert(offers.length == 0);
	free_offers(&offers);
	list_destroy(&offers);
}


/*
	Test function for update_service()
*/
void test_update_service()
{
	List offers;
	list_init(&offers);
	Offer* o = offer_create("munte", "Brasov", "10mai", 100);
	Offer* o2 = offer_create("mare", "Bucuresti", "1mai", 1);
	list_add(&offers, o);
	list_add(&offers, o2);
	assert(service_update(&offers, 4, "mare", "Vaslui", "25decembrie", 100) == 1);
	assert(service_update(&offers, -3, "mare", "Vaslui", "25decembrie", 100) == 1);
	assert(service_update(&offers, 1, "mare", "Vaslui", "1mai", 100) == 0);
	assert(strcmp(offer_get_destination((offers.elements[1])), "Vaslui") == 0);
	assert(strcmp(offer_get_type((offers.elements[1])), "mare") == 0);
	assert(strcmp(offer_get_date((offers.elements[1])), "1mai") == 0);
	assert(offer_get_price((offers.elements[1])) == 100);
	free_offers(&offers);
	list_destroy(&offers);
}


/*
	Test function for service_sort_by_price()
*/
void test_sort_by_price()
{
	List offers;
	list_init(&offers);
	Offer* o = offer_create("munte", "Brasov", "10mai", 100);
	Offer* o2 = offer_create("mare", "Bucuresti", "1mai", 1);
	list_add(&offers, o);
	list_add(&offers, o2);
	List sorted1 = service_sort_by_price(&offers, "asc");
	assert(strcmp(offer_get_type(sorted1.elements[0]), "mare") == 0);
	assert(strcmp(offer_get_destination(sorted1.elements[1]), "Brasov") == 0);
	List sorted2 = service_sort_by_price(&offers, "desc");
	assert(strcmp(offer_get_type(sorted2.elements[0]), "munte") == 0);
	assert(offer_get_price(sorted2.elements[1]) == 1);
	free_offers(&offers);
	list_destroy(&offers);
	list_destroy(&sorted1);
	list_destroy(&sorted2);
}


/*
	Test function for service_sort_by_dest()
*/
void test_sort_by_destination()
{
	List offers;
	list_init(&offers);
	Offer* o = offer_create("munte", "Brasov", "10mai", 100);
	Offer* o2 = offer_create("mare", "Bucuresti", "1mai", 1);
	list_add(&offers, o);
	list_add(&offers, o2);
	List sorted1 = service_sort_by_dest(&offers, "asc");
	assert(strcmp(offer_get_type(sorted1.elements[1]), "mare") == 0);
	assert(strcmp(offer_get_destination(sorted1.elements[0]), "Brasov") == 0);
	List sorted2 = service_sort_by_dest(&offers, "desc");
	assert(strcmp(offer_get_type(sorted2.elements[1]), "munte") == 0);
	assert(offer_get_price(sorted2.elements[0]) == 1);
	free_offers(&offers);
	list_destroy(&offers);
	list_destroy(&sorted1);
	list_destroy(&sorted2);
}


/*
	Test function for service_sort_by()
*/
void test_sort_by()
{
	test_sort_by_price();
	test_sort_by_destination();
}


/*
	Test function for filter_by_price()
*/
void test_filter_by_price()
{
	List offers;
	list_init(&offers);
	Offer* o = offer_create("munte", "Brasov", "10mai", 100);
	Offer* o2 = offer_create("mare", "Bucuresti", "1mai", 1);
	Offer* o3 = offer_create("citybreak", "Cluj", "14septembrie", 100);
	list_add(&offers, o);
	list_add(&offers, o2);
	list_add(&offers, o3);
	List filtered1 = service_filter_by_price(&offers, "100");
	assert(filtered1.length == 2);
	List filtered2 = service_filter_by_price(&offers, "0");
	assert(filtered2.length == 0);
	free_offers(&offers);
	list_destroy(&offers);
	list_destroy(&filtered1);
	list_destroy(&filtered2);
}


/*
	Test function for filter_by_dest()
*/
void test_filter_by_dest()
{
	List offers;
	list_init(&offers);
	Offer* o = offer_create("munte", "Brasov", "10mai", 100);
	Offer* o2 = offer_create("mare", "Brasov", "1mai", 1);
	Offer* o3 = offer_create("citybreak", "Cluj", "14septembrie", 100);
	list_add(&offers, o);
	list_add(&offers, o2);
	list_add(&offers, o3);
	List filtered1 = service_filter_by_dest(&offers, "Brasov");
	assert(filtered1.length == 2);
	List filtered2 = service_filter_by_dest(&offers, "Cluj");
	assert(filtered2.length == 1);
	free_offers(&offers);
	list_destroy(&offers);
	list_destroy(&filtered1);
	list_destroy(&filtered2);
}


/*
	Test function for filter_by_type()
*/
void test_filter_by_type()
{
	List offers;
	list_init(&offers);
	Offer* o = offer_create("munte", "Brasov", "10mai", 100);
	Offer* o2 = offer_create("munte", "Bucuresti", "1mai", 1);
	Offer* o3 = offer_create("citybreak", "Cluj", "14septembrie", 100);
	list_add(&offers, o);
	list_add(&offers, o2);
	list_add(&offers, o3);
	List filtered1 = service_filter_by_type(&offers, "munte");
	assert(filtered1.length == 2);
	List filtered2 = service_filter_by_type(&offers, "mare");
	assert(filtered2.length == 0);
	free_offers(&offers);
	list_destroy(&offers);
	list_destroy(&filtered1);
	list_destroy(&filtered2);
}


/*
	Test function for service_filter_by()
*/
void test_filter_by()
{
	test_filter_by_price();
	test_filter_by_dest();
	test_filter_by_type();
}


void test_destroy_offers()
{
	List offers;
	list_init(&offers);
	Offer* o = offer_create("munte", "Brasov", "10mai", 100);
	Offer* o2 = offer_create("munte", "Bucuresti", "1mai", 1);
	Offer* o3 = offer_create("citybreak", "Cluj", "14septembrie", 100);
	list_add(&offers, o);
	list_add(&offers, o2);
	list_add(&offers, o3);
	service_destroy(&offers);
	list_destroy(&offers);
}


/*
	Runs service tests
*/
void run_service_tests()
{
	test_add_service();
	test_remove_service();
	test_update_service();
	test_sort_by();
	test_filter_by();
	test_destroy_offers();
}


/*
	Test function for offer_init()
*/
void test_offer_init()
{
	Offer* o = offer_create("munte", "Brasov", "10mai", 100);
	assert(strcmp(o->type, "munte") == 0);
	assert(strcmp(o->destination, "Brasov") == 0);
	assert(strcmp(o->date, "10mai") == 0);
	assert(o->price == 100);
	offer_destroy(o);
}


/*
	Test function for getters
*/
void test_getters()
{
	Offer* o = offer_create("munte", "Brasov", "10mai", 100);
	assert(strcmp(offer_get_destination(o), "Brasov") == 0);
	assert(strcmp(offer_get_type(o), "munte") == 0);
	assert(strcmp(offer_get_date(o), "10mai") == 0);
	assert(offer_get_price(o) == 100);
	offer_destroy(o);
}


/*
	Test function for list_add()
*/
void test_list_add()
{
	List offers;
	list_init(&offers);
	Offer* o = offer_create("munte", "Brasov", "10mai", 100);
	list_add(&offers, o);
	assert(offers.length == 1);
	assert(strcmp(offer_get_type(o), offer_get_type(offers.elements[0])) == 0);
	assert(strcmp(offer_get_destination(o), offer_get_destination(offers.elements[0])) == 0);
	assert(strcmp(offer_get_date(o), offer_get_date(offers.elements[0])) == 0);
	assert(offer_get_price(o) == offer_get_price(offers.elements[0]));
	Offer* o2 = offer_create("mare", "Bucuresti", "1mai", 1);
	list_add(&offers, o2);
	assert(offers.length == 2);
	free_offers(&offers);
	list_destroy(&offers);
}


/*
	Test function for list_remove()
*/
void test_list_remove()
{
	List offers;
	list_init(&offers);
	Offer* o = offer_create("munte", "Brasov", "10mai", 100);
	Offer* o2 = offer_create("mare", "Bucuresti", "1mai", 1);
	Offer* o3 = offer_create("mare", "Cluj", "1decembrie", 50);
	list_add(&offers, o3);
	list_add(&offers, o2);
	list_add(&offers, o);
	assert(list_remove(&offers, 4) == 1);
	assert(list_remove(&offers, 3) == 1);
	assert(list_remove(&offers, -6) == 1);
	assert(offers.length == 3);
	assert(list_remove(&offers, 1) == 0);
	assert(offers.length == 2);
	assert(list_remove(&offers, 0) == 0);
	assert(offers.length == 1);
	offer_destroy(o2);
	offer_destroy(o3);
	free_offers(&offers);
	list_destroy(&offers);
}


/*
	Test function for list_update()
*/
void test_list_update()
{
	List offers;
	list_init(&offers);
	Offer* o = offer_create("munte", "Brasov", "10mai", 100);
	Offer* o2 = offer_create("mare", "Bucuresti", "1mai", 1);
	list_add(&offers, o);
	list_add(&offers, o2);
	assert(list_set_element(&offers, -1, o) == 1);
	assert(list_set_element(&offers, 1, offer_create("mare", "Vaslui", "1mai", 100)) == 0);
	assert(strcmp(offer_get_destination((offers.elements[1])), "Vaslui") == 0);
	assert(strcmp(offer_get_type((offers.elements[1])), "mare") == 0);
	assert(strcmp(offer_get_date((offers.elements[1])), "1mai") == 0);
	assert(offer_get_price((offers.elements[1])) == 100);
	offer_destroy(o2);
	free_offers(&offers);
	list_destroy(&offers);
}


/*
	Runs domain tests
*/
void run_domain_tests()
{
	test_offer_init();
	test_getters();
	test_list_add();
	test_list_remove();
	test_list_update();
}


/*
	Runs all test functions
*/
void run_all_tests()
{
	run_domain_tests();
	run_service_tests();
	run_validator_tests();
}