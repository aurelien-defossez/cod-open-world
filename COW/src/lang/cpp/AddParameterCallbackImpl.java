package lang.cpp;

import lang.cpp.GameLibraryInterface.VariantStruct;

public class AddParameterCallbackImpl implements AddParameterCallback {
	private GameCallbackHandler callbackHandler;
	
	public AddParameterCallbackImpl(GameCallbackHandler callbackHandler) {
		this.callbackHandler = callbackHandler;
	}

	@Override
	public void invoke(VariantStruct.ByValue parameter) {
		callbackHandler.addParameter(parameter);
	}
}
