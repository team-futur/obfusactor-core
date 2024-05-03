package kr.co.futur.obfuscatecore.core;

import kr.co.futur.obfuscatecore.utils.RenameUtils;
import kr.co.futur.obfuscatecore.visitor.RenameClassVisitor;
import kr.co.futur.obfuscatecore.visitor.RenameReferenceVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

/**
 * 입력 받은 클래스를 읽어서 ASM의 ClassReader와 ClassWriter를 사용하여 클래스, 메서드, 필드 이름을 변경하고 결과 출력
 */
public class Obfuscator {

	private final Logger logger = LoggerFactory.getLogger(Obfuscator.class);

	private final Set<String> processedEntries = new HashSet<>();

	/**
	 * Class 파일 난독화
	 * @param in 입력 Class 파일
	 * @param out 출력 Class 파일
	 * @throws IOException
	 */
	public void obfuscate(InputStream in, OutputStream out) throws IOException {

		ClassReader cr = new ClassReader(in);
		ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);

		Map<String, String> renameMap = new HashMap<>();
		RenameClassVisitor renameClassVisitor = new RenameClassVisitor(cw) {
			@Override
			public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
				String newName = RenameUtils.rename(name);
				renameMap.put(name, newName);
				super.visit(version, access, newName, signature, superName, interfaces);
			}
		};

		RenameReferenceVisitor renameReferenceVisitor = new RenameReferenceVisitor(renameClassVisitor, renameMap);
		cr.accept(renameReferenceVisitor, ClassReader.EXPAND_FRAMES);

		out.write(cw.toByteArray());

		logger.info("Obfuscate End");
	}

	/**
	 *  클래스 파일 난독화
	 * @param inputFile 입력 Class 파일
	 * @param outputFile 출력 Class 파일
	 * @throws IOException
	 */
	public void obfuscate(File inputFile, File outputFile) throws IOException {
		try (InputStream in = new FileInputStream(inputFile);
		     OutputStream out = new FileOutputStream(outputFile)) {
			obfuscate(in, out);
		}
	}

	/**
	 * Jar 파일 난독화
	 * @param inputJar  입력 Jar 파일
	 * @param outputJar 출력 Jar 파일
	 * @throws IOException
	 */
	public void obfuscateJar(File inputJar, File outputJar) throws IOException {
		try (JarFile jarFile = new JarFile(inputJar);
		     JarOutputStream jos = new JarOutputStream(new FileOutputStream(outputJar))) {

			Enumeration<JarEntry> entries = jarFile.entries();
			Set<String> processedEntries = new HashSet<>();

			while (entries.hasMoreElements()) {
				JarEntry entry = entries.nextElement();

				try (InputStream is = jarFile.getInputStream(entry)) {
					if (entry.getName().endsWith(".class")) {
						// 클래스 파일에 대해 obfuscate 메서드를 실행
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						obfuscate(is, baos);

						// 변경된 클래스를 새 jar 파일에 씀
						String newName;
						do {
							newName = RenameUtils.rename(entry.getName());
						} while (processedEntries.contains(newName));
						processedEntries.add(newName);

						JarEntry newEntry = new JarEntry(newName);
						jos.putNextEntry(newEntry);
						jos.write(baos.toByteArray());
						jos.closeEntry();
					} else {
						// 클래스 파일이 아닌 경우, 원본 내용을 그대로 복사
						JarEntry newEntry = new JarEntry(entry.getName());
						jos.putNextEntry(newEntry);
						byte[] buffer = new byte[1024];
						int bytesRead;
						while ((bytesRead = is.read(buffer)) != -1) {
							jos.write(buffer, 0, bytesRead);
						}
						jos.closeEntry();
					}
				}
			}
		}
	}
}
