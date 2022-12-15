package pro.sbs.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import pro.sbs.domain.Users;

@NoArgsConstructor
@Data
public class UsersCashDto {

    private Integer userId;
    
    private String userName;
    
    private Integer cash; 
    
    public Users toEntity() {
        return Users.builder().userId(userId).userName(userName).cash(cash).build();
    }
    
} 
  