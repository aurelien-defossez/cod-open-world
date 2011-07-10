
package game;

import java.util.Iterator;
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

	public int[] getCardsValues() {
		int[] cardsValues = new int[cards.size()];
		
		Iterator<Card> it = cards.iterator();
		int i = 0;
		while (it.hasNext()) {
			cardsValues[i] = it.next().getCode();
			i++;
		}
		
		return cardsValues;
	}
	
	public Player nextPlayer() {
		return nextPlayer;
	}
	
	public void setCards(List<Card> cards) {
		this.cards = cards;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("[AI #" + aiId + " (" + aiName + ", " + playerName
			+ "); score=" + score + " { ");
		
		Iterator<Card> it = cards.iterator();
		while (it.hasNext()) {
			sb.append(it.next() + " ");
		}
		
		sb.append("}]");
		return sb.toString();
	}
}
