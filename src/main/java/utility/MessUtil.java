package utility;

public class MessUtil {
	public static String enter(String method) {
		return "Method: "+method+", Entering method.";
	}
	public static String enter(String method, String route) {
		return "Method: "+method+", Entering method for route "+route+".";
	}
	public static String exit(String method) {
		return "Method: "+method+", Exiting method.";
	}
	public static String exitLoading(String method, String route, String page) {
		return "Method: "+method+", Exiting method for route "+route+". Loading "+page+".";
	}
	public static String errorLevel(String method, String action) {
		return "Method "+method+", Could not "+action+".";
	}
}
