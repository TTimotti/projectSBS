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
public class TeamLog extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TEAMS_LOG_SEQ_GEN")
    private Integer teamLogId;
    
    @Column(nullable = false, name="user_name")
    private String userName;
    
    @Column(nullable = false, name="team_id")
    private Integer teamId;
    
}
