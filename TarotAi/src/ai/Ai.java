/**
 * AI - This AI communicates with the game to do some unit testing on the
 * platform.
 */

package ai;

import java.util.ArrayList;
import java.util.List;
import game.Api;
import game.TarotAi;

public class Ai implements TarotAi {
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private int id;
	private Game game;
	private Hand hand;
	private int position;
	private int currentContract;
	private int takerId;
	private boolean isTaker;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public Ai() {
		Utils.init();
		game = null;
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	public void init(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	@Override
	public void newHand(int position, int[] cards) {
		this.hand = new Hand(cards);
		this.currentContract = 0;
		this.position = position;
		
		print("My hand is " + hand + ", pos #" + position + ".");
	}
	
	@Override
	public void bid() {
		hand.computeScores(position, currentContract);
		
		int score = hand.getScore();
		int scoreSans = hand.getScoreSans();
		
		print("Scores = " + score + " / " + scoreSans);
		
		// Garde contre
		if (scoreSans >= Params.THRESHOLD_GARDE_CONTRE
			&& currentContract < Api.ENCHERE_GARDE_CONTRE) {
			
			print("I bid " + Api.decode(Api.ENCHERE_GARDE_CONTRE)
				+ " (" + Api.decode(Api.bid(Api.ENCHERE_GARDE_CONTRE)) + ")");
			
			isTaker = true;
		}
		// Garde sans
		else if (scoreSans >= Params.THRESHOLD_GARDE_SANS
			&& currentContract < Api.ENCHERE_GARDE_SANS) {
			
			print("I bid " + Api.decode(Api.ENCHERE_GARDE_SANS)
				+ " (" + Api.decode(Api.bid(Api.ENCHERE_GARDE_SANS)) + ")");
			
			isTaker = true;
		}
		// Garde
		else if (score >= Params.THRESHOLD_GARDE
			&& currentContract < Api.ENCHERE_GARDE) {
			
			print("I bid " + Api.decode(Api.ENCHERE_GARDE)
				+ " (" + Api.decode(Api.bid(Api.ENCHERE_GARDE)) + ")");
			
			isTaker = true;
		}
	}
	
	@Override
	public void bidInfo(int bidder, int contract) {
		currentContract = contract;
		takerId = bidder;
		isTaker = false;
	}
	
	@Override
	public void dogInfo(int[] cards) {
		// It's my dog
		if (isTaker) {
			print("My dog is {" + Utils.printCards(cards) + "}");
			
			hand.addCards(cards, true);
		}
	}
	
	@Override
	public void setCardsAside() {
		print("My hand is " + hand);
		
		int[] cardsAside = hand.setCardsAside();
		
		print("Setting aside {" + Utils.printCards(cardsAside) + "}"
			+ " (" + Api.decode(Api.setCardsAside(cardsAside)) + ")");
		print("My hand is " + hand);
	}
	
	@Override
	public void cardsAsideInfo(int card) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void announcementInfo(int announcer, int announcement) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void playCard(int firstPlayer, int[] cards) {
		List<Card> gameCards = new ArrayList<Card>(3);

		for (int i = 0; i < 3; i++) {
			if (cards[i] != 0) {
				gameCards.add(Utils.getCard(cards[i]));
			}
		}
		
		position = gameCards.size() + 1;
		
		if (game == null) {
			if (isTaker) {
				game = new AttackGame(this, hand);
			} else {
				game = new DefenseGame(this, hand, takerId);
			}
		}

		game.cardsPlayed(firstPlayer, gameCards, 0);
		game.playCard(gameCards);
	}
	
	@Override
	public void turnInfo(int firstPlayer, int turnWinner, int[] cards) {
		List<Card> gameCards = new ArrayList<Card>(4);
		
		for (int i = 0; i < 4; i++) {
			gameCards.add(Utils.getCard(cards[i]));
		}
		
		game.cardsPlayed(firstPlayer, gameCards, position + 1);
	}
	
	@Override
	public void handInfo(boolean won, int[] scores) {
		// TODO Auto-generated method stub
		game = null;
	}
	
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}
	
	private void print(String message) {
		System.out.println(" [" + id + "] " + message);
	}
}
