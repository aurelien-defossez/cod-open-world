
package com.python;

import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PyString;
import com.Variant;

public interface PyGameCommunicator {
	// -------------------------------------------------------------------------
	// Abstract methods
	// -------------------------------------------------------------------------
	
	public void init(PyGameEngine framework, PyObject game, PyObject api,
			PyObject apiDemux);
	
	public void initGame();
	
	public void addAi(PyInteger aiId, PyString playerName, PyString aiName);
	
	public void endGame();
	
	public void play();
	
	public Variant
			callGameApi(short function, short aiId, Object[] pyParameters);
	
	public void removeAi(PyInteger pyInteger, PyString pyString);
}
