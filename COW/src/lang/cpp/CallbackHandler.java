
package lang.cpp;

public interface CallbackHandler {
	void makeCall(int functionId, int nbParameters, VariantStruct.ByValue[] parameters);
	
	int makeReturnCall(int functionId, int nbParameters, VariantStruct.ByValue[] parameters);
}
