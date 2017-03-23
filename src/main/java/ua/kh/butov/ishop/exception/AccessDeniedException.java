package ua.kh.butov.ishop.exception;

public class AccessDeniedException extends IllegalArgumentException {
	private static final long serialVersionUID = -8981777225188967376L;

	public AccessDeniedException(String s) {
		super(s);
	}
}
