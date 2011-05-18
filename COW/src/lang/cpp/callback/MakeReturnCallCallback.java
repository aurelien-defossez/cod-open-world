package lang.cpp.callback;

import com.sun.jna.win32.StdCallLibrary.StdCallCallback;

public interface MakeReturnCallCallback extends StdCallCallback {
	public int callback();
}
