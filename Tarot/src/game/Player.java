
package game;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

public class Player {
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private short aiId;
	private String aiName;
	private String playerName;
	private int score;
	private Set<Card> cards;
	private Player nextPlayer;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public Player(short aiId, String aiName, String playerName) {
		this.aiId = aiId;
		this.aiName = aiName;
		this.playerName = playerName;
		this.score = 0;
		this.cards = null;
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	public void setNextPlayer(Player player) {
		nextPlayer = player;
	}
	
	public short getAiId() {
		return aiId;
	}
	
	public String getAiName() {
		return aiName;
	}
	
	public String getPlayerName() {
		return playerName;
	}
	
	public Set<Card> getCards() {
		return cards;
	}
	
	public Player nextPlayer() {
		return nextPlayer;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setCards(Collection<Card> newCards) {
		cards = new TreeSet<Card>(new CardComparator());
		addCards(newCards);
	}
	
	public void addCards(Collection<Card> newCards) {
		for (Card card : newCards) {
			cards.add(card);
		}
	}
	
	public void removeCard(Card card) {
		cards.remove(card);
	}
	
	public boolean hasCard(Card card) {
		return cards.contains(card);
	}

	public void addScore(int gameScore) {
		score += gameScore;
	}

	public boolean hasColor(int desiredColor) {
		for (Card card : cards) {
			if (card.getColor() == desiredColor) {
				return true;
			}
		}
		
		return false;
	}

	public boolean hasAtoutAbove(int value) {
		for (Card card : cards) {
			if (card.getColor() == Card.ATOUT && card.getValue() > value) {
				return true;
			}
		}
		
		return false;
	}
	
	public String toString() {
		return "AI #" + aiId + " (" + aiName + ", " + playerName + ")";
	}
}
