
package view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class KeyboardController implements KeyListener {
	private Vector<KeyboardListener> listeners;
	private Vector<Integer> keyHold;
	
	public KeyboardController() {
		this.listeners = new Vector<KeyboardListener>();
		this.keyHold = new Vector<Integer>();
	}
	
	@Override
	public void keyPressed(KeyEvent key) {
		if (!keyHold.contains(key.getKeyCode())) {
			keyHold.add(key.getKeyCode());
		}
	}
	
	@Override
	public void keyReleased(KeyEvent key) {
		int code = key.getKeyCode();
		keyHold.removeElement(code);
		
		for (KeyboardListener listener : listeners) {
			listener.keyReleased(code);
		}
	}
	
	@Override
	public void keyTyped(KeyEvent key) {
		// Do nothing
	}
	
	public void addListener(KeyboardListener listener) {
		listeners.add(listener);
	}
	
	public void removeListener(KeyboardListener listener) {
		listeners.removeElement(listener);
	}
	
	public void disableAll() {
		keyHold.clear();
	}
	
	public boolean isPressed(int key) {
		return keyHold.contains(key);
	}
}
