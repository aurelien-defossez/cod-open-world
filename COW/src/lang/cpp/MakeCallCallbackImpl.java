package lang.cpp;

public class MakeCallCallbackImpl implements MakeCallCallback {
	private CallbackHandler callbackHandler;
	
	public MakeCallCallbackImpl(CallbackHandler callbackHandler) {
		this.callbackHandler = callbackHandler;
	}
	
	@Override
	public void invoke() {
		callbackHandler.makeCall();
	}
}
