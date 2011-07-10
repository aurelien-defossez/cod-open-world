/**
 * Game - This is the game implementation of "Unit Testing".
 */

package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import gameConn.TarotEngine;
import gameConn.GameCommander;

public class Game implements TarotEngine {
	// -------------------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------------------
	
	public final static int NB_PLAYERS = 4;
	public final static int NB_CARDS = 78;
	public final static int DOG_SIZE = 6;
	public final static int CARDS_PER_PLAYER = (NB_CARDS - DOG_SIZE)
		/ NB_PLAYERS;
	
	// -------------------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------------------
	
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
	
	// -------------------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------------------
	
	public Game() {
		players = new HashMap<Short, Player>(NB_PLAYERS, 1);
		ctHands = 0;
		deck = new ArrayList<Card>(NB_CARDS);
		dog = new ArrayList<Card>(DOG_SIZE);
		
		// Create deck
		deck.add(new Card(TarotEngine.COEUR_1));
		deck.add(new Card(TarotEngine.COEUR_2));
		deck.add(new Card(TarotEngine.COEUR_3));
		deck.add(new Card(TarotEngine.COEUR_4));
		deck.add(new Card(TarotEngine.COEUR_5));
		deck.add(new Card(TarotEngine.COEUR_6));
		deck.add(new Card(TarotEngine.COEUR_7));
		deck.add(new Card(TarotEngine.COEUR_8));
		deck.add(new Card(TarotEngine.COEUR_9));
		deck.add(new Card(TarotEngine.COEUR_10));
		deck.add(new Card(TarotEngine.COEUR_VALET));
		deck.add(new Card(TarotEngine.COEUR_CAVALIER));
		deck.add(new Card(TarotEngine.COEUR_DAME));
		deck.add(new Card(TarotEngine.COEUR_ROI));
		deck.add(new Card(TarotEngine.CARREAU_1));
		deck.add(new Card(TarotEngine.CARREAU_2));
		deck.add(new Card(TarotEngine.CARREAU_3));
		deck.add(new Card(TarotEngine.CARREAU_4));
		deck.add(new Card(TarotEngine.CARREAU_5));
		deck.add(new Card(TarotEngine.CARREAU_6));
		deck.add(new Card(TarotEngine.CARREAU_7));
		deck.add(new Card(TarotEngine.CARREAU_8));
		deck.add(new Card(TarotEngine.CARREAU_9));
		deck.add(new Card(TarotEngine.CARREAU_10));
		deck.add(new Card(TarotEngine.CARREAU_VALET));
		deck.add(new Card(TarotEngine.CARREAU_CAVALIER));
		deck.add(new Card(TarotEngine.CARREAU_DAME));
		deck.add(new Card(TarotEngine.CARREAU_ROI));
		deck.add(new Card(TarotEngine.PIQUE_1));
		deck.add(new Card(TarotEngine.PIQUE_2));
		deck.add(new Card(TarotEngine.PIQUE_3));
		deck.add(new Card(TarotEngine.PIQUE_4));
		deck.add(new Card(TarotEngine.PIQUE_5));
		deck.add(new Card(TarotEngine.PIQUE_6));
		deck.add(new Card(TarotEngine.PIQUE_7));
		deck.add(new Card(TarotEngine.PIQUE_8));
		deck.add(new Card(TarotEngine.PIQUE_9));
		deck.add(new Card(TarotEngine.PIQUE_10));
		deck.add(new Card(TarotEngine.PIQUE_VALET));
		deck.add(new Card(TarotEngine.PIQUE_CAVALIER));
		deck.add(new Card(TarotEngine.PIQUE_DAME));
		deck.add(new Card(TarotEngine.PIQUE_ROI));
		deck.add(new Card(TarotEngine.TREFLE_1));
		deck.add(new Card(TarotEngine.TREFLE_2));
		deck.add(new Card(TarotEngine.TREFLE_3));
		deck.add(new Card(TarotEngine.TREFLE_4));
		deck.add(new Card(TarotEngine.TREFLE_5));
		deck.add(new Card(TarotEngine.TREFLE_6));
		deck.add(new Card(TarotEngine.TREFLE_7));
		deck.add(new Card(TarotEngine.TREFLE_8));
		deck.add(new Card(TarotEngine.TREFLE_9));
		deck.add(new Card(TarotEngine.TREFLE_10));
		deck.add(new Card(TarotEngine.TREFLE_VALET));
		deck.add(new Card(TarotEngine.TREFLE_CAVALIER));
		deck.add(new Card(TarotEngine.TREFLE_DAME));
		deck.add(new Card(TarotEngine.TREFLE_ROI));
		deck.add(new Card(TarotEngine.EXCUSE));
		deck.add(new Card(TarotEngine.ATOUT_1));
		deck.add(new Card(TarotEngine.ATOUT_2));
		deck.add(new Card(TarotEngine.ATOUT_3));
		deck.add(new Card(TarotEngine.ATOUT_4));
		deck.add(new Card(TarotEngine.ATOUT_5));
		deck.add(new Card(TarotEngine.ATOUT_6));
		deck.add(new Card(TarotEngine.ATOUT_7));
		deck.add(new Card(TarotEngine.ATOUT_8));
		deck.add(new Card(TarotEngine.ATOUT_9));
		deck.add(new Card(TarotEngine.ATOUT_10));
		deck.add(new Card(TarotEngine.ATOUT_11));
		deck.add(new Card(TarotEngine.ATOUT_12));
		deck.add(new Card(TarotEngine.ATOUT_13));
		deck.add(new Card(TarotEngine.ATOUT_14));
		deck.add(new Card(TarotEngine.ATOUT_15));
		deck.add(new Card(TarotEngine.ATOUT_16));
		deck.add(new Card(TarotEngine.ATOUT_17));
		deck.add(new Card(TarotEngine.ATOUT_18));
		deck.add(new Card(TarotEngine.ATOUT_19));
		deck.add(new Card(TarotEngine.ATOUT_20));
		deck.add(new Card(TarotEngine.ATOUT_21));
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
			GameCommander.throwException(
				"usage: -z <nbHands> [<randomSeed>]");
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
					GameCommander.throwException(
						"The number of hands should be positive.");
				}
				
				// Wrong number of players
				if (players.size() != NB_PLAYERS) {
					GameCommander.throwException(
						"4 players are needed to play Tarot, but " +
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
	public void disqualifyAi(short aiId, String reason) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void play() {
		int i;
		
		// Initialize players
		for (Player player : players.values()) {
			GameCommander.init(player.getAiId(), player.getAiId());
		}
		
		// While there are hands to play
		while (ctHands < maxHands) {
			// Shuffle deck
			Collections.shuffle(deck, random);
			
			// Set next player
			currentPlayer = firstPlayer.nextPlayer();
			currentContract = 0;
			
			// Distribute cards
			i = 0;
			for (Player player : players.values()) {
				player.setCards(deck.subList(
					CARDS_PER_PLAYER * i, CARDS_PER_PLAYER * (i + 1)));
				i++;
			}
			
			// Create dog
			dog = deck.subList(CARDS_PER_PLAYER * NB_PLAYERS, NB_CARDS);
			
			// Print game
			System.out.println(getNewGameState());
			
			// Send hands to players
			for (i = 0; i < NB_PLAYERS; i++) {
				GameCommander.newHand(currentPlayer.getAiId(), i + 1,
					currentPlayer.getCardsValues());
				currentPlayer = currentPlayer.nextPlayer();
			}
			
			// Bidding phase
			for (i = 0; i < NB_PLAYERS; i++) {
				GameCommander.bid(currentPlayer.getAiId());
				currentPlayer = currentPlayer.nextPlayer();
			}
			
			//
			
			// TEMP
			ctHands++;
		}
	}
	
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int bid(short aiId, int contract) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int setCardsAside(short aiId, int[] cards) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int playCard(short aiId, int card) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	// -------------------------------------------------------------------------
	// Private methods
	// -------------------------------------------------------------------------
	
	private String getNewGameState() {
		StringBuffer sb = new StringBuffer();
		
		// Players' games
		for (Player player : players.values()) {
			sb.append(player + "\n");
		}
		
		// Dog
		sb.append("[Dog: { " + dog.get(0) + " " + dog.get(1) + " " + dog.get(2)
			+ " " + dog.get(3) + " " + dog.get(4) + " " + dog.get(5) + " }]\n");
		
		// First player
		sb.append("First player is " + firstPlayer);
		
		return sb.toString();
	}
}
