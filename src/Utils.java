
public abstract class Utils {
	public static boolean isNullOrEmpty(String str) {
		return(str.trim().length() == 0 || str == null);
	}
	
	public static boolean isNumeric(String str) {
		boolean numeric = true;
		
		for(int i = 0; i < str.length() && numeric; i++) {
			if(!Character.isDigit(str.charAt(i))) {
				numeric = false;
			}
		}
		
		return numeric;
	}
}
