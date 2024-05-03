package kr.co.futur.obfuscatecore.visitor;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.Map;

/**
 * 참조 구문 난독화 이름에 맞게 변경
 */
public class RenameReferenceVisitor extends ClassVisitor {

	private final Map<String, String> renameMap;

	public RenameReferenceVisitor(ClassVisitor classVisitor, Map<String, String> renameMap) {
		super(Opcodes.ASM9, classVisitor);
		this.renameMap = renameMap;
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		// 클래스 참조 변경
		superName = renameMap.getOrDefault(superName, superName);
		for (int i = 0; i < interfaces.length; i++) {
			interfaces[i] = renameMap.getOrDefault(interfaces[i], interfaces[i]);
		}
		super.visit(version, access, name, signature, superName, interfaces);
	}

	@Override
	public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
		// 필드 참조 변경
		descriptor = renameInDescriptor(descriptor);
		return super.visitField(access, name, descriptor, signature, value);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
		// 메서드 참조 변경
		descriptor = renameInDescriptor(descriptor);
		if (exceptions != null) {
			for (int i = 0; i < exceptions.length; i++) {
				exceptions[i] = renameMap.getOrDefault(exceptions[i], exceptions[i]);
			}
		}
		return super.visitMethod(access, name, descriptor, signature, exceptions);
	}

	private String renameInDescriptor(String descriptor) {
		// 디스크립터 내의 클래스 참조 변경
		for (Map.Entry<String, String> entry : renameMap.entrySet()) {
			descriptor = descriptor.replace(entry.getKey(), entry.getValue());
		}
		return descriptor;
	}

}
