#ifndef __VARIANT_H__
#define __VARIANT_H__

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

class IntMatrix1 {
private:
	bool allocated;
	int length;
	int *values;

public:
	IntMatrix1(int length);
	IntMatrix1(int length, int *values);
	~IntMatrix1();
	
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

class IntMatrix2 {
private:
	bool allocated;
	int length[2];
	int *values;

public:
	IntMatrix2(int length1, int length2);
	IntMatrix2(int length1, int length2, int *values);
	IntMatrix2(int length1, int length2, int **values);
	~IntMatrix2();
	
	Variant toVariant();
	
	inline int size() {
		return length[0];
	}
	
	inline IntMatrix1 operator[] (int index) {
		return IntMatrix1(length[1], values + index * length[1]);
	}
};

// -------------------------------------------------------------------------
// Simple variant types
// -------------------------------------------------------------------------

Variant voidVariant();
Variant toVariant(bool value);
Variant toVariant(int value);
Variant toVariant(double value);

// -------------------------------------------------------------------------
// From variant functions
// -------------------------------------------------------------------------

bool boolValue(Variant var);
int intValue(Variant var);
double doubleValue(Variant var);
char *stringValue(Variant var);

#endif
