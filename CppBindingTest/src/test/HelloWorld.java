
package test;

import com.sun.jna.Native;

public class HelloWorld {
	public static void main(String[] args) {
		new HelloWorld();
	}
	
	public HelloWorld() {
		AiLibrary lib = null;
		System.setProperty("jna.library.path", ".");
		
		lib = (AiLibrary) Native.loadLibrary("ai", AiLibrary.class);

		// Void with print
		System.out.println("hello();");
		lib.hello();
		System.out.println();
		
		// Parameter + return
		System.out.println("doubleThis(21);");
		System.out.println("Returned value: " + lib.doubleThis(21));
		System.out.println();
		
		// Register callback
		System.out.println("lib.registerCallback(new TestCallbackImpl());");
		lib.registerCallback(new TestCallbackImpl());
		System.out.println();
		
		// Use callback
		System.out.println("lib.sendBack(4.2);");
		lib.sendBack(4.2);
		System.out.println();
	}
}
