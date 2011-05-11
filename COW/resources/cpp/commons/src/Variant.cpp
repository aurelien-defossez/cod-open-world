#include "Variant.hpp"

// -------------------------------------------------------------------------
// Standard int[] matrix class
// -------------------------------------------------------------------------

IntMatrix1::IntMatrix1(int length) {
	this->allocated = true;
	this->length = length;
	this->values = new int[length];
}

IntMatrix1::IntMatrix1(int length, int *values) {
	this->allocated = false;
	this->length = length;
	this->values = values;
}

IntMatrix1::IntMatrix1(Variant var) {
	this->allocated = false;
	this->length = var.length1;
	this->values = var.value.intMatrix->values;
}

IntMatrix1::~IntMatrix1() {
	if(allocated) {
		delete[] values;
	}
}

Variant IntMatrix1::toVariant() {
	Variant var;
	var.type = VARIANT_INT_MATRIX1;
	var.length1 = this->length;
	var.value.returnIntMatrix = this->values;
	return var;
}

// -------------------------------------------------------------------------
// Standard int[][] matrix class
// -------------------------------------------------------------------------

IntMatrix2::IntMatrix2(int length1, int length2) {
	this->allocated = true;
	this->length[0] = length1;
	this->length[1] = length2;
	this->values = new int[length1 * length2];
}

IntMatrix2::IntMatrix2(int length1, int length2, int *values) {
	this->allocated = false;
	this->length[0] = length1;
	this->length[1] = length2;
	this->values = values;
}

IntMatrix2::IntMatrix2(int length1, int length2, int **values) {
	this->allocated = true;
	this->length[0] = length1;
	this->length[1] = length2;
	this->values = new int[length1 * length2];
	
	for(int i = 0; i < length1; i++) {
		int offset = i * length2;
		for(int j = 0; j < length2; j++) {
			this->values[offset + j] = values[i][j];
		}
	}
}

IntMatrix2::IntMatrix2(Variant var) {
	this->allocated = false;
	this->length[0] = var.length1;
	this->length[1] = var.length2;
	this->values = var.value.intMatrix->values;
}

IntMatrix2::~IntMatrix2() {
	if(allocated) {
		delete[] values;
	}
}

Variant IntMatrix2::toVariant() {
	Variant var;
	var.type = VARIANT_INT_MATRIX2;
	var.length1 = length[0];
	var.length2 = length[1];
	var.value.returnIntMatrix = values;
	
	return var;
}

// -------------------------------------------------------------------------
// Simple variant types
// -------------------------------------------------------------------------

Variant voidVariant() {
	Variant var;
	var.type = VARIANT_VOID;
	return var;
}

Variant toVariant(bool value) {
	Variant var;
	var.type = VARIANT_BOOL;
	var.value.boolValue = value;
	return var;
}

Variant toVariant(int value) {
	Variant var;
	var.type = VARIANT_INT;
	var.value.intValue = value;
	return var;
}

Variant toVariant(double value) {
	Variant var;
	var.type = VARIANT_DOUBLE;
	var.value.doubleValue = value;
	return var;
}

Variant toVariant(char *value) {
	Variant var;
	var.type = VARIANT_STRING;
	var.value.stringValue = value;
	return var;
}

// -------------------------------------------------------------------------
// From variant functions
// -------------------------------------------------------------------------

bool boolValue(Variant var) {
	return var.value.boolValue;
}

int intValue(Variant var) {
	return var.value.intValue;
}

double doubleValue(Variant var) {
	return var.value.doubleValue;
}

char *stringValue(Variant var) {
	return var.value.stringValue;
}
