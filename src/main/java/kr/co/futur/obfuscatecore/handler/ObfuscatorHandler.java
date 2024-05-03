package kr.co.futur.obfuscatecore.handler;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface ObfuscatorHandler {

	/**
	 * 난독화된 파일명 생성
	 * @return 파일명
	 */
	String generateOutputFileName(String originalFileName);

	/**
	 * 난독화 하는 메서드
	 * @param inputJarPath Jar 파일 경로
	 * @param outputJarPath 난독화된 Jar 파일 경로
	 * @return 난독화 성공 여부
	 */
	boolean obfuscate(String inputJarPath, String outputJarPath, String configPath);

	/**
	 * Jar 파일 저장
	 * @param inputJar Jar 파일
	 */
	Path saveInputJar(MultipartFile inputJar);

	/**
	 * Jar 파일 저장
	 * @param base64InputJar Jar 파일 Base64
	 */
	Path saveInputJar(String base64InputJar);

	/**
	 * Jar 파일 삭제
	 * @param inputJarPath Jar 파일 경로
	 * @return 삭제 성공 여부
	 */
	boolean removeInputJar(Path inputJarPath);

}
