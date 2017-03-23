package ua.kh.butov.ishop.exception;

import javax.servlet.http.HttpServletResponse;

/**
 * Converts checked {@code SqlException} into unchecked {@code SqlApplicationException}
 * 
 * @author V.Butov
 */
public class InternalServerErrorException extends AbstractApplicationException {
	private static final long serialVersionUID = -4183474678160151871L;

	/**
	 * Creates a new {@code InternalServerErrorException} object with a specified message.
	 *
	 * @param message
	 *            message of the exception
	 */
	public InternalServerErrorException(String message) {
		super(message, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		
	}

	/**
	 * Creates a new {@code InternalServerErrorException} object with a specified cause.
	 *
	 * @param cause
	 *            cause of the exception
	 */
	public InternalServerErrorException(Throwable cause) {
		super(cause, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		
	}

	/**
	 * Creates a new {@code InternalServerErrorException} object with a specified message and
	 * cause.
	 *
	 * @param message
	 *            message of the exception
	 * @param cause
	 *            cause of the exception
	 */
	public InternalServerErrorException(String message, Throwable cause) {
		super(message, cause, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		
	}

}
