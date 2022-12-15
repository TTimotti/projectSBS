package pro.sbs.dto;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import pro.sbs.domain.Users;

public class UsersSecurityDto extends User {

    private String username;
    private String password;
    private String email;
    private String nickname;
    
    public UsersSecurityDto(String username, String password, String email, String nickname, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);

        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        
    }
    
    public static UsersSecurityDto fromEntity(Users user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(x -> new SimpleGrantedAuthority(x.getRole()))
                .collect(Collectors.toList());
        UsersSecurityDto dto = new UsersSecurityDto(user.getUserName(), 
                user.getPassword(), user.getEmail(), user.getNickname(), authorities);
        
        return dto;
    }

}
