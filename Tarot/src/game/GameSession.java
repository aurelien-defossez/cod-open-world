
package game;

import java.util.List;
import game.Game.Phase;
import gameConn.GameCommander;

public class GameSession {
	private Game game;
	private int contract;
	private Player taker;
	private Player currentPlayer;
	private double score;
	private List<Card> dog;
	private int innerTurnNb;
	private int[] turnCards;
	private Player turnWinner;
	private Card turnBestCard;
	private int desiredColor;
	
	public GameSession(Game game, Player taker, int contract,
		Player currentPlayer, List<Card> dog) {
		this.game = game;
		this.taker = taker;
		this.contract = contract;
		this.currentPlayer = currentPlayer;
		this.dog = dog;
		this.score = 0;
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	public void addToScore(int[] cards) {
		for (int code : cards) {
			score += Utils.getCard(code).getPoints();
		}
	}
	
	public void play() {
		// Garde
		if (contract == Game.ENCHERE_PRISE
			|| contract == Game.ENCHERE_GARDE) {
			
			// Reveal dog
			game.setPhase(Phase.Idle);
			for (int i = 0; i < Game.NB_PLAYERS; i++) {
				GameCommander.dogInfo(currentPlayer.getAiId(), Utils.toArray(dog));
				currentPlayer = currentPlayer.nextPlayer();
			}
			
			// Add cards to taker
			taker.addCards(dog);
			
			// Set cards aside
			game.setPhase(Phase.SettingCardsAside);
			GameCommander.setCardsAside(taker.getAiId());
			
			// Mauvais écart
			if (taker.getCards().size() != Game.CARDS_PER_PLAYER) {
				GameCommander.throwException("The taker (" + taker + ") "
					+ "did not set aside the good number of cards " + "(He still has "
					+ taker.getCards().size() + " cards in hand instead of "
					+ Game.CARDS_PER_PLAYER + ").");
			}
		}
		// Garde sans
		else if (contract == Game.ENCHERE_GARDE_SANS) {
			// Give dog to attack
			for (Card card : dog) {
				score += card.getPoints();
			}
		}
		// Garde contre
		else {
			// Do nothing
		}
		
		// The game finally begins
		game.setPhase(Phase.PlayingCard);
		for (int ctTurn = 0; ctTurn < Game.CARDS_PER_PLAYER; ctTurn++) {
			turnCards = new int[] { 0, 0, 0, 0 };
			
			// Play 4 cards
			for (innerTurnNb = 0; innerTurnNb < Game.NB_PLAYERS; innerTurnNb++) {
				GameCommander.playCard(currentPlayer.getAiId(), turnCards);
				
				// Player didn't play a card
				if (turnCards[innerTurnNb] == 0) {
					GameCommander.throwException("The current player (" + currentPlayer
						+ ") did not play his card this turn.");
				}
				
				currentPlayer = currentPlayer.nextPlayer();
			}
		}
	}
	
	public boolean cardPlayed(Card card) {
		// Add card in turn history
		turnCards[innerTurnNb] = card.getCode();
		
		// Remove card from player's hand
		currentPlayer.removeCard(card);
		
		// First player
		if (innerTurnNb == 0 || turnBestCard.getCode() == Game.EXCUSE) {
			turnBestCard = card;
			turnWinner = currentPlayer;
			desiredColor = card.getColor();
		}
		// Following players
		else {
			// Determine if this move is legal
			// TODO
			
			// Determine if this card is better than the other
			if (card.isBetterThan(turnBestCard, desiredColor)) {
				turnBestCard = card;
				turnWinner = currentPlayer;
			}
		}
		
		return true;
	}
}
