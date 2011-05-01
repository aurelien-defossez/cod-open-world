package test;

import com.sun.jna.Callback;

public interface TestCallback extends Callback {
	void invoke(double value);
}
