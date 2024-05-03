package kr.co.futur.obfuscatecore.handler.proguard;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import kr.co.futur.obfuscatecore.exception.InputJarDeletionFailedException;
import kr.co.futur.obfuscatecore.exception.InputJarSavingFailedException;
import kr.co.futur.obfuscatecore.exception.PathInitializationFailedException;
import kr.co.futur.obfuscatecore.handler.ObfuscatorHandler;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

public class ProguradHandler implements ObfuscatorHandler {

	private final Logger logger = LoggerFactory.getLogger(ProguradHandler.class);

	@Getter
	private final Path rootPath;

	public ProguradHandler(String rootPath) {
		this.rootPath = Paths.get(rootPath);
		boolean isCreate = createRootDirectory();

		if (!isCreate) {
			throw new PathInitializationFailedException();
		}
	}

	/**
	 * 파일 저장 경로 생성
	 * @return 파일 저장 경로 생성 성공 여부
	 */
	private boolean createRootDirectory() {
		boolean isSuccess = false;

		try {
			Files.createDirectories(this.rootPath);
			isSuccess = true;
		} catch (IOException e) {
			throw new PathInitializationFailedException(e);
		}

		return isSuccess;
	}

	/**
	 * 파일명 생성
	 * @return 파일명
	 */
	private String generateRandomFileName() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 설정 파일 생성
	 * @param inputJarPath 입력 Jar 파일 경로
	 * @param outputJarPath 출력 Jar 파일 경로
	 * @return 설정 파일 경로
	 */
	private Path generateConfigurationPath(String inputJarPath, String outputJarPath) {
		return null;
	}

	@Override
	public String generateOutputFileName(String originalFileName) {
		if(originalFileName == null || !originalFileName.endsWith(".jar")) {
			return "obfuscated.jar";
		}

		return String.format("obfuscated_%s", originalFileName);
	}

	@Override
	public boolean obfuscate(String inputJarPath, String outputJarPath, String configPath) {
		return false;
	}

	@Override
	public Path saveInputJar(@NotNull(message = "inputJar is required") MultipartFile inputJar) {

		String originalJarName = inputJar.getOriginalFilename();

		if (originalJarName == null) {
			originalJarName = generateRandomFileName();
		}

		Path targetPath = this.rootPath.resolve(originalJarName);

		try {
			inputJar.transferTo(targetPath);
			logger.debug("Input Jar file saved: {}", targetPath);
		} catch (IOException e) {
			throw new InputJarSavingFailedException(e);
		}

		return targetPath;
	}

	@Override
	public Path saveInputJar(@NotEmpty(message = "base64InputJar is required") String base64InputJar) {
		String iarFileName = String.format("%S.jar", generateRandomFileName());
		Path targetPath = this.rootPath.resolve(iarFileName);

		byte[] decodedBytes = Base64.getDecoder().decode(base64InputJar);

		try {
			Files.write(targetPath, decodedBytes);
			logger.debug("Input Jar file saved: {}", targetPath);
		} catch (IOException e) {
			throw new InputJarSavingFailedException(e);
		}

		return targetPath;
	}

	@Override
	public boolean removeInputJar(@NotNull(message = "inputJarPath is required") Path inputJarPath) {

		boolean isRemove = false;

		try {
			Files.deleteIfExists(inputJarPath);
			logger.debug("Input Jar file deleted: {}", inputJarPath);
			isRemove = true;
		} catch (IOException e) {
			throw new InputJarDeletionFailedException(e);
		}

		return isRemove;
	}

}
