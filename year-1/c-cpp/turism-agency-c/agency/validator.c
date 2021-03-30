#include <string.h>
#include "domain.h"


/*
	Validates an offer
	offers - a pointer to an Offer struct
	Returns 0 if the offer is valid and 1 otherwise
*/
int validate(Offer* offer)
{
	if (!((strcmp(offer->type, "mare") == 0 || strcmp(offer->type, "citybreak") == 0 || strcmp(offer->type, "munte") == 0)))
		return 1;
	if (strcmp(offer->destination, "") == 0)
		return 1;
	if (strcmp(offer->date, "") == 0)
		return 1;
	return 0;
}