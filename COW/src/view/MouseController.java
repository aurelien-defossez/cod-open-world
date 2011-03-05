
package view;

import java.awt.Canvas;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Vector;

public class MouseController implements MouseListener, MouseWheelListener {
	private Vector<ButtonListener> listeners;
	private Vector<Integer> buttonHold;
	private Canvas canvas;
	
	public MouseController() {
		this.listeners = new Vector<ButtonListener>();
		this.buttonHold = new Vector<Integer>();
	}
	
	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		int code = e.getButton();
		
		for (ButtonListener listener : listeners) {
			listener.buttonPressed(code);
		}
		
		if (!buttonHold.contains(code)) {
			buttonHold.add(code);
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		int code = e.getButton();
		buttonHold.removeElement(code);
		
		for (ButtonListener listener : listeners) {
			listener.buttonReleased(code);
		}
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		for (ButtonListener listener : listeners) {
			listener.scroll(e.getWheelRotation());
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
	}
	
	public void addListener(ButtonListener listener) {
		listeners.add(listener);
	}
	
	public void removeListener(ButtonListener listener) {
		listeners.removeElement(listener);
	}
	
	public boolean isPressed(int button) {
		return buttonHold.contains(button);
	}
	
	public Point getAbsolutePosition() {
		return MouseInfo.getPointerInfo().getLocation();
	}
	
	public Point getLocalPosition() {
		return canvas.getMousePosition();
	}
}
