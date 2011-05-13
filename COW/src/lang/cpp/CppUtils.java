
package lang.cpp;

public class CppUtils {
	public static void setJNALibraryPath(String path) {
		System.setProperty("jna.library.path", path);
	}
	
	public static void appendJNALibraryPath(String path) {
		System.setProperty("jna.library.path",
			System.getProperty("jna.library.path") + ":" + path);
	}
}
