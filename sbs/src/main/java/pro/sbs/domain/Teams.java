package pro.sbs.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 팀 생성을 위한 엔터티
 * @author 김지훈
 *
 */
@Entity(name = "TEAMS")
@SequenceGenerator(name = "TEAMS_SEQ_GEN", sequenceName = "TEAMS_SEQ", initialValue = 1, allocationSize = 1)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
public class Teams extends BaseTimeEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TEAMS_SEQ_GEN")
    private Integer teamId;

    @Column(nullable = false, unique = true)
    private String teamName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String leader;

    @Column(nullable = false)
    private String purpose;

    @Column(nullable = false)
    private Integer maxMember;
    
    @Column(nullable = false)
    private Integer fid;
    
    
    /**
     * 팀에 대한 정보(팀 이름, 팀 소개, 팀 최대인원)를 수정할 때 쓰는 메서드
     * @param teamName 바꿀 팀 이름
     * @param purpose 바꿀 팀 소개
     * @param maxMember 바꿀 최대 인원
     * @return
     * @author 서범수
     */
    public Teams update(String teamName, String purpose, Integer maxMember) {
        this.teamName = teamName;
        this.purpose = purpose;
        this.maxMember = maxMember;
        
        return this;
    }

    /**
     * 팀 비밀번호를 바꿉니다.
     * @param password 바꿀 비밀번호.
     */
    public void changePassword(String password) {
        this.password = password;
        
    }


    

}
