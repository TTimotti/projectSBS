package pro.sbs.dto;

import lombok.Data;
import lombok.Builder;
import pro.sbs.domain.Activity;

@Builder
@Data
public class ActivityReadDto {

    private Integer activityId;
    private Integer teamId;
    private String play;
    private String place;
    private String startTime;
    
    public static ActivityReadDto fromEntity(Activity entity) {
        return ActivityReadDto.builder()
                .activityId(entity.getActivityId())
                .teamId(entity.getTeamId())
                .play(entity.getPlay())
                .place(entity.getPlace())
                .build();
    }
}
