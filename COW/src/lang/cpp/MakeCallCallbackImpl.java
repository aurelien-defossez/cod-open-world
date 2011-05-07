package lang.cpp;

public class MakeCallCallbackImpl implements MakeCallCallback {
	private GameCallbackHandler callbackHandler;
	
	public MakeCallCallbackImpl(GameCallbackHandler callbackHandler) {
		this.callbackHandler = callbackHandler;
	}
	
	@Override
	public void invoke() {
		callbackHandler.makeCall();
	}
}
