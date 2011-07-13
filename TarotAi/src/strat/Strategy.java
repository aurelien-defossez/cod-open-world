
package strat;

import java.util.List;
import ai.Card;

public interface Strategy {
	public boolean isActivated();
	public abstract Card execute(List<Card> playedCards);
}
