
package lang.cpp;

import com.ApiCall;
import com.Variant;

public class AiCallbackHandler implements CallbackHandler {
	private CppAiConnector connector;
	private ApiCall call;
	
	private AiLibraryInterface aiLib;
	private PrepareCallCallbackImpl prepareCallback;
	private AddParameterCallbackImpl addParameterCallback;
	private MakeReturnCallCallbackImpl makeCallCallback;
	
	public AiCallbackHandler(CppAiConnector connector,
		AiLibraryInterface aiLib) {
		this.connector = connector;
		this.aiLib = aiLib;
		
		// Store callback references so they're not deleted by Java garbage
		// collector
		prepareCallback = new PrepareCallCallbackImpl(this);
		addParameterCallback = new AddParameterCallbackImpl(this);
		makeCallCallback = new MakeReturnCallCallbackImpl(this);
	}
	
	public void registerCallbacks() {
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
		// Do nothing
	}
	
	public int makeReturnCall() {
		return connector.callGameFunction(call).getIntValue();
	}
}
