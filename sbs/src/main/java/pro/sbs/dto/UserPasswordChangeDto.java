package pro.sbs.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import pro.sbs.domain.User;

@NoArgsConstructor
@Data
public class UserPasswordChangeDto {
    private Integer userId;
    private String password;
    
    public User toEntity() {
        return User.builder().userId(userId).password(password).build();
    }
}
