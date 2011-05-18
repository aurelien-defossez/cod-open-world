
package lang.cpp.callback;

import lang.cpp.CallbackHandler;

public class PrepareCallCallbackImpl implements PrepareCallCallback {
	private CallbackHandler callbackHandler;
	
	public PrepareCallCallbackImpl(CallbackHandler callbackHandler) {
		this.callbackHandler = callbackHandler;
	}
	
	@Override
	public void invoke(int functionId, int nbParameters) {
		callbackHandler.prepareCall(functionId, nbParameters);
	}
}