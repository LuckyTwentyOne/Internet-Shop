package ua.kh.butov.ishop.util;

public class UrlUtils {

	public static boolean isAjaxUrl(String url) {
		return url.startsWith("/iShop/ajax/");
	}
	
	public static boolean isAjaxJsonUrl(String url) {
		return url.startsWith("/iShop/ajax/json/");
	}
	
	public static boolean isAjaxHtmlUrl(String url) {
		return url.startsWith("/iShop/ajax/html/");
	}

	public static boolean isStaticUrl(String url) {
		return url.startsWith("/iShop/static/");
	}

	public static boolean isMediaUrl(String url) {
		return url.startsWith("/iShop/media/");
	}

	private UrlUtils() {
	}
}
