package pro.sbs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 유저가 가입하는 창에서 비밀번호를 맞게 입력한 경우, 
 * TeamsLog에 teamId, userName에 대한 정보를 기록하기 위해 생성한 DTO.
 * @author 서범수
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamsJoinDto {
    // 필드 이름들은 Ajax 요청에서 사용된 data 객체의 속성(property) 이름과 동일하게.
    private Integer teamId;
    private String userName;
}
