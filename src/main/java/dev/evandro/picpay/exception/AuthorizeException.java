package dev.evandro.picpay.exception;

public class AuthorizeException extends RuntimeException{

	public AuthorizeException(String message) {
		super(message);
	}
}
