package pro.sbs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.sbs.domain.Team;
import pro.sbs.domain.TeamLog;
import pro.sbs.dto.TeamJoinDto;
import pro.sbs.repository.TeamLogRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class TeamLogService {
    
    private final TeamLogRepository teamLogRepository;
    
    
    public Integer add(TeamJoinDto dto) {
        TeamLog teamLog = TeamLog.builder().userName(dto.getUserName()).teamId(dto.getTeamId()).build();
        teamLogRepository.save(teamLog);
        
        return teamLog.getTeamId();
    }


    public List<TeamLog> readByLoginUser(String loginUser) {
        log.info("아이디 가져옴 {}", loginUser);
        String loginUser2 = loginUser.substring(0, loginUser.length() - 1);
        log.info("아이디 가져옴 {}", loginUser2);
        List<TeamLog> list = teamLogRepository.findByUserName(loginUser2);
        return list;
    }

}
