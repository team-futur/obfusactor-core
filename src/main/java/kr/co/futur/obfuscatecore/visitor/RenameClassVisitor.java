package kr.co.futur.obfuscatecore.visitor;

import kr.co.futur.obfuscatecore.utils.RenameUtils;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

/**
 * 클래스, 메서드, 필드 이름 난독화
 */
public class RenameClassVisitor extends ClassVisitor {

	public RenameClassVisitor(ClassVisitor classVisitor) {
		super(Opcodes.ASM9, classVisitor);
	}

	/**
	 * 클래스 이름 변경
	 */
	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		super.visit(version, access, RenameUtils.rename(name), signature, superName, interfaces);
	}

}