/**
 * Language - This enumerates every supported language.
 */

package com;

public abstract class Lang {
	public enum Language {
		Cpp, Java, Python
	}
	
	/**
	 * Returns the language execution speed handicap, meaning the average time
	 * ratio it takes to execute a program compared to a GNU C gcc program.
	 * 
	 * @see <a
	 *      href="http://shootout.alioth.debian.org/u32/which-programming-languages-are-fastest.php">
	 *      Source: Programming languages speed comparison.</a>
	 * @param language the language.
	 * @return the speed handicap, compared to GNU C gcc.
	 */
	public static double getLanguageSpeedHandicap(Language language) {
		switch (language) {
		case Cpp:
			return 1.0;
			
		case Java:
			return 1.73;
			
		case Python:
			return 40.86;
			
		default:
			return 1.0;
		}
	}
}
