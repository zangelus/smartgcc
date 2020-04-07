#include <iostream>

int offset;

void setOffset(int num) {
  offset = num;
}
int add(int num) {
  return offset + num;
}

void __attribute__ ((constructor)) initLibrary(void) {
	std::cout << "Library is initialized\n";
    offset = 0;
}
void __attribute__ ((destructor)) cleanUpLibrary(void) {
	std::cout << "Library exited\n";
}