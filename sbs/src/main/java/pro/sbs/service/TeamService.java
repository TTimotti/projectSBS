package pro.sbs.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.sbs.domain.Teams;
import pro.sbs.repository.TeamRepository;

@Service
@Slf4j
@RequiredArgsConstructor 
public class TeamService {
    
private final TeamRepository teamRepository;
    
    @Transactional(readOnly = true) // 검색 속도가 빨라짐.
    public List<Teams> read() {
        log.info("read() 호출");
        
        return teamRepository.findByOrderByTeamIdDesc();
    }

}
