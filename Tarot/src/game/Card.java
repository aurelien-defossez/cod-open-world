
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
	
	public final static int VALET = 11;
	public final static int CAVALIER = 12;
	public final static int DAME = 13;
	public final static int ROI = 14;
	
	private int code;
	private int color;
	private int value;
	private boolean oudler;
	
	public Card(int code) {
		this.code = code;
		this.color = code & COLOR_MASK;
		this.value = code & VALUE_MASK;
		
		// Extract info
		this.oudler = (color == ATOUT &&
			(value == 0 || value == 1 || value == 21));
	}
	
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
			return (oudler) ? 4.5 : 0.5;
		} else {
			switch (value) {
			case VALET:
				return 1.5;
			case CAVALIER:
				return 2.5;
			case DAME:
				return 3.5;
			case ROI:
				return 4.5;
			default:
				return 0.5;
			}
		}
	}
	
	public boolean isOudler() {
		return oudler;
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
