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


@Entity(name = "TEAMS_TEST")
@SequenceGenerator(name = "TEAMS_TEST_SEQ_GEN", sequenceName = "TEAMS_TEST_SEQ", initialValue = 1, allocationSize = 1)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
public class Team extends BaseTimeEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TEAMS_TEST_SEQ_GEN")
    private Integer teamId;

    @Column(nullable = false, unique = true)
    private String teamName;

    @Column(nullable = false)
    private String teamPassword;

    @Column(nullable = false)
    private String teamLeader;

    @Column(nullable = false)
    private String purpose;

    @Column(nullable = false)
    private Integer categoryId;

    @Column(nullable = false)
    private Integer maxMember;

    public Team update(String teamName, Integer maxMember, String teamLeader, String teamPassword) {
        this.teamName = teamName;
        this.maxMember = maxMember;
        this.teamLeader = teamLeader;
        this.teamPassword = teamPassword;
        
        return this;
    }
    

}
