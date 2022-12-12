package pro.sbs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import pro.sbs.domain.Team;

// ??????????????????????????????????????????????????????????????????

@AllArgsConstructor
@Builder
@Getter
@ToString
public class TeamReadDto {
    private Integer teamId;

    private String teamName;

    private String teamPassword;

    private String teamLeader;

    private String purpose;

    private Integer categoryId;

    private Integer maxMember;
    
    public static TeamReadDto fromEntity(Team entity) {
        return TeamReadDto.builder().teamId(entity.getTeamId()).teamName(entity.getTeamName())
                .teamPassword(entity.getTeamPassword()).teamLeader(entity.getTeamLeader()).purpose(entity.getPurpose())
                .categoryId(entity.getCategoryId()).maxMember(entity.getMaxMember()).build();
}
}