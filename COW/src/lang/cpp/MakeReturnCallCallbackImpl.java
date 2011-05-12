package lang.cpp;

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
