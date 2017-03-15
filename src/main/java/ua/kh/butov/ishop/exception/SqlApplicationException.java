package ua.kh.butov.ishop.exception;


/**
 * Converts checked {@code SqlException} into unchecked {@code SqlApplicationException}
 * 
 * @author V.Butov
 */
public class SqlApplicationException extends RuntimeException {
	private static final long serialVersionUID = -4183474678160151871L;

	/**
	 * Creates a new {@code SqlApplicationException} object with a specified message.
	 *
	 * @param message
	 *            message of the exception
	 */
	public SqlApplicationException(String message) {
		super(message);
		
	}

	/**
	 * Creates a new {@code SqlApplicationException} object with a specified cause.
	 *
	 * @param cause
	 *            cause of the exception
	 */
	public SqlApplicationException(Throwable cause) {
		super(cause);
		
	}

	/**
	 * Creates a new {@code SqlApplicationException} object with a specified message and
	 * cause.
	 *
	 * @param message
	 *            message of the exception
	 * @param cause
	 *            cause of the exception
	 */
	public SqlApplicationException(String message, Throwable cause) {
		super(message, cause);
		
	}

}
