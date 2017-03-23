package ua.kh.butov.ishop.exception;

public class ResourceNotFoundException extends IllegalArgumentException {
	private static final long serialVersionUID = -6121766502027524096L;

	public ResourceNotFoundException(String s) {
		super(s);
	}
}
