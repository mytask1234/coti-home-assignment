package io‏.coti.exception;

public class InvalidInputException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4744858762248583172L;

	private String messageResponse;

	public InvalidInputException() {

	}

	public InvalidInputException(String message) {
		super(message);
		this.messageResponse = message;
	}

	public InvalidInputException(Throwable cause) {
		super(cause);
	}

	public InvalidInputException(String message, Throwable cause) {
		super(message, cause);
		this.messageResponse = message;
	}

	public InvalidInputException(String message, String messageResponse) {
		super(message);
		this.messageResponse = messageResponse;
	}
	
	public InvalidInputException(String message, String messageResponse, Throwable cause) {
		super(message, cause);
		this.messageResponse = messageResponse;
	}

	public String getMessageResponse() {
		return messageResponse;
	}
}
