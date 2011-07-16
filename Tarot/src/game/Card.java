
package game;

import gameConn.TarotEngine;

public class Card {
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------
	
	public final static byte COLOR_MASK = (byte) 0xE0;
	public final static byte VALUE_MASK = (byte) 0x1F;
	
	public final static int COEUR = TarotEngine.COEUR_1 & COLOR_MASK;
	public final static int CARREAU = TarotEngine.CARREAU_1 & COLOR_MASK;
	public final static int PIQUE = TarotEngine.PIQUE_1 & COLOR_MASK;
	public final static int TREFLE = TarotEngine.TREFLE_1 & COLOR_MASK;
	public final static int ATOUT = TarotEngine.ATOUT_1 & COLOR_MASK;
	
	public final static int VALET = 11;
	public final static int CAVALIER = 12;
	public final static int DAME = 13;
	public final static int ROI = 14;
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private int code;
	private int color;
	private int value;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public Card(int code) {
		this.code = code;
		this.color = code & COLOR_MASK;
		this.value = code & VALUE_MASK;
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	public int getCode() {
		return code;
	}
	
	public int getColor() {
		return color;
	}
	
	public int getValue() {
		return value;
	}
	
	public double getPoints() {
		if (color == ATOUT) {
			return (isOudler()) ? 4.5 : 0.5;
		} else {
			switch (value) {
			case ROI:
				return 4.5;
			case DAME:
				return 3.5;
			case CAVALIER:
				return 2.5;
			case VALET:
				return 1.5;
			default:
				return 0.5;
			}
		}
	}
	
	public boolean isOudler() {
		return (code == Game.EXCUSE || code == Game.ATOUT_1 || code == Game.ATOUT_21);
	}
	
	public boolean isBetterThan(Card otherCard, int desiredColor) {
		int otherColor = otherCard.getColor();
		int otherValue = otherCard.getValue();
		
		return
		// Both of the desired color but this card has a better value
		((color == desiredColor && otherColor == desiredColor && value > otherValue)
			// This card has the desired color and the other has not
			|| (color == desiredColor && otherColor != desiredColor && otherColor != Card.ATOUT)
			// This card is an atout and the other not
			|| (color == Card.ATOUT && otherColor != Card.ATOUT)
			// Both are atout but this card has a better value
			|| (color == Card.ATOUT && otherColor == Card.ATOUT && value > otherValue)
		// Other card is the excuse
		|| (otherCard.getCode() == Game.EXCUSE));
	}
	
	public String toString() {
		return getStringValue() + getStringColor();
	}
	
	// -------------------------------------------------------------------------
	// Private methods
	// -------------------------------------------------------------------------
	
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
		if (color == ATOUT || value < VALET) {
			return Integer.toString(value);
		} else {
			switch (value) {
			case VALET:
				return "V";
			case CAVALIER:
				return "C";
			case DAME:
				return "D";
			case ROI:
				return "R";
			default:
				return Integer.toString(value);
			}
		}
	}
}
