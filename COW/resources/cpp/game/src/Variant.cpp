#include "Variant.hpp"

IntMatrix1 toIntMatrix1(int values[], int length) {
	return IntMatrix1(values, values + length);
}

IntMatrix2 toIntMatrix2(int values[], int length, int length2) {
	IntMatrix2 matrix = IntMatrix2();
	
	for(int i = 0; i < length; i++) {
		matrix.push_back(toIntMatrix1(values + i * length2, length2));
	}
	
	return matrix;
}
