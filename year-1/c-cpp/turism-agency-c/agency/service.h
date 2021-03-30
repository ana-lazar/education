#pragma once

#include "domain.h"
#include "List.h"


int service_add(List* offers, const char* type, const char* destination, const char* date, int price);


int service_remove(List* offers, int index);


int service_update(List* offers, int index, const char* type, const char* destination, const char* date, int price);


void service_destroy(List* list);


List service_sort_by_price(List* offers, const char* direction);


List service_sort_by_dest(List* offers, const char* direction);


List service_filter_by_price(List* offers, const char* value);


List service_filter_by_dest(List* offers, const char* value);


List service_filter_by_type(List* offers, const char* type);