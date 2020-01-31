package de.lars.updater.utils;

public class VersionTagUtil {
	
	/**
	 * Convert version tag to version number
	 * @param tag Version tag
	 * @return Substring of tag that contains only numbers
	 * @throws Exception 
	 */
	public static String getVersionNumber(String tag) throws Exception {
		int beginIndex = -1, endIndex = tag.length() - 1;
		for(int i = 0; i < tag.length(); i++) {
			if(Character.isDigit(tag.charAt(i)) && beginIndex == -1) {
				beginIndex = i;
			} else if(beginIndex != -1) {
				endIndex = i;
			}
		}
		if(beginIndex == -1) {	// invalid version tag (contains no numbers)
			throw new Exception("Invalid version tag. Tag must contain at least one number.");
		}
		return tag.substring(beginIndex, endIndex + 1);
	}
	
	
	/**
	 * Compares current and new version number
	 * <!> Both strings MUST ONLY CONTAIN DIGITS AND '.'
	 * @param current Current version number
	 * @param update New version number
	 * @return True, if 'update' is larger than 'current'
	 */
	public static boolean compareVersionNumber(String current, String update) {
		//convert current to double (e.g. 0.2.0.1 -> 0.201)
		double curD = versionNumberToDouble(current);
		//convert new to double (e.g. 0.2.0.4 -> 0.204)
		double newD = versionNumberToDouble(update);
		return curD < newD;
	}
	
	/**
	 * Convert version number to double
	 * @param versionNumber Version number as string, MUST ONLY CONTAIN DIGITS AND '.'
	 * @return Version number as double (e.g. 1.0.3 -> 1.03)
	 */
	public static double versionNumberToDouble(String versionNumber) {
		String curIn = versionNumber;
		String curOut = "";
		double curD = 0D;
		
		if(curIn.contains(".")) {
			curOut = curIn.substring(0, curIn.indexOf(".") + 1);
			curIn = curIn.substring(curIn.indexOf(".") + 1);
			
			while(curIn.contains(".")) {
				curOut += curIn.substring(0, curIn.indexOf("."));
				curIn = curIn.substring(curIn.indexOf(".") + 1);
			}
		}
		
		for(int i = 0; i < curIn.length(); i++) {
			if(Character.isDigit(curIn.charAt(i))) {
				curOut += curIn.charAt(i);
			}
		}
		curD = Double.parseDouble(curOut);
		
		return curD;
	}

}
