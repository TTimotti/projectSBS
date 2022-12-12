package pro.sbs.dto;



import lombok.Data;
import lombok.NoArgsConstructor;
import pro.sbs.domain.User;

@NoArgsConstructor
@Data
public class UserCashDto {
    private Integer userId;
    private Integer cash;
    
    public User toEntity() {
        return User.builder().userId(userId).cash(cash).build();
    }
}
