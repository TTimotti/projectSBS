package pro.sbs.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.sbs.domain.TeamsLog;
import pro.sbs.dto.TeamsJoinDto;
import pro.sbs.repository.TeamsLogRepository;

/**
 * 유저가 가입하는 창에서 비밀번호를 맞게 입력한 경우, 
 * TeamsLog에 teamId, userName에 대한 정보를 기록하기 위해 생성한 Service.
 * @author 서범수
 *
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class TeamLogService {
    
    private final TeamsLogRepository teamsLogRepository;
    
    /**
     * 
     * @param dto
     * @return teamsLog 테이블에서 올바르게 가입이 되었는지 확인하기 위해 teamId를 가져와서 확인하기 위해 return값 부여.
     * @author 서범수
     */
    public Integer add(TeamsJoinDto dto) {
        TeamsLog teamLog = TeamsLog.builder().userName(dto.getUserName()).teamId(dto.getTeamId()).build();
        teamsLogRepository.save(teamLog);
        
        return teamLog.getTeamId();
    }



}
