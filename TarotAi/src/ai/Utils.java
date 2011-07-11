package ai;

import game.Api;
import java.util.Set;
import java.util.TreeSet;

public class Utils {
	public static String printCards(int[] cards) {
		TreeSet<Integer> cardSet = new TreeSet<Integer>();
		
		for (int card : cards) {
			cardSet.add(card);
		}
		
		return printCards(cardSet);
	}
	
	public static String printCards(Set<Integer> cards) {
		StringBuffer sb = new StringBuffer(" ");

		for (int card : cards) {
			sb.append(Api.decode(card) + " ");
		}
		
		return sb.toString();
	}
}
