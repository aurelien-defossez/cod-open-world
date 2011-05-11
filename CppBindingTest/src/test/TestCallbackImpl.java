package test;

public class TestCallbackImpl implements TestCallback {
	public void invoke(double value) {
		System.out.println("Received from C++: " + value);
	}
}
