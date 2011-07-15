
package game;

import game.Game.Phase;
import gameConn.GameCommander;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

public class GameSession {
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private Game game;
	private int contract;
	private Player taker;
	private Player firstPlayer;
	private Player currentPlayer;
	private int nbOudlers;
	private double score;
	private Set<Card> dog;
	private int innerTurnNb;
	private int[] turnCards;
	private Player turnWinner;
	private Card turnBestCard;
	private int desiredColor;
	private boolean cardPlayed;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public GameSession(Game game, Player taker, int contract,
		Player currentPlayer, Collection<Card> dog) {
		this.game = game;
		this.taker = taker;
		this.contract = contract;
		this.firstPlayer = currentPlayer;
		this.currentPlayer = currentPlayer;
		this.dog = new TreeSet<Card>(new CardComparator());
		this.dog.addAll(dog);
		this.nbOudlers = 0;
		this.score = 0;
		this.turnCards = new int[4];
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	public void addToScore(Set<Card> cards) {
		for (Card card : cards) {
			score += card.getPoints();
			
			if (card.isOudler()) {
				nbOudlers++;
			}
		}
	}
	
	public boolean play() {
		// Garde
		if (contract == Game.ENCHERE_PRISE || contract == Game.ENCHERE_GARDE) {
			
			// Reveal dog
			game.setPhase(Phase.Idle);
			for (int i = 0; i < Game.NB_PLAYERS; i++) {
				GameCommander.dogInfo(currentPlayer.getAiId(), Utils
					.toArray(dog));
				currentPlayer = currentPlayer.nextPlayer();
			}
			
			// Frame
			GameCommander.setFrame();
			
			// Add cards to taker
			taker.addCards(dog);
			
			// Set cards aside
			game.setPhase(Phase.SettingCardsAside);
			GameCommander.setCardsAside(taker.getAiId());
			
			// Mauvais écart
			if (taker.getCards().size() != Game.CARDS_PER_PLAYER) {
				GameCommander.throwException("The taker (" + taker + ") "
					+ "did not set aside the good number of cards "
					+ "(He still has "
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
		for (int ctTurn = 0; ctTurn < Game.CARDS_PER_PLAYER; ctTurn++) {
			System.out.println("--------- Turn #" + ctTurn + " ---------");
			
			// Reset turn cards
			turnCards[0] = turnCards[1] = turnCards[2] = turnCards[3] = 0;
			
			// Play 4 cards
			game.setPhase(Phase.PlayingCard);
			for (innerTurnNb = 0; innerTurnNb < Game.NB_PLAYERS; innerTurnNb++) {
				cardPlayed = false;
				GameCommander.playCard(currentPlayer.getAiId(), firstPlayer
					.getAiId(), turnCards);
				
				// Frame
				GameCommander.setFrame();
				
				// Player didn't play a card
				if (!cardPlayed) {
					GameCommander.throwException("The current player ("
						+ currentPlayer
						+ ") did not play his card this turn.");
				}
				
				currentPlayer = currentPlayer.nextPlayer();
			}
			
			// Add points to score
			if (turnWinner == taker) {
				for (int code : turnCards) {
					score += Utils.getCard(code).getPoints();
				}
			}
			
			// Give turn info
			game.setPhase(Phase.Idle);
			for (int i = 0; i < Game.NB_PLAYERS; i++) {
				GameCommander.turnInfo(currentPlayer.getAiId(), firstPlayer
					.getAiId(),
					turnWinner.getAiId(), turnCards);
				currentPlayer = currentPlayer.nextPlayer();
			}
			
			// Set new first player
			firstPlayer = turnWinner;
			currentPlayer = firstPlayer;
		}
		
		// Frame
		GameCommander.setFrame();
		
		// Compute final score
		int goal = getGoal(nbOudlers);
		int finalScore = (int) score - goal;
		finalScore += (finalScore > 0) ? 25 : -25;
		finalScore *= getModifier(contract);
		
		// TODO: Manage petit au bout
		// TODO: Manage poignées
		// TODO: Manage Shelems
		
		System.out.println("Score: " + (int) score + " / " + getGoal(nbOudlers)
			+ " (x" + getModifier(contract) + ")");
		
		// Add scores to players
		taker.addScore(finalScore * 3);
		Player opponentPlayer = taker.nextPlayer();
		for (int i = 0; i < 3; i++) {
			opponentPlayer.addScore(-finalScore);
			opponentPlayer = opponentPlayer.nextPlayer();
		}
		
		return (finalScore > 0);
	}
	
	public boolean cardPlayed(Card card) {
		// Already played
		if (cardPlayed) {
			return false;
		}
		
		// First player
		if (innerTurnNb == 0 || turnBestCard.getCode() == Game.EXCUSE) {
			turnBestCard = card;
			turnWinner = currentPlayer;
			desiredColor = card.getColor();
		}
		// Following players
		else {
			// Determine if this move is legal
			if (card.getCode() != Game.EXCUSE) {
				int cardColor = card.getColor();
				
				// In color
				if (desiredColor != Card.ATOUT) {
					// Not the same color
					if (cardColor != desiredColor) {
						// Another color but had the same color
						if (currentPlayer.hasColor(desiredColor)) {
							return false;
						}
						// Another color but had atout
						else if (cardColor != Card.ATOUT
							&& currentPlayer.hasAtoutAbove(0)) {
							return false;
						}
						// A small atout but had better
						else if (cardColor == Card.ATOUT
							&& turnBestCard.getColor() == Card.ATOUT
							&& !card.isBetterThan(turnBestCard, desiredColor)
							&& currentPlayer.hasAtoutAbove(turnBestCard
								.getValue())) {
							return false;
						}
					}
				}
				// Atout
				else {
					// Not atout but had atouts
					if (cardColor != Card.ATOUT
						&& currentPlayer.hasAtoutAbove(0)) {
						return false;
					}
					// A small atout but had better
					else if (cardColor == Card.ATOUT
						&& !card.isBetterThan(turnBestCard, desiredColor)
						&& currentPlayer.hasAtoutAbove(turnBestCard.getValue())) {
						return false;
					}
				}
				
				// Determine if this card is better than the other
				if (card.isBetterThan(turnBestCard, desiredColor)) {
					turnBestCard = card;
					turnWinner = currentPlayer;
				}
			}
		}
		
		// Add card in turn history
		turnCards[innerTurnNb] = card.getCode();
		
		// Remove card from player's hand
		currentPlayer.removeCard(card);
		
		cardPlayed = true;
		return true;
	}
	
	// -------------------------------------------------------------------------
	// Private methods
	// -------------------------------------------------------------------------
	
	private int getGoal(int nbOudlers) {
		switch (nbOudlers) {
		case 0:
			return 56;
		case 1:
			return 51;
		case 2:
			return 41;
		case 3:
			return 36;
		default:
			return 56;
		}
	}
	
	private int getModifier(int contract) {
		switch (contract) {
		case Game.ENCHERE_PRISE:
			return 1;
		case Game.ENCHERE_GARDE:
			return 2;
		case Game.ENCHERE_GARDE_SANS:
			return 4;
		case Game.ENCHERE_GARDE_CONTRE:
			return 6;
		default:
			return 0;
		}
	}
}
