package pro.sbs.service;

import java.util.List;

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

    /**
     * 팀 리더가, 팀에 가입된 멤버들을 탈퇴시키는 기능.
     * @param checkedMember 체크박스에 클릭되어 있는 멤버들(복수 선택 가능)
     * @param teamId 멤버들을 탈퇴시키고자 하는 팀의 번호(teamId).
     * @return 탈퇴된 멤버들의 수.
     * @author 서범수
     */
    public Integer kickOutMembers(List<String> checkedMember, Integer teamId) {
        Integer kickOutMembers = teamsLogRepository.deleteByUsername(checkedMember, teamId);
        return kickOutMembers;
    }

    /**
     * 팀 폐쇄하기
     * @param teamId 삭제할 팀의 번호(teamId).
     * @author 서범수
     */
    public void deleteByTeamId(Integer teamId) {
        log.info("deleteByTeamId(teamId={}) 호출", teamId);
        
        teamsLogRepository.deleteByTeamId(teamId);
        
    }
    
    /**
     * 사용자가 가입한 모임 탈퇴하는 기능
     * @param userName
     * @param teamId
     * @author 서범수
     */
    public void deleteJoinedTeam(String userName, Integer teamId) {
        log.info("deleteJoinedTeam(userName={}, teamId={}) 호출", userName, teamId);
        
        teamsLogRepository.deleteByUsername(userName, teamId);
        
    }



}
