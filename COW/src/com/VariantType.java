
package com;

/**
 * This enumeration represents the type of the variant.
 */
public enum VariantType {
	// Void
	VOID((byte) 0),

	// Native types
	BOOL((byte) 1), INT((byte) 2), DOUBLE((byte) 3), STRING((byte) 4),

	// Bool matrices
	BOOL_MATRIX1((byte) 5), BOOL_MATRIX2((byte) 6), BOOL_MATRIX3((byte) 7),

	// Integer matrices
	INT_MATRIX1((byte) 8), INT_MATRIX2((byte) 9), INT_MATRIX3((byte) 10),

	// Double matrices
	DOUBLE_MATRIX1((byte) 11), DOUBLE_MATRIX2((byte) 12), DOUBLE_MATRIX3(
		(byte) 13),

	// String matrices
	STRING_MATRIX1((byte) 14), STRING_MATRIX2((byte) 15), STRING_MATRIX3(
		(byte) 16);
	
	private final byte id;
	
	private VariantType(byte id) {
		this.id = id;
	}
	
	public byte getId() {
		return id;
	}
}
