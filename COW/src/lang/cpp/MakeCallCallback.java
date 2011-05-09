package lang.cpp;

import com.sun.jna.Callback;

public interface MakeCallCallback extends Callback {
	public void invoke();
}
