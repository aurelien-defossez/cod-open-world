
package lang.cpp;

import com.ApiCall;
import com.Variant;

public class AiCallbackHandler implements CallbackHandler {
	private CppAiConnector connector;
	private ApiCall call;
	
	private PrepareCallCallbackImpl prepareCallback;
	private AddParameterCallbackImpl addParameterCallback;
	private MakeCallCallbackImpl makeCallCallback;
	
	public AiCallbackHandler(CppAiConnector connector,
		AiLibraryInterface aiLib) {
		this.connector = connector;
		
		// Store callback references so they're not deleted by Java garbage
		// collector
		prepareCallback = new PrepareCallCallbackImpl(this);
		addParameterCallback = new AddParameterCallbackImpl(this);
		makeCallCallback = new MakeCallCallbackImpl(this);
		
		// Register callbacks
		aiLib.registerCallbacks(prepareCallback, addParameterCallback,
			makeCallCallback);
	}
	
	public void prepareCall(int functionId, int nbParameters) {
		call = new ApiCall((short) functionId, nbParameters);
	}
	
	public void addParameter(VariantStruct parameter) {
		call.add(new Variant(parameter));
	}
	
	public void makeCall() {
		// Call Game API function
		// TODO: return variant
		connector.callGameFunction(call);
	}
}
