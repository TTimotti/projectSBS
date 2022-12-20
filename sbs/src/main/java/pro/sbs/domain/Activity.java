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
// @Entity : 기본생성자, getter 메서드 고유키 @Id 반드시 가져야한다.  
@Entity(name = "ACTIVITIES") // 엔터티 클래스와 데이터베이스 테이블의 이름이 다르면 반드시 name 속성을 지정.
@SequenceGenerator(name = "ACTIVITIES_SEQ_GEN", sequenceName = "ACTIVITIES_SEQ", initialValue = 1, allocationSize = 1)
//-> 오라클의 시퀀스 객체를 고유키 생성에 사용하기 위해서.
public class Activity extends BaseTimeEntity {

    @Id // Primary Key(고유키)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACTIVITIES_SEQ_GEN")
    @Column(nullable = false, name = "activity_id")
    private Integer activityId;

    @Column(nullable = false, name ="play")
    private String play;

    @Column (name = "budget")
    private Integer budget;

    @Column(nullable = false, name = "team_id")
    private Integer teamId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "start_time")
    private LocalDateTime startTime;
    
    @Column(name = "place")
    private String place;
    
    public Activity createDto(Integer teamId) {

        this.teamId = teamId;

        return this;
    }

    public Activity update(String play, String place, Integer budget, LocalDateTime startTime) {
        this.play = play;
        this.place = place;
        this.budget = budget;
        this.startTime = startTime;
        
        return this;
    }
}
