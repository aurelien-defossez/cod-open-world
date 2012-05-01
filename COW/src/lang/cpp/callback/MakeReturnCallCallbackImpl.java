
package lang.cpp.callback;

import lang.cpp.CallbackHandler;
import lang.cpp.VariantStruct;

public class MakeReturnCallCallbackImpl implements MakeReturnCallCallback {
	private CallbackHandler callbackHandler;
	
	public MakeReturnCallCallbackImpl(CallbackHandler callbackHandler) {
		this.callbackHandler = callbackHandler;
	}
	
	@Override
	public int invoke(
		int functionId,
		int nbParameters,
		VariantStruct.ByValue parameter1,
		VariantStruct.ByValue parameter2,
		VariantStruct.ByValue parameter3,
		VariantStruct.ByValue parameter4,
		VariantStruct.ByValue parameter5,
		VariantStruct.ByValue parameter6,
		VariantStruct.ByValue parameter7,
		VariantStruct.ByValue parameter8) {
		return this.callbackHandler.makeReturnCall(functionId, nbParameters,
			new VariantStruct.ByValue[] { parameter1, parameter2, parameter3,
				parameter4, parameter5, parameter6, parameter7, parameter8 });
	}
}
