package pro.sbs.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pro.sbs.domain.Team;

@Getter
@Setter
@NoArgsConstructor
@Data
public class TeamRegisterDto {
    
    private String teamName;
    private String teamPassword;
    private String purpose;
    private Integer categoryId;
    private Integer maxMember;
    private String teamLeader;

    public Team toEntity() {
        return Team.builder().teamName(teamName).teamPassword(teamPassword).purpose(purpose).categoryId(categoryId).teamLeader(teamLeader).maxMember(maxMember).build();
    }

}
