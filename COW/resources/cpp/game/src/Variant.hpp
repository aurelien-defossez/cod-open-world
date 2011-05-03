#ifndef __VARIANT_H__
#define __VARIANT_H__

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

typedef struct variant {
	char type;
	void *value;
} Variant;

/*
class Variant {
private:
	char type;
	void *value;
	
public:
	Variant(char type, void *value) {
		this->type = type;
		this->value = value;
	}
	
	Variant(VariantStruct variant) {
		this->type = variant.type;
		this->value = variant.value;
	}
	
	char getType() {
		return type;
	}
	
	void *getValue() {
		return value;
	}
}*/

#endif
