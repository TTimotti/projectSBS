package pro.sbs.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyTeamListDto {
    private Integer teamId;
    private String teamName;
    private LocalDateTime createdTime;
    

}
