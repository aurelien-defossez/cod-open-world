package lang.java;

import com.ApiCall;

public abstract class JavaGameCommander {
	private static JavaGameCommunicator gameCommunicator;
	
	public void setCommunicator(JavaGameCommunicator communicator) {
		gameCommunicator = communicator;
	}
	
	protected static void callAiFunction(short aiId, ApiCall call) {
		gameCommunicator.callAiFunction(aiId, call);
	}
}
