package pro.sbs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pro.sbs.domain.MyActivityList;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MyActivityListCreateDto {

    private Integer myListId;
    private Integer teamId;
    private Integer activityId;
    private String userName;
    private String nickname;
    
    public MyActivityList toEntity() {
        return MyActivityList.builder()
                .TeamId(teamId)
                .activityId(activityId)
                .userName(userName)
                .nickName(nickname)
                .build();
    }
    
}
