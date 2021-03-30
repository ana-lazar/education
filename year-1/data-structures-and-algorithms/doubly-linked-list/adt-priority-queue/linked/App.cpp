#include <iostream>

#define _CRTDBG_MAP_ALLOC
#include <stdlib.h>
#include <crtdbg.h>

#include "CP.h"
#include "TestExtins.h"
#include "TestScurt.h"
#include "TestNou.h"

using namespace std;

int main() {
	testAllExtins();
	testNou();
	cout << "End";
	_CrtDumpMemoryLeaks();
	return 0;
}
