package lang.cpp;

import com.sun.jna.Callback;

public interface PrepareCallCallback extends Callback {
	void invoke(int functionId, int nbParameters);
}
