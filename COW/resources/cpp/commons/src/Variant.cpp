#include "Variant.hpp"

// -------------------------------------------------------------------------
// Standard int[] matrix class
// -------------------------------------------------------------------------

StdIntMatrix1::StdIntMatrix1(int *values, int length) {
	this->length = length;
	this->values = values;
}

Variant StdIntMatrix1::toVariant() {
	Variant var;
	var.type = VARIANT_INT_MATRIX1;
	var.length1 = this->length;
	var.value.returnIntMatrix = this->values;
	return var;
}

// -------------------------------------------------------------------------
// Standard int[][] matrix class
// -------------------------------------------------------------------------

StdIntMatrix2::StdIntMatrix2(int *values, int length1, int length2) {
	this->length1 = length1;
	this->length2 = length2;
	this->values = values;
}

StdIntMatrix2::StdIntMatrix2(int **values, int length1, int length2) {
	this->length1 = length1;
	this->length2 = length2;
	this->values = new int[length1 * length2];
	
	for(int i = 0; i < length1; i++) {
		for(int j = 0; j < length2; j++) {
			this->values[i * length2 + j] = values[i][j];
		}
	}
}

StdIntMatrix2::~StdIntMatrix2() {
	delete[] values;
}

Variant StdIntMatrix2::toVariant() {
	Variant var;
	var.type = VARIANT_INT_MATRIX2;
	var.length1 = this->length1;
	var.length2 = this->length2;
	var.value.returnIntMatrix = this->values;
	
	return var;
}

// -------------------------------------------------------------------------
// Simple variant types
// -------------------------------------------------------------------------

Variant intVariant(int value) {
	Variant var;
	var.type = VARIANT_INT;
	var.value.intValue = value;
	return var;
}
