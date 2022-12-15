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

@Entity(name = "TEAMS_LOG")
@SequenceGenerator(name = "TEAMS_LOG_SEQ_GEN", sequenceName = "TEAMS_LOG_SEQ", initialValue = 1, allocationSize = 1)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
public class TeamsLog extends BaseTimeEntity{

    /*
     * 사용자의 팀 가입 내역을 기록하기 위한 도메인(테이블)
     * 사용자(userName)가 어느 팀(teamId)에 가입되었는지 기록이 나옴.
     * 
     * 비밀번호 입력 후 가입하면 자동으로 생성.
     * 
     * !!! 추후에 또 수정할 예정입니다 !!!
     */

    // DB에 저장될 가입 내역의 고유 ID. (시퀀스로 저장)
    @Id // 시퀀스
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TEAMS_LOG_SEQ_GEN")
    private Integer teamLogId;

    // UserName(로그인 아이디)
    @Column(nullable = false, name="user_name")
    private String userName;

    // 가입할 팀의 ID.
    @Column(nullable = false, name="team_id")
    private Integer teamId;

}