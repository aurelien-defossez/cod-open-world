
package lang.cpp;

import lang.cpp.callback.MakeCallCallback;
import com.sun.jna.Library;

public interface GameLibraryInterface extends Library {
	public void registerCallbacks(MakeCallCallback makeCallCallback);
	
	public void init(int nbParameters, String[] parameters);
	
	public void addAi(short aiId, String aiName, String playerName);
	
	public void play();
	
	public void endGame();
	
	public void aiTimedOut(short aiId);
	
	public VariantStruct.ByValue performGameFunction(int functionId,
		int nbParameters, VariantStruct parameters[]);
}
