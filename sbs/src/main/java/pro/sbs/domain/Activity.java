package pro.sbs.domain;

import java.time.LocalDateTime;

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

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString 
@Entity(name = "ACTIVITIES") 
@SequenceGenerator(name = "ACTIVITIES_SEQ_GEN", sequenceName = "ACTIVITIES_SEQ", initialValue = 1, allocationSize = 1)
public class Activity extends BaseTimeEntity {

    @Id 
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACTIVITIES_SEQ_GEN")
    @Column(nullable = false, name = "activity_id")
    private Integer activityId;

    @Column(nullable = false)
    private String play;

    private Integer budget;

    @Column(nullable = false, name = "team_id")
    private Integer teamId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "start_time")
    private LocalDateTime startTime;
    
    @Column(name = "place")
    private String place;
    
    public Activity createDto(Integer teamId) {
        this.teamId = teamId;

        return this;
    }
}
