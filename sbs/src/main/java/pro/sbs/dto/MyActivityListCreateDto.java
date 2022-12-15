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

    /**
     * 어떤 사용자가 어떤팀에서 어떤 활동을 하고있는지 종합하기위한 메서드 
     * 조인하면 되지않냐고 생각할 수 있는데 중복이나 복수 가입등이 있어 따로
     * 테이블을 만들어야함.
     */
    private Integer myListId;
    private Integer teamId;
    private Integer activityId;
    private Integer userId;
    
    public MyActivityList toEntity() {
        return MyActivityList.builder()
                .TeamId(teamId)
                .activityId(activityId)
                .userId(userId)
                .build();
    }
    
}
