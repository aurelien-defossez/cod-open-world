#ifndef __VARIANT_H__
#define __VARIANT_H__

#include <vector>

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

typedef std::vector<int> IntMatrix1;
typedef std::vector<IntMatrix1> IntMatrix2;

struct intMatrix1 {
	int length;
	int values[];
};

struct intMatrix2 {
	int length;
	int length2;
	int values[];
};

typedef union variant {
	bool boolValue;
	int intValue;
	double doubleValue;
	char *stringValue;
	
	struct intMatrix1 *intMatrix1;
	struct intMatrix2 *intMatrix2;
} Variant;

IntMatrix1 toIntMatrix1(struct intMatrix1 *matrix);
IntMatrix1 toIntMatrix1(int values[], int length);

IntMatrix2 toIntMatrix2(struct intMatrix2 *matrix);
IntMatrix2 toIntMatrix2(int values[], int length, int length2);

#endif
