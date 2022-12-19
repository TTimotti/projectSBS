package pro.sbs.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MyAcListReadDto {
    private Integer myListId;
//    private List<TeamReadDto> teamList;
    private List<ActivityReadDto> activeList;
//    private List<UserCreateDto> userList;
}
