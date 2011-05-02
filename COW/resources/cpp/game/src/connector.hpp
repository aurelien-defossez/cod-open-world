#ifndef __CONNECTOR_H__
#define __CONNECTOR_H__

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

void test(Variant variant[]);
void init(int nbParameters, char *parameters[]);
void addAi(short aiId, char *aiName, char *playerName);
void play();
void endGame();
void disqualifyAi(char *aiName, char *reason);
void performGameFunction(int functionId, int nbParameters,
		Variant parameters[]);

#endif

