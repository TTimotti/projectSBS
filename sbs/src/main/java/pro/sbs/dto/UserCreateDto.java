package pro.sbs.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import pro.sbs.domain.User;
import pro.sbs.domain.UserRole;

@NoArgsConstructor
@Data
public class UserCreateDto {

    private String userName;
 
    private String password; // 사용자 (로그인) 비밀번호.

    private String nickname; // 사용자 별명(닉네임)

    private String email; // 사용자 이메일 -> social 로그인 기능에서 사용될 수 있음.

    private String phone; // 사용자 이메일 -> social 로그인 기능에서 사용될 수 있음.
 
    private String gender;

    private String birthday;
    
    private Integer locationId;

    public User toEntity() {
        return User.builder().userName(userName).password(password).nickname(nickname).email(email).phone(phone)
                .gender(gender).birthday(birthday).locationId(locationId).build().addRole(UserRole.ADMIN);
    }
}