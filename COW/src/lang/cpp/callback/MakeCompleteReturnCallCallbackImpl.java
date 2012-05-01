
package lang.cpp.callback;

import lang.cpp.CallbackHandler;
import lang.cpp.VariantStruct;

public class MakeCompleteReturnCallCallbackImpl implements MakeCompleteCallCallback {
	private CallbackHandler callbackHandler;
	
	public MakeCompleteReturnCallCallbackImpl(CallbackHandler callbackHandler) {
		this.callbackHandler = callbackHandler;
	}
	
	@Override
	public void invoke(
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
		this.callbackHandler.makeCompleteCall(functionId, nbParameters,
			new VariantStruct.ByValue[] { parameter1, parameter2, parameter3,
				parameter4, parameter5, parameter6, parameter7, parameter8 });
	}
}
