#pragma once

#include "domain.h"
#include "exceptions.h"

class TenantValidator
{
public:
	void validate(const Tenant& tenant) const;
};