package ai;

import game.Api;

public class Card {
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------
	
	public final static byte COLOR_MASK = (byte) 0xE0;
	public final static byte VALUE_MASK = (byte) 0x1F;
	
	public final static int COEUR = Api.COEUR_1 & COLOR_MASK;
	public final static int CARREAU = Api.CARREAU_1 & COLOR_MASK;
	public final static int PIQUE = Api.PIQUE_1 & COLOR_MASK;
	public final static int TREFLE = Api.TREFLE_1 & COLOR_MASK;
	public final static int ATOUT = Api.ATOUT_1 & COLOR_MASK;
	
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
	private Card previous;
	private Card next;
	private boolean isOudler;
	private boolean isDominant;
	private boolean isInGame;

	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public Card(int code) {
		this.code = code;
		this.color = code & COLOR_MASK;
		this.value = code & VALUE_MASK;
		
		// Extract info
		this.isOudler = (color == ATOUT &&
			(value == 0 || value == 1 || value == 21));
		
		reset();
	}

	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	public void init() {
		previous = (value > 1) ? Utils.getCard(code - 1) : null;
		next = (value < ROI || color == ATOUT && value < 21) ? Utils.getCard(code + 1) : null;
	}
	
	public void reset() {
		isDominant = false;
		isInGame = true;
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
	
	public boolean isDominant() {
		return isDominant;
	}
	
	public boolean isInGame() {
		return isInGame;
	}
	
	public boolean isOudler() {
		return isOudler;
	}
	
	public Card previous(boolean inGame) {
		if (previous == null) {
			return null;
		} else if(!inGame) {
			return previous;
		} else {
			return (previous.isInGame()) ? previous : previous.previous(inGame);
		}
	}
	
	public Card next(boolean inGame) {
		if (next == null) {
			return null;
		} else if(!inGame) {
			return next;
		} else {
			return (next.isInGame()) ? next : next.next(inGame);
		}
	}
	
	public double getPoints() {
		if (color == ATOUT) {
			return (isOudler) ? 4.5 : 0.5;
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
		|| (otherCard.getCode() == Api.EXCUSE));
	}
	
	public void discard() {
		isInGame = false;
	}
	
	public void setDominant() {
		isDominant = true;
	}
	
	public String toString() {
		return getStringValue() + getStringColor();
	}
	
	public String getStringValue() {
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
}
