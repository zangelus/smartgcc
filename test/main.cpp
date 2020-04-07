#include <iostream>
#include "tlb001\add.h"
#include "tlb001\op1.h"

int main(int argc, char* argv[]) {

	std::cout << "is result eq to 10: " << op1();
	setOffset(3);
	std::cout << "is retult eq to 13: " << add(10);
	
    return 0;
}

/*
g++ -c main.cpp -o bin/main.o
g++ -c tlb001/add.c -o bin/static/add.o
g++ -c tlb001/op1.cpp -o bin/static/op1.o

ref
(https://renenyffenegger.ch/notes/development/languages/C-C-plus-plus/GCC/create-libraries/index)
*/
