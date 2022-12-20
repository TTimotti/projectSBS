package pro.sbs.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pro.sbs.domain.Activity;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ActivityUpdateDto {

    private Integer activityId;
    private String play;
    private String place;
    private Integer teamId;
    private Integer budget;
    private String startTime;
    
    
    public Activity toEntity() {
        return Activity.builder()
                .activityId(activityId)
                .play(play)
                .place(place)
                .budget(budget)
                .startTime(LocalDateTime.parse(startTime))
                .build();
    }
    
    
}
