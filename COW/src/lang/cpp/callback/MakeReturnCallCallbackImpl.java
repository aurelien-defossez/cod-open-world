
package lang.cpp.callback;

import lang.cpp.CallbackHandler;

public class MakeReturnCallCallbackImpl implements MakeReturnCallCallback {
	private CallbackHandler callbackHandler;
	
	public MakeReturnCallCallbackImpl(CallbackHandler callbackHandler) {
		this.callbackHandler = callbackHandler;
	}
	
	@Override
	public int callback() {
		return callbackHandler.makeReturnCall();
	}
}
