package test;

import com.sun.jna.Library;

public interface AiLibrary extends Library {
	void hello();
	int doubleThis(int value);
	void registerCallback(TestCallback callback);
	void sendBack(double value);
}
