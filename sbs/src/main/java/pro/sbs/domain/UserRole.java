package pro.sbs.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** 
 * 사용자의 권한 - 사이트 이용자, 사이트 관리자
 * Spring Security에서만! 사용됨.
 * @author 김지훈
 *
 */
@Getter
@AllArgsConstructor
public enum UserRole {
    USER("ROLE_USER"), ADMIN("ROLE_ADMIN");

    private String role;

}
