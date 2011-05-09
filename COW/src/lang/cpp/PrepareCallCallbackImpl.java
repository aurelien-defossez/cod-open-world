
package lang.cpp;

public class PrepareCallCallbackImpl implements PrepareCallCallback {
	private GameCallbackHandler callbackHandler;
	
	public PrepareCallCallbackImpl(GameCallbackHandler callbackHandler) {
		this.callbackHandler = callbackHandler;
	}
	
	@Override
	public void invoke(int functionId, int nbParameters) {
		callbackHandler.prepareCall(functionId, nbParameters);
	}
}
