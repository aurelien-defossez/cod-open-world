package lang.cpp;

import com.sun.jna.win32.StdCallLibrary.StdCallCallback;

public interface MakeReturnCallCallback extends StdCallCallback {
	public int callback();
}
