/**
 * Language - This enumerates every supported language.
 */

package com;

public abstract class Lang {
	// -------------------------------------------------------------------------
	// Enumeration
	// -------------------------------------------------------------------------
	
	/**
	 * The language enum, with every supported language.
	 */
	public enum Language {
		Cpp, Java, Python
	}
	
	// -------------------------------------------------------------------------
	// Class methods
	// -------------------------------------------------------------------------
	
	/**
	 * Returns the language enum value corresponding to the given string.
	 * 
	 * @param language the language, as a string.
	 * @return the language, as an enum value.
	 */
	public static Language getLanguage(String language) {
		language = language.toLowerCase();
		
		if (language.equals("cpp") || language.equals("c++")) {
			return Language.Cpp;
		} else if (language.equals("java")) {
			return Language.Java;
		} else if (language.equals("python")) {
			return Language.Python;
		} else {
			return null;
		}
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
