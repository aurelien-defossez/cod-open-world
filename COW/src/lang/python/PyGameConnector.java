
package lang.python;

import java.util.Collection;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;
import com.ApiCall;
import com.Variant;
import com.ai.Ai;
import com.game.Game;
import com.game.GameConnector;

public class PyGameConnector extends GameConnector {
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private PyGameCommunicator gameCommunicator;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public PyGameConnector(Game game) {
		super(game);
		
		// Create and initialize Python interpreter
		PythonInterpreter interpreter = new PythonInterpreter();
		
		// Initialize Python
		PyInitializer.initialize(game.getName());
		
		// Load game connector
		interpreter.execfile("resources/python/gameCommunicator.py");
		PyObject gameCommunicatorClass = interpreter.get("GameCommunicator");
		PyObject gameConnectorPy = gameCommunicatorClass.__call__();
		gameCommunicator =
			(PyGameCommunicator) gameConnectorPy
				.__tojava__(PyGameCommunicator.class);
		
		// Load API
		interpreter.execfile("games/" + game.getName() + "/engine/api.py");
		PyObject gameApiClass = interpreter.get("Api");
		PyObject apiPy = gameApiClass.__call__();
		
		// Load API call demultiplexer
		interpreter.execfile("games/" + game.getName()
			+ "/engine/apiCallDemux.py");
		PyObject ApiDemuxClass = interpreter.get("ApiCallDemux");
		PyObject apiDemuxPy = ApiDemuxClass.__call__();
		
		// Load game
		interpreter.execfile("games/" + game.getName() + "/engine/game.py");
		PyObject gameClass = interpreter.get("Game");
		PyObject gamePy = gameClass.__call__();
		
		// Initialize connector
		gameCommunicator.init(this, gamePy, apiPy, apiDemuxPy);
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	@Override
	public void initGame(Collection<Ai> ais, String[] parameters) {
		// Set game parameters
		PyString[] pyParameters = new PyString[parameters.length];
		for (int i = 0; i < parameters.length; i++) {
			pyParameters[i] = new PyString(parameters[i]);
		}
		gameCommunicator.initGame(pyParameters);
		
		// Add AIs to the game
		for (Ai ai : ais) {
			gameCommunicator.addAi(new PyInteger(ai.getId()),
				new PyString(ai.getPlayerName()), new PyString(ai.getName()));
		}
	}
	
	@Override
	public void disqualifyAi(Ai ai, String reason) {
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
	public Variant performGameFunction(ApiCall call, Ai ai) {
		int nbParameters = call.getParameters().length;
		Object[] pyParameters = new Object[nbParameters];
		for (int i = 0; i < nbParameters; i++) {
			pyParameters[i] = call.getParameter(i).getValue();
		}
		
		return gameCommunicator.performGameFunction(call.getFunctionId(),
			ai.getId(), pyParameters);
	}
}
