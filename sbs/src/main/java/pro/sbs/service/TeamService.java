package pro.sbs.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.sbs.domain.Team;
import pro.sbs.dto.TeamRegisterDto;
import pro.sbs.repository.TeamRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    @Transactional(readOnly = true)
    public List<Team> read() {
        log.info("실험중");
        return teamRepository.findAll();
    }

    @Transactional
    public void registerTeam(TeamRegisterDto dto) {
        log.info("DB에 멤버 정보를 저장합니다. dto={}", dto);
        
        teamRepository.save(dto.toEntity());
    }
        
    @Transactional
    // dto 대신, 번호만 가져와도 될 듯?
    public Team readTeam(TeamRegisterDto dto) {
        
        Team entity = teamRepository.findByTeamName(dto.getTeamName()).get();
        // teamName 대신 번호는??
        
        return entity;
    }
    
    @Transactional
    // dto 대신, 번호만 가져와도 될 듯?
    public Team readTeam(Integer teamId) {
        
        Team entity = teamRepository.findByTeamId(teamId).get();
        
        
        
        return entity;
    }

    public List<Team> readTeam() {
        List<Team> list = teamRepository.findAll();
        log.info("sefs", list);
        
        return list;
    }
    
//    @Transactional
//    public Integer update(TeamUpdateDto dto) {
//        Team entity = teamRepository.findById(dto.getTeamId()).get();
//        log.info("ㅠㅠㅠㅠㅠㅠ {}", entity.getTeamId());
//        entity.update(dto.getTeamName(), dto.getMaxMember());
//        log.info("ㅠㅠㅠㅠㅠㅠ {}", entity.getTeamId());
//        return entity.getTeamId();
//    }
    
    public String checkTeamName(String teamName) {
        log.info("확인 중입니다... {}", teamName);
        
        Optional<Team> result = teamRepository.findByTeamName(teamName);
        
        if (result.isPresent()) {
            return "nok";
        } else {
            return "ok";
        }
    }

    public void deleteTeam(Integer teamId) {
        teamRepository.deleteById(teamId);
        
    }

    public List<Team> readByLoginUser(String loginUser) {
        String loginUser2 = loginUser.substring(0, loginUser.length() - 1);
        log.info("아이디 가져옴 {}", loginUser2);
        List<Team> list = teamRepository.selectYourTeams(loginUser2);
        return list;
    }

    public List<Team> search(String keyword) {
        log.info("search(keyword={})", keyword);
        
        List<Team> list = new ArrayList<>();
        
        list = teamRepository.searchByKeyword(keyword);
        
        return list;
    }

//    public List<Team> readByLoginUser(String loginUser) {
//        List<Team> list = teamRepository.selectAllByLoginUser(loginUser);
//        return list;
//    }
    
    @Transactional(readOnly = true) 
    public List<Team> readDesc() {
        log.info("read()");
        
        return teamRepository.findByOrderByTeamIdDesc();
    }

    public Integer countMembers(Integer teamId) {
        Integer members = teamRepository.countById(teamId);
        return members;
    }
    

//    @Transactional(readOnly = true) 
//    public List<TeamDto> readMain() {
//        log.info("teamRepository readCategory()");
//        
//        return teamRepository.findGetCategory(); // 여기 join 쿼리문
//    }
//    
//    public List<Team> search(String keyword) {
//        log.info("search(keyword={})", keyword);
//        
//        List<Team> list = new ArrayList<>();
//        
//        list = teamRepository.searchByKeyword(keyword);
//        
//        return list;
//    }

}
