package pro.sbs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamJoinDto {
    // 필드 이름들은 Ajax 요청에서 사용된 data 객체의 속성(property) 이름과 동일하게.
    private Integer teamId;
    private String userName;
}
