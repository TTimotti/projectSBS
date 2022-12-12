package pro.sbs.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
@Entity(name = "USERS")
@SequenceGenerator(name = "USERS_SEQ_GEN", sequenceName = "USERS_SEQ", allocationSize = 1)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERS_SEQ_GEN")
    private Integer userId;

    @Column(unique = true, nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password; // 사용자 (로그인) 비밀번호.

    @Column(nullable = false)
    private String nickname; // 사용자 별명(닉네임)

    @Column(nullable = false)
    private String email; // 사용자 이메일 -> social 로그인 기능에서 사용될 수 있음.

    @Column(nullable = false)
    private String phone; // 사용자 이메일 -> social 로그인 기능에서 사용될 수 있음.

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String birthday;

    private Integer locationId;

    private Integer cash;

    private String userImage;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<UserRole> roles = new HashSet<>();

    public User addRole(UserRole role) {
        roles.add(role);

        return this;
    }

    public User update(String nickname, String email, String phone, String gender, String birthday,
            Integer locationId) {

        this.nickname = nickname;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.birthday = birthday;
        this.locationId = locationId;

        return this;
    }

    public User passwordChange(String password) {
        this.password = password;

        return this;
    }

    public User cash(Integer cash) {
        this.cash += cash;

        return this;
    }

}