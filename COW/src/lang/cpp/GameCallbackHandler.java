
package lang.cpp;

import lang.cpp.callback.AddParameterCallbackImpl;
import lang.cpp.callback.MakeCallCallbackImpl;
import lang.cpp.callback.MakeCompleteCallCallbackImpl;
import lang.cpp.callback.PrepareCallCallbackImpl;
import com.ApiCall;
import com.Variant;
import com.game.GameConnector;

public class GameCallbackHandler implements CallbackHandler {
	private CppGameConnector connector;
	private ApiCall call;
	private int parametersCt;
	
	private GameLibraryInterface gameLib;
	private PrepareCallCallbackImpl prepareCallback;
	private AddParameterCallbackImpl addParameterCallback;
	private MakeCallCallbackImpl makeCallCallback;
	
	public GameCallbackHandler(CppGameConnector connector,
		GameLibraryInterface gameLib) {
		this.connector = connector;
		this.gameLib = gameLib;
		
		// Store callback references so they're not deleted by Java garbage
		// collector
		prepareCallback = new PrepareCallCallbackImpl(this);
		addParameterCallback = new AddParameterCallbackImpl(this);
		makeCallCallback = new MakeCallCallbackImpl(this);
	}
	
	public void registerCallbacks() {
		// Register callbacks
		gameLib.registerCallbacks(prepareCallback, addParameterCallback,
			makeCallCallback);
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
			connector.callAiFunction(call.getParameter(0).getIntValue().shortValue(), aiApiCall);
			break;
		
		case GameConnector.STOP_AI:
			connector.stopAi(call.getParameter(0).getIntValue().shortValue());
			break;
		
		case GameConnector.THROW_EXCEPTION:
			connector.throwException(call.getParameter(0).getStringValue());
			break;
		
		// View functions
		default:
			connector.callViewFunction(call);
			break;
		}
	}
	
	public int makeReturnCall() {
		// Not used by game
		return 0;
	}
	
	public void makeCompleteCall(int functionId, int nbParameters, VariantStruct.ByValue[] parameters) {
		if (parametersCt == 0) {
			call = new ApiCall((short) functionId, nbParameters);
			parametersCt = nbParameters;
		}
		
		int parametersToAdd = Math.min(parametersCt, 8);
		for (int i = 0; i < parametersToAdd; i++) {
			--parametersCt;
			call.add(new Variant(parameters[i]));
		}
		
		if (parametersCt == 0) {
			makeCall();
		}
	}
	
	@Override
	public int makeCompleteReturnCall(int functionId, int nbParameters, VariantStruct.ByValue[] parameters) {
		// Not used
		return 0;
	}
}
