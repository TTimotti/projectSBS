package pro.sbs.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.sbs.domain.Teams;
import pro.sbs.dto.TeamsCreateDto;
import pro.sbs.repository.TeamRepository;

@Service
@Slf4j
@RequiredArgsConstructor 
public class TeamService {
    
private final TeamRepository teamRepository;
    
    /**
     * 모든 팀들 검색
     * @return 찾은 팀들 리스트
     * @author 김지훈
     */
    @Transactional(readOnly = true) // 검색 속도가 빨라짐.
    public List<Teams> read() {
        log.info("read() 호출");
        
        return teamRepository.findByOrderByTeamIdDesc();
    }
    
    /**
     * DTO 정보로 Teams 객체 하나 찾는 메서드
     * @param dto
     * @return 찾은 Teams 엔터티 하나
     * @author 김지훈
     */
    @Transactional(readOnly = true)
    public Teams readTeam(TeamsCreateDto dto) {
        log.info("readTeam(dto = {}) 호출", dto);
        
        Teams entity = teamRepository.findByTeamId(dto.getTeamId()).get();
        
        return entity;
        
    }
    
    /**
     * teamId에 해당하는 Teams 찾는 메서드
     * @param teamId
     * @return 찾은 Teams 엔터티 하나
     * @author 김지훈
     */
    @Transactional(readOnly = true)
    public Teams readTeam(Integer teamId) {
        log.info("readTeam(teamId = {}) 호출", teamId);
        
        Teams entity = teamRepository.findByTeamId(teamId).get();
        
        return entity;
    }
    
    /**
     * DTO로 생성한 엔터티를 DB에 저장 후
     * @param dto
     * @return 생성한 Teams 객체
     * @author 김지훈
     */
    public Teams createTeam(TeamsCreateDto dto) {
        log.info("registerTeam(dto = {}) 호출", dto);

        Teams entity = teamRepository.save(dto.toEntity());   
        
        return entity;
    }



    
}
