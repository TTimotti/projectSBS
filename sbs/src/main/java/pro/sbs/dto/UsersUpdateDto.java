package pro.sbs.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import pro.sbs.domain.Users;

/**
 * 유저정보 업데이트에 사용되는 dto
 * @author ITWILL
 *
 */
@NoArgsConstructor
@Data
public class UsersUpdateDto {

    private Integer userId;
    
    private String userName;
     
    private String password; 

    private String nickname; 

    private String email; 

    private String phone; 

    private String gender;

    private String birthdate;
    
    private String location;

    private Integer cash;

    public Users toEntity() {
        return Users.builder().userId(userId).userName(userName).password(password).nickname(nickname).email(email).phone(phone)
                .gender(gender).birthdate(birthdate).cash(cash).location(location).build();
    }
    
    
}
