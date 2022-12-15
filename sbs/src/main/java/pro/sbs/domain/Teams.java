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



//    public Teams update(String teamName, Integer maxMember, String teamLeader, String teamPassword) {
//        this.teamName = teamName;
//        this.maxMember = maxMember;
//        this.teamLeader = teamLeader;
//        this.teamPassword = teamPassword;
//        
//        return this;
//    }
    

}
