/**
 * AI - This AI communicates with the game to do some unit testing on the
 * platform.
 */

package ai;

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
		
		System.out.println("[" + id + "] My hand is " + hand + ", pos #" + position + ".");
	}
	
	@Override
	public void bid() {
		hand.computeScores(position, currentContract);
		
		int score = hand.getScore();
		int scoreSans = hand.getScoreSans();
		
		System.out
			.println("[" + id + "] Scores = " + score + " / " + scoreSans);
		
		// Garde contre
		if (scoreSans >= Params.THRESHOLD_GARDE_CONTRE
			&& currentContract < Api.ENCHERE_GARDE_CONTRE) {
			
			System.out.println("[" + id + "] I bid " + Api.decode(Api.ENCHERE_GARDE_CONTRE)
				+ " (" + Api.decode(Api.bid(Api.ENCHERE_GARDE_CONTRE)) + ")");
			
			isTaker = true;
		}
		// Garde sans
		else if (scoreSans >= Params.THRESHOLD_GARDE_SANS
			&& currentContract < Api.ENCHERE_GARDE_SANS) {
			
			System.out.println("[" + id + "] I bid " + Api.decode(Api.ENCHERE_GARDE_SANS)
				+ " (" + Api.decode(Api.bid(Api.ENCHERE_GARDE_SANS)) + ")");
			
			isTaker = true;
		}
		// Garde
		else if (score >= Params.THRESHOLD_GARDE
			&& currentContract < Api.ENCHERE_GARDE) {
			
			System.out.println("[" + id + "] I bid " + Api.decode(Api.ENCHERE_GARDE)
				+ " (" + Api.decode(Api.bid(Api.ENCHERE_GARDE)) + ")");
			
			isTaker = true;
		}
	}
	
	@Override
	public void bidInfo(int bidder, int contract) {
		currentContract = contract;
		isTaker = false;
	}
	
	@Override
	public void dogInfo(int[] cards) {
		// It's my dog
		if (isTaker) {
			System.out.println("[" + id + "] My dog is {" + Utils.printCards(cards) + "}");
			
			hand.addCards(cards);
		}
	}
	
	@Override
	public void setCardsAside() {
		System.out.println("[" + id + "] My hand is " + hand);
		
		int[] cardsAside = hand.setCardsAside();
		
		System.out.println("[" + id + "] Setting aside {" + Utils.printCards(cardsAside) + "}"
			+ " (" + Api.decode(Api.setCardsAside(cardsAside)) + ")");
		System.out.println("[" + id + "] My hand is " + hand);
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
		if (game == null) {
			game = new Game(this, hand, isTaker);
		}
		
		game.playCard(cards);
	}
	
	@Override
	public void turnInfo(int firstPlayer, int turnWinner, int[] cards) {
		game.cardsPlayed(firstPlayer, cards);
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
}
