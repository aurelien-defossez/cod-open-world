package direct;

import tests.Model;

public class DirectCallModel extends Model {
	public DirectCallModel() {
		game = new DCGame();
		ai = new DCAI(game);
	}

	@Override
	public void execute() {
		ai.execute();
	}

	@Override
	public void stop() {
		//Do nothing
	}
}
