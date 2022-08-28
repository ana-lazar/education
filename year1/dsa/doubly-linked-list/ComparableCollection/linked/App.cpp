#include <iostream>

#define _CRTDBG_MAP_ALLOC
#include <stdlib.h>
#include <crtdbg.h>

#include "TestExtins.h"
#include "TestScurt.h"

using namespace std;

int main() {
	testAllExtins();
	_CrtDumpMemoryLeaks();
	cout << "End";
	return 0;
}
