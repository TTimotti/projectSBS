package pro.sbs.dto;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import lombok.Getter;
import lombok.ToString;
import pro.sbs.domain.User;

@Getter
@ToString
public class UserSecurityDto extends org.springframework.security.core.userdetails.User {

    private String userName;
    private String password;
    private String email;
    private String nickname;
    private String phone;
    private String gender;
    private String birthday;
    private Integer locationId;
    private Integer cash;

    public UserSecurityDto(String userName, String password, String email, String nickname, String phone,
            String birthday, Integer cash, String gender, Integer locationId,
            Collection<? extends GrantedAuthority> authorities) {
        super(userName, password, authorities);
                
        this.userName = userName;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.birthday = birthday;
        this.locationId = locationId;
        this.cash = cash;
        
    }

    public static UserSecurityDto fromEntity(User u) {
        List<GrantedAuthority> authorities = u.getRoles().stream().map(x -> new SimpleGrantedAuthority(x.getRole()))
                .collect(Collectors.toList());

        UserSecurityDto dto = new UserSecurityDto(u.getUserName(), u.getPassword(), u.getEmail(), u.getNickname(), u.getPhone(), u.getBirthday(), u.getCash(), u.getGender(), u.getLocationId(), authorities);

        return dto;
    }
}