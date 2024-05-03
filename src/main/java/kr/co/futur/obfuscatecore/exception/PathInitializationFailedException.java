package kr.co.futur.obfuscatecore.exception;

public class PathInitializationFailedException extends RuntimeException {

	private static final String ERROR_MESSAGE = "File path initialization failed";

	public PathInitializationFailedException() {
		super(ERROR_MESSAGE);
	}

	public PathInitializationFailedException(String message) {
		super(message);
	}

	public PathInitializationFailedException(Throwable cause) {
		super(ERROR_MESSAGE, cause);
	}

	public PathInitializationFailedException(String message, Throwable cause) {
		super(message, cause);
	}

}
