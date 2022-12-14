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

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;



/**
 * 유저 생성을 위한 엔터티
 * @author 김지훈
 *
 */
@Entity(name = "USERS")
@SequenceGenerator(name = "USERS_SEQ_GEN", sequenceName = "USERSS_SEQ", allocationSize = 1)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
public class Users extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERS_SEQ_GEN")
    private Integer userId;

    @Column(unique = true, nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String birthdate;
    
    @Column(nullable = false)
    private Integer fid;
    
    @Column(nullable = false)
    private String location;
    
    /*
     * 사이트 내 권한
     */
    @Column(nullable = false)
    @Builder.Default
    private String rank = "일반회원";
    
    @Column(nullable = false)
    @Builder.Default
    private Integer cash = 0;    

    /*
     * 스프링 시큐리티 용 권한
     */
    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<UserRole> roles = new HashSet<>();    
    
    public Users addRole(UserRole role) {
        roles.add(role);

        return this;
    }

    public Users update(String nickname, String email, String phone, String gender, String birthdate, String location) {

        this.nickname = nickname;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.birthdate = birthdate;
        this.location = location;

        return this;
    }

    public Users passwordChange(String password) {
        this.password = password;

        return this;
    }

    public Users chargeCash(Integer cash) {
    	if (this.cash == 0) {
    		this.cash = 0;
    	}
        this.cash += cash;

        return this;
    }

}