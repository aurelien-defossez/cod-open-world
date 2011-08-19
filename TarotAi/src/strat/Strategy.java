
package strat;

import java.util.List;
import ai.Card;

public interface Strategy {
	public abstract void checkRequirements();
	public abstract boolean isActivated();
	public abstract Card execute(List<Card> playedCards);
}
