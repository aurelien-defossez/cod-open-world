package direct;

import tests.Game;

public class DCGame implements Game {
	@Override
	public int callAPI(int parameter) {
		return parameter * 2;
	}

	@Override
	public void delete() {
		//Do nothing
	}
}
