#ifndef __VARIANT_H__
#define __VARIANT_H__

#include <vector>

// -------------------------------------------------------------------------
// Variant types
// -------------------------------------------------------------------------

#define VARIANT_VOID 0
#define VARIANT_BOOL 1
#define VARIANT_INT 2
#define VARIANT_DOUBLE 3
#define VARIANT_STRING 4
#define VARIANT_BOOL_MATRIX1 5
#define VARIANT_BOOL_MATRIX2 6
#define VARIANT_BOOL_MATRIX3 7
#define VARIANT_INT_MATRIX1 8
#define VARIANT_INT_MATRIX2 9
#define VARIANT_INT_MATRIX3 10
#define VARIANT_DOUBLE_MATRIX1 11
#define VARIANT_DOUBLE_MATRIX2 12
#define VARIANT_DOUBLE_MATRIX3 13
#define VARIANT_STRING_MATRIX1 14
#define VARIANT_STRING_MATRIX2 15
#define VARIANT_STRING_MATRIX3 16

// -------------------------------------------------------------------------
// Variant union
// -------------------------------------------------------------------------

struct intMatrix {
	int values[];
};

typedef union variantValue {
	bool boolValue;
	int intValue;
	double doubleValue;
	char *stringValue;
	
	struct intMatrix *intMatrix;
	int *returnIntMatrix;
} VariantValue;

// -------------------------------------------------------------------------
// Variant structure
// -------------------------------------------------------------------------

typedef struct variant {
	char type;
	int length1;
	int length2;
	int length3;
	
	VariantValue value;
} Variant;

// -------------------------------------------------------------------------
// Standard int[] matrix class
// -------------------------------------------------------------------------

class StdIntMatrix1 {
private:
	int length;
	int *values;

public:
	StdIntMatrix1(int *values, int length);
	
	Variant toVariant();
	
	inline int size() {
		return length;
	}
	
	inline int& operator[] (int index) {
		return values[index];
	}
};

// -------------------------------------------------------------------------
// Standard int[][] matrix class
// -------------------------------------------------------------------------

class StdIntMatrix2 {
private:
	int length1;
	int length2;
	int *values;

public:
	StdIntMatrix2(int *values, int length1, int length2);
	StdIntMatrix2(int **values, int length1, int length2);
	~StdIntMatrix2();
	
	Variant toVariant();
	
	inline int size() {
		return length1;
	}
	
	inline StdIntMatrix1 operator[] (int index) {
		return StdIntMatrix1(values + index * length2, length2);
	}
};

// -------------------------------------------------------------------------
// Simple variant types
// -------------------------------------------------------------------------

Variant intVariant(int value);

#endif
