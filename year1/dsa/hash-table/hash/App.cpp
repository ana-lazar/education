#include <iostream>

#define _CRTDBG_MAP_ALLOC
#include <stdlib.h>
#include <crtdbg.h>

#include "TestScurt.h"
#include "TestExtins.h"

int main() {
	testAllExtins();
	std::cout << "Finished Tests!" << std::endl;
	_CrtDumpMemoryLeaks();
	return 0;
}
