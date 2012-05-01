
package lang.cpp;

public interface CallbackHandler {
	void prepareCall(int functionId, int nbParameters);
	
	void addParameter(VariantStruct parameter);
	
	void makeCall();
	
	int makeReturnCall();
	
	void makeCompleteCall(int functionId, int nbParameters, VariantStruct.ByValue[] parameters);
	
	int makeCompleteReturnCall(int functionId, int nbParameters, VariantStruct.ByValue[] parameters);
}
