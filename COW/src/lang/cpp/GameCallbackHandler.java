
package lang.cpp;

import com.ApiCall;
import com.Variant;
import com.game.GameConnector;
import lang.cpp.GameLibraryInterface.VariantStruct;

public class GameCallbackHandler {
	private CppGameConnector connector;
	private ApiCall call;
	
	public GameCallbackHandler(CppGameConnector connector,
		GameLibraryInterface gameLib) {
		this.connector = connector;
		
		gameLib.registerCallbacks(new PrepareCallCallbackImpl(this),
			new AddParameterCallbackImpl(this), new MakeCallCallbackImpl(this));
	}
	
	public void prepareCall(int functionId, int nbParameters) {
		call = new ApiCall((short) functionId, nbParameters);
	}
	
	public void addParameter(VariantStruct parameter) {
		call.add(new Variant(parameter));
	}
	
	public void makeCall() {
		switch (call.getFunctionId()) {
		case GameConnector.SET_FRAME:
			connector.setFrame();
			break;
		
		case GameConnector.SET_TIMEOUT:
			connector.setTimeout(call.getParameter(0).getIntValue());
			break;
		
		case GameConnector.SET_SCORE:
			connector.setScore(call.getParameter(0).getIntValue().shortValue(),
				call.getParameter(1).getIntValue());
			break;
		
		case GameConnector.INCREMENT_SCORE:
			connector.incrementScore(call.getParameter(0).getIntValue()
				.shortValue(), call.getParameter(1).getIntValue());
			break;
		
		case GameConnector.CALL_AI_FUNCTION:
			// Create new AI API call
			ApiCall aiApiCall =
				new ApiCall(call.getParameter(1).getIntValue().shortValue(),
					call.getParameters().length - 2);
			
			// Retrieve API call parameters, excluding the first two parameters
			for (int i = 2; i < call.getParameters().length; i++) {
				aiApiCall.add(call.getParameter(i));
			}
			
			// Call AI API function
			connector.callAiFunction(call.getParameter(0).getIntValue()
				.shortValue(), aiApiCall);
			break;
		}
	}
}
