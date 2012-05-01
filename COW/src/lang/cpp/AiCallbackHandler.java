
package lang.cpp;

import lang.cpp.callback.MakeReturnCallCallbackImpl;
import com.ApiCall;
import com.Variant;

public class AiCallbackHandler implements CallbackHandler {
	private CppAiConnector connector;
	private ApiCall call;
	private int parametersCt;
	
	private AiLibraryInterface aiLib;
	private MakeReturnCallCallbackImpl makeCompleteReturnCallCallback;
	
	public AiCallbackHandler(CppAiConnector connector,
		AiLibraryInterface aiLib) {
		this.connector = connector;
		this.aiLib = aiLib;
		
		// Store callback references so they're not deleted by Java garbage
		// collector
		makeCompleteReturnCallCallback = new MakeReturnCallCallbackImpl(this);
	}
	
	public void registerCallbacks() {
		aiLib.registerCallbacks(makeCompleteReturnCallCallback);
	}
	
	public void makeCall(int functionId, int nbParameters, VariantStruct.ByValue[] parameters) {
		// Do nothing
	}
	
	@Override
	public int makeReturnCall(int functionId, int nbParameters, VariantStruct.ByValue[] parameters) {
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
