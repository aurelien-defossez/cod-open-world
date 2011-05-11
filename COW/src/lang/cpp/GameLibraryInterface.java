
package lang.cpp;

import com.sun.jna.Library;

public interface GameLibraryInterface extends Library {
	public void registerCallbacks(PrepareCallCallback prepareCallcallback,
		AddParameterCallback setParameterCallback,
		MakeCallCallback makeCallCallback);
	
	public void init(int nbParameters, String[] parameters);
	
	public void addAi(short aiId, String aiName, String playerName);
	
	public void play();
	
	public void endGame();
	
	public void disqualifyAi(String aiName, String reason);
	
	public VariantStruct.ByValue performGameFunction(int functionId,
		int nbParameters, VariantStruct parameters[]);
}
