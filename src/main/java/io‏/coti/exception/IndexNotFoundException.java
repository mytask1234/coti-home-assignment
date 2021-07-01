package io‏.coti.exception;

public class IndexNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3703001316024115616L;
	
	private String messageResponse;

	public IndexNotFoundException() {

	}

	public IndexNotFoundException(String message) {
		super(message);
		this.messageResponse = message;
	}

	public IndexNotFoundException(Throwable cause) {
		super(cause);
	}

	public IndexNotFoundException(String message, Throwable cause) {
		super(message, cause);
		this.messageResponse = message;
	}

	public IndexNotFoundException(String message, String messageResponse) {
		super(message);
		this.messageResponse = messageResponse;
	}
	
	public IndexNotFoundException(String message, String messageResponse, Throwable cause) {
		super(message, cause);
		this.messageResponse = messageResponse;
	}

	public String getMessageResponse() {
		return messageResponse;
	}
}
