
package lang.cpp;

import lang.cpp.callback.AddParameterCallbackImpl;
import lang.cpp.callback.MakeCompleteReturnCallCallbackImpl;
import lang.cpp.callback.MakeReturnCallCallbackImpl;
import lang.cpp.callback.PrepareCallCallbackImpl;
import com.ApiCall;
import com.Variant;

public class AiCallbackHandler implements CallbackHandler {
	private CppAiConnector connector;
	private ApiCall call;
	private int parametersCt;
	
	private AiLibraryInterface aiLib;
	private PrepareCallCallbackImpl prepareCallback;
	private AddParameterCallbackImpl addParameterCallback;
	private MakeReturnCallCallbackImpl makeCallCallback;
	private MakeCompleteReturnCallCallbackImpl makeCompleteReturnCallCallback;
	
	public AiCallbackHandler(CppAiConnector connector,
		AiLibraryInterface aiLib) {
		this.connector = connector;
		this.aiLib = aiLib;
		
		// Store callback references so they're not deleted by Java garbage
		// collector
		prepareCallback = new PrepareCallCallbackImpl(this);
		addParameterCallback = new AddParameterCallbackImpl(this);
		makeCallCallback = new MakeReturnCallCallbackImpl(this);
		makeCompleteReturnCallCallback = new MakeCompleteReturnCallCallbackImpl(this);
	}
	
	public void registerCallbacks() {
		aiLib.registerCallbacks(prepareCallback, addParameterCallback,
			makeCallCallback, makeCompleteReturnCallCallback);
	}
	
	public void prepareCall(int functionId, int nbParameters) {
		call = new ApiCall((short) functionId, nbParameters);
	}
	
	public void addParameter(VariantStruct parameter) {
		call.add(new Variant(parameter));
	}
	
	public void makeCall() {
		// Do nothing
	}
	
	public int makeReturnCall() {
		return connector.callGameFunction(call).getIntValue();
	}
	
	public void makeCompleteCall(int functionId, int nbParameters, VariantStruct.ByValue[] parameters) {
		// Do nothing
	}
	
	@Override
	public int makeCompleteReturnCall(int functionId, int nbParameters, VariantStruct.ByValue[] parameters) {
		if (parametersCt == 0) {
			call = new ApiCall((short) functionId, nbParameters);
			parametersCt = nbParameters;
		}
		
		int parametersToAdd = Math.min(parametersCt, 8);
		for (int i = 0; i < parametersToAdd; i++) {
			--parametersCt;
			call.add(new Variant(parameters[i]));
		}
		
		if (parametersCt == 0) {
			return connector.callGameFunction(call).getIntValue();
		} else {
			return -1;
		}
	}
}
