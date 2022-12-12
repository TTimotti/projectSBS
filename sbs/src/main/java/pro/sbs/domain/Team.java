package pro.sbs.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

//엔터티 클래스와 데이터베이스 테이블의 이름이 다르면 반드시 name 속성을 지정
@Entity(name = "TEAMS_TEST")
// 오라클의 시퀀스 객체를 고유키 생성에 사용하기 위해서.
@SequenceGenerator(name = "TEAMS_TEST_SEQ_GEN", sequenceName = "TEAMS_TEST_SEQ", initialValue = 1, allocationSize = 1)
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Team extends BaseTimeEntity {

    // 테이블의 PK 컬럼 설정
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TEAMS_TEST_SEQ_GEN")
    private Integer teamId;

    @Column(nullable = false, unique = true) // NN
    private String teamName;

    @Column(nullable = false) // NN
    private String teamPassword;

    @Column(nullable = false) // NN
    private String teamLeader;

    @Column(nullable = false) // NN
    private String purpose;

    @Column(nullable = false) // NN
    private Integer categoryId;

    @Column(nullable = false) // NN
    private Integer maxMember;
    
    @Builder
    public Team(Integer teamId, String teamName, String teamPassword, String teamLeader, String purpose, Integer categoryId, Integer maxMember) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.teamPassword = teamPassword;
        this.teamLeader = teamLeader;
        this.purpose = purpose;
        this.categoryId = categoryId;
        this.maxMember = maxMember;
        
    }
    
    public Team update(String teamName, Integer maxMember) {
        this.teamName = teamName;
        this.maxMember = maxMember;
        
        return this;
    }
}
