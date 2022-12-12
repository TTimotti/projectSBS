package pro.sbs.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import pro.sbs.domain.Team;

@NoArgsConstructor
@Data
public class TeamUpdateDto {
    private Integer teamId;
    private String teamName;
    private Integer maxMember;

public Team toEntity() {
    return Team.builder().teamId(teamId).teamName(teamName).maxMember(maxMember).build();
}
    
}

