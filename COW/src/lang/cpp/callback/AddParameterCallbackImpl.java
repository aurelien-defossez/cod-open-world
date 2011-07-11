
package lang.cpp.callback;

import lang.cpp.CallbackHandler;
import lang.cpp.VariantStruct;

public class AddParameterCallbackImpl implements AddParameterCallback {
	private CallbackHandler callbackHandler;
	
	public AddParameterCallbackImpl(CallbackHandler callbackHandler) {
		this.callbackHandler = callbackHandler;
	}
	
	@Override
	public void invoke(VariantStruct.ByValue parameter) {
		callbackHandler.addParameter(parameter);
	}
}
