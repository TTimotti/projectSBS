package pro.sbs.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import pro.sbs.domain.Teams;

/**
 * 팀 생성을 위한 DTO(수정중)
 * 
 * @author 김지훈
 *
 */
@NoArgsConstructor
@Data
public class TeamsCreateDto {

    private Integer teamId;
    private String teamName;
    private String password;
    private String leader;
    private String purpose;
    private Integer maxMember;
    private Integer fid;
    private Integer mid;

    public Teams toEntity() {
        return Teams.builder()
                .teamId(teamId)
                .teamName(teamName)
                .password(password)
                .leader(leader)
                .purpose(purpose)
                .maxMember(maxMember)
                .fid(fid)
                .build();
    }

}
