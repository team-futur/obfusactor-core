package kr.co.futur.obfuscatecore.exception;

public class InputJarSavingFailedException extends RuntimeException {

	private static final String ERROR_MESSAGE = "Jar saving failed";

	public InputJarSavingFailedException() {
		super(ERROR_MESSAGE);
	}

	public InputJarSavingFailedException(Throwable cause) {
		super(ERROR_MESSAGE, cause);
	}

	public InputJarSavingFailedException(String message) {
		super(message);
	}

	public InputJarSavingFailedException(String message, Throwable cause) {
		super(message, cause);
	}
}
