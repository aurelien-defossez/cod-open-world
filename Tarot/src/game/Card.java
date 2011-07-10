
package game;

import gameConn.TarotEngine;

public class Card {
	public final static byte COLOR_MASK = (byte) 0xE0;
	public final static byte VALUE_MASK = (byte) 0x1F;
	
	public final static int COEUR = TarotEngine.COEUR_1 & COLOR_MASK;
	public final static int CARREAU = TarotEngine.CARREAU_1 & COLOR_MASK;
	public final static int PIQUE = TarotEngine.PIQUE_1 & COLOR_MASK;
	public final static int TREFLE = TarotEngine.TREFLE_1 & COLOR_MASK;
	public final static int ATOUT = TarotEngine.ATOUT_1 & COLOR_MASK;
	
	private int code;
	private int color;
	private int value;
	private boolean oudler;
	
	public Card(int code) {
		this.code = code;
		
		// Extract color
		this.color = code & COLOR_MASK;
		
		// Extract value
		this.value = code & VALUE_MASK;
		
		// Extract info
		this.oudler = (color == ATOUT &&
			(value == 0 || value == 1 || value == 21));
	}
	
	public int getCode() {
		return code;
	}
	
	public boolean isOudler() {
		return oudler;
	}
	
	public String toString() {
		return getStringValue() + getStringColor();
	}
	
	private String getStringColor() {
		switch (color) {
		case ATOUT:
			return "#";
		case COEUR:
			return "C";
		case CARREAU:
			return "K";
		case PIQUE:
			return "P";
		case TREFLE:
			return "T";
		default:
			return ".";
		}
	}
	
	private String getStringValue() {
		if (color == ATOUT) {
			return Integer.toString(value);
		} else {
			switch (value) {
			case 11:
				return "V";
			case 12:
				return "C";
			case 13:
				return "D";
			case 14:
				return "R";
			default:
				return Integer.toString(value);
			}
		}
	}
}
