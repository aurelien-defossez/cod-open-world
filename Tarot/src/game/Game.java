/**
 * Game - This is the game implementation of "Unit Testing".
 */

package game;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import gameConn.TarotEngine;
import gameConn.GameCommander;

public class Game implements TarotEngine {
	// -------------------------------------------------------------------------
	// Enumeration
	// -------------------------------------------------------------------------
	
	public enum Phase {
		Idle,
		Bidding,
		SettingCardsAside,
		PlayingCard
	};
	
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------
	
	public final static int NB_PLAYERS = 4;
	public final static int NB_CARDS = 78;
	public final static int DOG_SIZE = 6;
	public final static int CARDS_PER_PLAYER = (NB_CARDS - DOG_SIZE) / NB_PLAYERS;
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
	private Phase phase;
	private GameSession currentSession;
	private Random random;
	private int currentContract;
	private int maxHands;
	private int ctHands;
	private Player firstPlayer;
	private Player currentPlayer;
	private Player currentBidder;
	private Map<Short, Player> players;
	private List<Card> deck;
	private List<Card> dog;
	private boolean stopGame;
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public Game() {
		this.players = new HashMap<Short, Player>(NB_PLAYERS, 1);
		this.ctHands = 0;
		this.stopGame = false;
		
		// Create cards
		Utils.createCards();
		
		// Create deck
		deck = Utils.createDeck();
	}
	
	// -------------------------------------------------------------------------
	// Public methods
	// -------------------------------------------------------------------------
	
	@Override
	public void addAi(short aiId, String aiName, String playerName) {
		players.put(aiId, new Player(aiId, aiName, playerName));
	}
	
	@Override
	public void init(String[] gameParameters) {
		// Wrong number of parameters
		if (gameParameters.length < 1) {
			GameCommander.throwException("usage: -z <nbHands> [<randomSeed>]");
		} else {
			try {
				// Retrieve number of hands
				maxHands = Integer.parseInt(gameParameters[0]);
				
				// Create random object
				if (gameParameters.length >= 2) {
					random = new Random(Long.parseLong(gameParameters[1]));
				} else {
					random = new Random();
				}
				
				// Illegal number of hands
				if (maxHands <= 0) {
					GameCommander
						.throwException("The number of hands should be positive.");
				}
				
				// Wrong number of players
				if (players.size() != NB_PLAYERS) {
					GameCommander
						.throwException("4 players are needed to play Tarot, but "
							+
							players.size() + " are present.");
				}
				
				// Pick first player randomly
				int firstPlayerId = random.nextInt(NB_PLAYERS);
				
				// Link players
				int i = 0;
				Player p1 = null;
				Player previousPlayer = null;
				for (Player player : players.values()) {
					// Save first player
					if (p1 == null) {
						p1 = player;
					}
					// Attach next player
					else {
						previousPlayer.setNextPlayer(player);
					}
					
					// First player
					if (i == firstPlayerId) {
						firstPlayer = player;
					}
					
					previousPlayer = player;
					i++;
				}
				
				// Attach first player to last player
				previousPlayer.setNextPlayer(p1);
				
			} catch (NumberFormatException e) {
				GameCommander.throwException("Arguments must be integers");
			}
		}
	}
	
	@Override
	public void aiTimedOut(short aiId) {
		GameCommander.stopAi(aiId);
	}
	
	@Override
	public void play() {
		int i;
		
		// Initialize players
		setPhase(Phase.Idle);
		for (Player player : players.values()) {
			GameCommander.init(player.getAiId(), player.getAiId());
		}
		currentPlayer = firstPlayer;
		
		// While there are hands to play
		while (ctHands < maxHands && !stopGame) {
			System.out
				.println("_________________________________________________________________");
			System.out.println();
			
			// Shuffle deck
			Collections.shuffle(deck, random);
			
			// Reset game
			currentPlayer = currentPlayer.nextPlayer();
			currentContract = 0;
			
			// Distribute cards
			i = 0;
			for (Player player : players.values()) {
				// TODO: Manage "Petit sec"
				player.setCards(deck.subList(
					CARDS_PER_PLAYER * i, CARDS_PER_PLAYER * (i + 1)));
				i++;
			}
			
			// Create dog
			dog = deck.subList(CARDS_PER_PLAYER * NB_PLAYERS, NB_CARDS);
			
			// Send hands to players
			setPhase(Phase.Idle);
			for (i = 0; i < NB_PLAYERS; i++) {
				GameCommander.newHand(currentPlayer.getAiId(), i + 1,
					Utils.toArray(currentPlayer.getCards()));
				currentPlayer = currentPlayer.nextPlayer();
			}
			
			// Bidding phase
			setPhase(Phase.Bidding);
			for (i = 0; i < NB_PLAYERS; i++) {
				GameCommander.bid(currentPlayer.getAiId());
				currentPlayer = currentPlayer.nextPlayer();
			}
			
			// Frame
			GameCommander.setFrame();
			
			// Play hand
			if (currentContract >= ENCHERE_PRISE) {
				ctHands++;
				
				// Create game session
				currentSession =
					new GameSession(this, currentBidder, currentContract,
						currentPlayer, dog);
				
				// Play game session
				boolean won = currentSession.play();
				
				// Create scores array
				int[] scores = new int[Game.NB_PLAYERS];
				for (Player player : players.values()) {
					scores[player.getAiId()] = player.getScore();
					GameCommander.setScore(player.getAiId(), player.getScore());
				}
				
				// Give hand info
				setPhase(Phase.Idle);
				for (i = 0; i < Game.NB_PLAYERS; i++) {
					GameCommander.handInfo(currentPlayer.getAiId(), won, scores);
					currentPlayer = currentPlayer.nextPlayer();
				}
			}
		}
	}
	
	@Override
	public void stop() {
		stopGame = true;
	}
	
	@Override
	public int bid(short aiId, int contract) {
		// Illegal phase
		if (phase != Phase.Bidding) {
			return ILLEGAL_PHASE;
		}
		
		// Illegal contract
		if (contract < ENCHERE_PRISE || contract > ENCHERE_GARDE_CONTRE ||
			contract <= currentContract) {
			return ILLEGAL_CONTRACT;
		}
		
		// Better contract
		currentContract = contract;
		currentBidder = currentPlayer;
		
		// Inform other players
		Player otherPlayer = currentPlayer.nextPlayer();
		for (int i = 0; i < NB_PLAYERS - 1; i++) {
			GameCommander.bidInfo(otherPlayer.getAiId(), currentBidder
				.getAiId(), contract);
			otherPlayer = otherPlayer.nextPlayer();
		}
		
		return OK;
	}
	
	@Override
	public int setCardsAside(short aiId, int[] cards) {
		// Illegal phase
		if (phase != Phase.SettingCardsAside) {
			return ILLEGAL_PHASE;
		}
		
		Set<Card> cardsAside = new TreeSet<Card>(new CardComparator());
		
		for (int code : cards) {
			Card card = Utils.getCard(code);
			cardsAside.add(card);
			
			// Unknown card
			if (card == null) {
				return UNKNOWN_CARD_CODE;
			}
			
			// Card not in hand
			if (!currentBidder.hasCard(card)) {
				return CARD_NOT_IN_HAND;
			}
			
			// Illegal card (Oudler or King)
			if (card.isOudler() || card.getColor() != Card.ATOUT
				&& card.getValue() == Card.ROI) {
				return ILLEGAL_CARD_ASIDE;
			}
		}
		
		for (Card card : cardsAside) {
			// Announce atouts to other players
			if (card.getColor() == Card.ATOUT) {
				setPhase(Phase.Idle);
				Player otherPlayer = currentBidder.nextPlayer();
				for (int i = 0; i < NB_PLAYERS - 1; i++) {
					GameCommander.cardsAsideInfo(otherPlayer.getAiId(), card
						.getCode());
					otherPlayer = otherPlayer.nextPlayer();
				}
			}
			
			// Remove card from hand
			currentBidder.removeCard(card);
		}
		
		// Add cards to player's score
		for (Card card : cardsAside) {
			currentSession.addToScore(card);
		}
		
		return OK;
	}
	
	@Override
	public int playCard(short aiId, int cardCode) {
		// Illegal phase
		if (phase != Phase.PlayingCard) {
			return ILLEGAL_PHASE;
		}
		
		Card card = Utils.getCard(cardCode);
		
		// Unknown card
		if (card == null) {
			return UNKNOWN_CARD_CODE;
		}
		
		// Card not in hand
		if (!currentSession.getCurrentPlayer().hasCard(card)) {
			return CARD_NOT_IN_HAND;
		}
		
		// Play card (can result to an illegal move)
		if (!currentSession.cardPlayed(card)) {
			return ILLEGAL_CARD_PLAYED;
		}
		
		return OK;
	}
	
	@Override
	public int makeAnnouncement(short aiId, int announcement) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void setPhase(Phase phaseState) {
		phase = phaseState;
	}
}
