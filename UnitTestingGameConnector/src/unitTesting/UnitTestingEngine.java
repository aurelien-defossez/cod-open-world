package unitTesting;

public interface UnitTestingEngine {
	public void init();
	public void addAi(short aiId, String aiName, String playerName);
	public void play();
	public void disqualifyAi(short aiId, String reason);
	public void stop();

	public void testNoParameters();
	public int testNoParametersReturnsInt();
}
