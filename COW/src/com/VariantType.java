/**
 * Variant Type - This enumeration represents the type of the variant.
 */

package com;

public enum VariantType {
	// -------------------------------------------------------------------------
	// Enumeration values
	// -------------------------------------------------------------------------
	
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
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	/**
	 * The type id.
	 */
	private final byte id;
	
	/**
	 * The variant types, ordered by id.
	 */
	private static final VariantType[] types =
		{ VOID, BOOL, INT, DOUBLE, STRING, BOOL_MATRIX1, BOOL_MATRIX2,
			BOOL_MATRIX3, INT_MATRIX1, INT_MATRIX2, INT_MATRIX3,
			DOUBLE_MATRIX1, DOUBLE_MATRIX2, DOUBLE_MATRIX3, STRING_MATRIX1,
			STRING_MATRIX2, STRING_MATRIX3 };
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	/**
	 * Constructs a variant type with an associated id.
	 */
	private VariantType(byte id) {
		this.id = id;
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	/**
	 * Returns the variant type id.
	 * 
	 * @return the variant type id.
	 */
	public byte getId() {
		return id;
	}
	
	/**
	 * Returns the variant type from its id.
	 * 
	 * @param id the variant type id.
	 * @return the corresponding variant type.
	 */
	public static VariantType valueOf(byte id) {
		return types[id];
	}
}
