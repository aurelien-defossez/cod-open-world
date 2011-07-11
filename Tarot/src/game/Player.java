
package game;

import java.util.ArrayList;
import java.util.List;

public class Player {
	private short aiId;
	private String aiName;
	private String playerName;
	private int score;
	private List<Card> cards;
	private Player nextPlayer;
	
	public Player(short aiId, String aiName, String playerName) {
		this.aiId = aiId;
		this.aiName = aiName;
		this.playerName = playerName;
		this.score = 0;
		this.cards = null;
	}
	
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
	
	public List<Card> getCards() {
		return cards;
	}
	
	public Player nextPlayer() {
		return nextPlayer;
	}
	
	public void setCards(List<Card> newCards) {
		cards = new ArrayList<Card>();
		addCards(newCards);
	}
	
	public void addCards(List<Card> newCards) {
		for (Card card : newCards) {
			cards.add(card);
		}
	}
	
	public boolean hasCard(Card card) {
		return cards.contains(card);
	}
	
	public void removeCards(int[] oldCards) {
		for (int code : oldCards) {
			removeCard(Utils.getCard(code));
		}
	}
	
	public void removeCard(Card card) {
		cards.remove(card);
	}
	
	public String toString() {
		return "AI #" + aiId + " (" + aiName + ", " + playerName + ")";
	}
}
