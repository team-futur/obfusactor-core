package kr.co.futur.obfuscatecore.config;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProguardConfiguration {

	/**
	 * 멤버와 메서드를 제외하고 클래스만 난독화
	 */
	private boolean exceptMemberAndMethod;

	/**
	 * 멤버를 제외하고 난독화
	 */
	private boolean exceptMember;

	/**
	 * 최적화 여부
	 */
	private boolean isOptimization;

	/**
	 * 코드 축소 여부
	 */
	private boolean isShrink;

	/**
	 * 특정 멤버를 가진 클래스를 보존
	 */
	private List<String> keepClassesWithMembers;

	/**
	 * 특정 클래스 이름을 보존
	 */
	private List<String> keepClassNames;

	/**
	 * 특정 패키지를 보존
	 */
	private List<String> keepPackages;

}
