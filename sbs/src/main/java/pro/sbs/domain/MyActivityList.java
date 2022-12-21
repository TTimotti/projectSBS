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

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString  
@Entity(name = "MYACTIVITYLIST") 
@SequenceGenerator(name = "MYACTIVITYLIST_SEQ_GEN", sequenceName = "MYACTIVITYLIST_SEQ", initialValue = 1, allocationSize = 1)
public class MyActivityList extends BaseTimeEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MYACTIVITYLIST_SEQ_GEN")
    @Column(nullable = false, name = "my_list_id")
    private Integer myListId;
    
    @Column(name = "team_id", columnDefinition = "number (6)")
    private Integer teamId;
    
    @Column(name = "activity_id", columnDefinition = "number (6)")
    private Integer activityId;
    
    @Column(name = "nickname", length = 400)
    private String nickName;
    
    @Column(name = "user_name", length = 400)
    private String userName;

}
