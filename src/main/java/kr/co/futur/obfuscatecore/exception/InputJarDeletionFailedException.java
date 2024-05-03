package kr.co.futur.obfuscatecore.exception;

public class InputJarDeletionFailedException extends RuntimeException {

	private static final String ERROR_MESSAGE = "Jar deletion failed";

	public InputJarDeletionFailedException() {
		super(ERROR_MESSAGE);
	}

	public InputJarDeletionFailedException(Throwable cause) {
		super(ERROR_MESSAGE, cause);
	}

	public InputJarDeletionFailedException(String message) {
		super(message);
	}

	public InputJarDeletionFailedException(String message, Throwable cause) {
		super(message, cause);
	}

}
