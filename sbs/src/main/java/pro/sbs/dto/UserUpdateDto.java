package pro.sbs.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import pro.sbs.domain.User;

@NoArgsConstructor
@Data
public class UserUpdateDto {

    private Integer userId;
    
    private String userName;
     
    private String password; 

    private String nickname; 

    private String email; 

    private String phone; 

    private String gender;

    private String birthday;
    
    private Integer locationId;

    private Integer cash;

    public User toEntity() {
        return User.builder().userId(userId).userName(userName).password(password).nickname(nickname).email(email).phone(phone)
                .gender(gender).birthday(birthday).cash(cash).build();
    }
    
}