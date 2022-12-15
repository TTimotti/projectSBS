package pro.sbs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pro.sbs.domain.Activity;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ActivityCreateDto {

    private String play;
    private String place;
    private String startTime;
    private Integer teamId;
    
    public Activity toEntity() {
        return Activity.builder()
                .play(play)
                .place(place)
                .teamId(teamId)
                .build();
    }
    
    
//    public ActivityCreateDto fromEntity(Activity entity) {
//        return ActivityCreateDto.builder()
//                .play(entity.getPlay())
//                .place(entity.getPlace())
//                .startTime(entity.getStartTime())
//                .build();
//    }
    
    
    /**
     * teamId를 받아온 활동 정보를 만드는 메서드.
     * 
     * @param entity
     * @return
     */
//    public ActivityCreateDto fromEntity(Activity entity) {
//        return ActivityCreateDto.builder()
//                .teamId(entity.getTeam().getTeamId())
//                .play(entity.getPlay())
//                .place(entity.getPlace())
//                .startTime(entity.getStartTime())
//                .build();
//    }

}











