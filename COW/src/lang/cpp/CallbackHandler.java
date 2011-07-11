
package lang.cpp;

public interface CallbackHandler {
	public abstract void prepareCall(int functionId, int nbParameters);
	
	public abstract void addParameter(VariantStruct parameter);
	
	public abstract void makeCall();
	
	public abstract int makeReturnCall();
}
