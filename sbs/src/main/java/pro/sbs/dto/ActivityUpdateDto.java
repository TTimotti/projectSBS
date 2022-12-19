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

    private Integer id;
    private String play;
    private String place;
    private Integer teamId;
    private String startTime;
    
    
    public Activity toEntity() {
        return Activity.builder()
                .activityId(id)
                .play(play)
                .place(place)
                .startTime(LocalDateTime.parse(startTime))
                .build();
    }
    
    
}
