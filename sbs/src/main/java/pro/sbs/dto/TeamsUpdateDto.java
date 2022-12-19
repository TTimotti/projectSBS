package pro.sbs.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import pro.sbs.domain.Teams;


/**
 * 팀에 대한 정보를 업데이트하기 위해 만들음.
 * @author 서범수
 *
 */
@NoArgsConstructor
@Data
public class TeamsUpdateDto {
    private Integer teamId;
    private String teamName;
    private String purpose;
    private Integer maxMember;

public Teams toEntity() {
    return Teams.builder().teamId(teamId).teamName(teamName).purpose(purpose).maxMember(maxMember).build();
}
    
}

