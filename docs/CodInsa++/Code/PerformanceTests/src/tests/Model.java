package tests;

public abstract class Model {
	protected AI ai;
	protected Game game;

	abstract public void execute();
	abstract public void stop();
}
