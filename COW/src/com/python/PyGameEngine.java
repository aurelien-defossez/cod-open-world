
package com.python;

import java.util.Collection;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;
import sim.LiveSimulator;
import com.Ai;
import com.ApiCall;
import com.GameEngine;
import com.Variant;

public class PyGameEngine extends GameEngine {
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private PyGameCommunicator gameCommunicator;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public PyGameEngine(LiveSimulator simulator, String gameName) {
		super(simulator, gameName);
		
		// Create and initialize Python interpreter
		PythonInterpreter interpreter = new PythonInterpreter();
		
		// Initialize Python
		PyInitializer.initialize(gameName);
		
		// Load game connector
		interpreter.execfile("resources/python/gameCommunicator.py");
		PyObject gameCommunicatorClass = interpreter.get("GameCommunicator");
		PyObject gameConnectorPy = gameCommunicatorClass.__call__();
		gameCommunicator =
				(PyGameCommunicator) gameConnectorPy
						.__tojava__(PyGameCommunicator.class);
		
		// Load API
		interpreter.execfile("games/" + gameName + "/engine/api.py");
		PyObject gameApiClass = interpreter.get("Api");
		PyObject apiPy = gameApiClass.__call__();
		
		// Load API call demultiplexer
		interpreter.execfile("games/" + gameName + "/engine/apiCallDemux.py");
		PyObject ApiDemuxClass = interpreter.get("ApiCallDemux");
		PyObject apiDemuxPy = ApiDemuxClass.__call__();
		
		// Load game
		interpreter.execfile("games/" + gameName + "/engine/game.py");
		PyObject gameClass = interpreter.get("Game");
		PyObject gamePy = gameClass.__call__();
		
		// Initialize connector
		gameCommunicator.init(this, gamePy, apiPy, apiDemuxPy);
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	@Override
	public void initGame(Collection<Ai> ais) {
		gameCommunicator.initGame();
		
		for (Ai ai : ais) {
			gameCommunicator.addAi(new PyInteger(ai.getId()),
					new PyString(ai.getPlayerName()),
					new PyString(ai.getName()));
		}
	}
	
	@Override
	public void disqualifyAi(Ai ai, String reason) {
		System.out.println("Remove Python AI.");
		gameCommunicator.removeAi(new PyInteger(ai.getId()), new PyString(
				reason));
	}
	
	@Override
	public void endGame() {
		gameCommunicator.endGame();
	}
	
	@Override
	public void play() {
		gameCommunicator.play();
	}
	
	@Override
	public Variant callGameApi(ApiCall call, Ai ai) {
		int nbParameters = call.getParameters().length;
		Object[] pyParameters = new Object[nbParameters];
		for (int i = 0; i < nbParameters; i++) {
			pyParameters[i] = call.getParameter(i).getValue();
		}
		
		return gameCommunicator.callGameApi(call.getFunctionId(), ai.getId(),
				pyParameters);
	}
	
	public void callViewApi(short function, Variant[] parameters) {
		simulator.callViewApi(new ApiCall(function, parameters));
	}
	
	public void executeAi(short aiId, byte phase) {
		simulator.executeAi(aiId, phase);
	}
}
