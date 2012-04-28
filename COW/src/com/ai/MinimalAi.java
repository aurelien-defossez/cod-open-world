package com.ai;

import main.CowException;
import com.ApiCall;

public class MinimalAi extends Ai {
	public MinimalAi(short aiId) throws CowException {
		super(aiId);
	}
	
	public MinimalAi(short aiId, String aiName, String playerName) {
		super(aiId, aiName, playerName);
	}

	@Override
	public void performAiFunction(ApiCall call) {
		// Do nothing
	}

	@Override
	public void stop() {
		// Do nothing
	}
}
