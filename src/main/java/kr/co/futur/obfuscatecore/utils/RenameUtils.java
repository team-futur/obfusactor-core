package kr.co.futur.obfuscatecore.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RenameUtils {

	private static final Logger logger = LoggerFactory.getLogger(RenameUtils.class);

	private static final Random random = new Random();

	private static final Map<String, String> nameMap = new HashMap<>();

	/**
	 * 랜덤 이름 생성
	 * @param length
	 * @return
	 */
	private static String generateBarcodeRandomName(int length) {
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			sb.append(random.nextBoolean() ? 'i' : 'l');
		}
		return sb.toString();
	}

	/**
	 * 랜덤 이름
	 * @return
	 */
	public static String rename(String originalName) {
		if (nameMap.containsKey(originalName)) {
			return nameMap.get(originalName);
		}

		String newName = generateBarcodeRandomName(random.nextInt(30) + 1);
		nameMap.put(originalName, newName);

		return newName;
	}

}