package pro.sbs.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import pro.sbs.domain.Users;

@NoArgsConstructor
@Data
public class PasswordChangeDto {

    private Integer userId;
    
    private String password;
    
    public Users toEntity() {
        return Users.builder().userId(userId).password(password).build();
    }
    
}
