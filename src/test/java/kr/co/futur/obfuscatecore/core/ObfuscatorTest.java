package kr.co.futur.obfuscatecore.core;

import kr.co.futur.obfuscatecore.utils.RenameUtils;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class ObfuscatorTest {

	private final Obfuscator obfuscator = new Obfuscator();

	@Test
	void obfuscateStream() {
		File inputFile = new File("src/test/resources/classes/test.class");
		File outputFile = new File("src/test/resources/classes/output.class");

		try (InputStream in = new FileInputStream(inputFile);
		     OutputStream out = new FileOutputStream(outputFile)) {
			obfuscator.obfuscate(in, out);
		} catch (IOException e) {
			fail("Obfuscation failed", e);
		}

		assertTrue(outputFile.exists(), "Output file does not exist");
	}

	@Test
	void obfuscateFile() {
		File inputFile = new File("src/test/resources/classes/test.class");
		File outputFile = new File("src/test/resources/classes/output.class");

		try {
			obfuscator.obfuscate(inputFile, outputFile);
		} catch (IOException e) {
			fail("Obfuscation failed", e);
		}

		assertTrue(outputFile.exists(), "Output file does not exist");
	}

	@Test
	void obfuscateJar() {
		File inputJar = new File("src/test/resources/jars/test.jar");
		File outputJar = new File("src/test/resources/jars/output.jar");

		try {
			obfuscator.obfuscateJar(inputJar, outputJar);
		} catch (IOException e) {
			fail("Obfuscation failed", e);
		}

		assertTrue(outputJar.exists(), "Output jar does not exist");
	}

}