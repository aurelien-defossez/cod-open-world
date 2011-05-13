package lang.cpp;

import com.Variant;
import com.sun.jna.Pointer;
import com.sun.jna.Union;

public class VariantUnion extends Union {
	public boolean boolValue;
	public int intValue;
	public double doubleValue;
	public String stringValue;
	
	public IntMatrix.ByReference intMatrix;
	public Pointer returnIntMatrix;
	
	public void setValue(Variant variant) {
		switch (variant.getType()) {
		case VOID:
			setType(int.class);
			this.intValue = 0;
			break;
		
		case BOOL:
			setType(boolean.class);
			this.boolValue = (Boolean) variant.getValue();
			break;
		
		case INT:
			setType(int.class);
			this.intValue = (Integer) variant.getValue();
			break;
		
		case DOUBLE:
			setType(double.class);
			this.doubleValue = (Double) variant.getValue();
			break;
		
		case STRING:
			setType(String.class);
			this.stringValue = (String) variant.getValue();
			break;
		
		case BOOL_MATRIX1:
			// TODO
			break;
		
		case BOOL_MATRIX2:
			// TODO
			break;
		
		case BOOL_MATRIX3:
			// TODO
			break;
		
		case INT_MATRIX1:
			setType(IntMatrix.ByReference.class);
			this.intMatrix = (((int[])variant.getValue()).length == 0) ?
				new IntMatrix.ByReference(new int[] { 0 }) :
				new IntMatrix.ByReference((int[]) variant.getValue());
			break;
		
		case INT_MATRIX2:
			setType(IntMatrix.ByReference.class);
			this.intMatrix = (((int[][])variant.getValue()).length == 0) ?
				new IntMatrix.ByReference(new int[][] { { 0 } }) :
				new IntMatrix.ByReference((int[][]) variant.getValue());
			break;
		
		case INT_MATRIX3:
			// TODO
			break;
		
		case DOUBLE_MATRIX1:
			// TODO
			break;
		
		case DOUBLE_MATRIX2:
			// TODO
			break;
		
		case DOUBLE_MATRIX3:
			// TODO
			break;
		
		case STRING_MATRIX1:
			// TODO
			break;
		
		case STRING_MATRIX2:
			// TODO
			break;
		
		case STRING_MATRIX3:
			// TODO
			break;
		}
	}
	
	public static class ByValue extends VariantUnion implements Union.ByValue {
		// Empty class
	}
}
