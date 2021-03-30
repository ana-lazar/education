#include <iostream>

#define _CRTDBG_MAP_ALLOC
#include <stdlib.h>
#include <crtdbg.h>

#include "TestNou.h"
#include "TestScurt.h"
#include "TestExtins.h"

using namespace std;

int main() {
	testAllExtins();
	cout << "Finished tests! \n";
	_CrtDumpMemoryLeaks();
	return 0;
}
