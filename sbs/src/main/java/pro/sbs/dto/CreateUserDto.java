package pro.sbs.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import pro.sbs.domain.UserRole;
import pro.sbs.domain.Users;

/**
 * 회원 가입 DTO
 * @author 김지훈
 *
 */
@NoArgsConstructor
@Data
public class CreateUserDto {
    
    private String userName;    
    private String password; // 사용자 (로그인) 비밀번호.
    private String nickname; // 사용자 별명(닉네임)
    private String email; // 사용자 이메일 -> social 로그인 기능에서 사용될 수 있음.
    private String phone; // 사용자 이메일 -> social 로그인 기능에서 사용될 수 있음. 
    private String gender;
    private String birthdate;    
    private String location;
    private Integer fid;

    public Users toEntity() {
        return Users.builder().userName(userName).password(password).nickname(nickname).email(email).phone(phone)
                .gender(gender).birthdate(birthdate).location(location).fid(fid).build().addRole(UserRole.ADMIN);
    }
}
