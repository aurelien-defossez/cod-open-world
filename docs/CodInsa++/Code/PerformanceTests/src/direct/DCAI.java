package direct;

import tests.AI;
import tests.Game;

public class DCAI implements AI {
	private int nb;
	private Game game;
	
	public DCAI(Game game) {
		this.nb = 1;
		this.game = game;
	}

	@Override
	public void execute() {
		nb = game.callAPI(nb);
	}

	@Override
	public void delete() {
		//Do nothing
	}
}
