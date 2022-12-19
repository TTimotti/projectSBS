package pro.sbs.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.sbs.domain.Teams;
import pro.sbs.domain.Users;
import pro.sbs.dto.TeamsCreateDto;
import pro.sbs.dto.TeamsUpdateDto;
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
    
    /**
     * 
     * @param teamId
     * @return 해당 teamId와 일치하는 팀의 현재 인원 수를 가져 옴.
     * @author 서범수
     */
    public Integer countMembers(Integer teamId) {
        Integer members = teamRepository.countByTeamId(teamId);
        return members;
    }
    
    /**
     * 
     * @param loginUser
     * // loginUser를 자바스크립트에서 가져올 때, 마지막에 ('=')라는 글자가 추가되기 때문에
     * // 마지막 글자를 제거하기 위해 loginUser2라는 변수를 새로 생성함.
     * @return 해당 loginUser가 가입한 팀에 대한 정보들을 리스트로 가져옴.
     * @author 서범수
     */
    public List<Teams> readByLoginUser(String loginUser) {
        String loginUser2 = loginUser.substring(0, loginUser.length() - 1);
        log.info("아이디 가져옴 {}", loginUser2);
        List<Teams> list = teamRepository.selectYourTeams(loginUser2);
        return list;
    }
    
    /**
     * 
     * @param keyword 사용자가 입력한 값
     * @return 사용자가 입력한 값을 DB에서 존재하는 팀들의 제목과 비교 후, 팀 정보들을 가져옴.
     * @author 서범수
     */
    public List<Teams> search(String keyword) {
        log.info("search(keyword={})", keyword);
        
        List<Teams> list = new ArrayList<>();
        
        list = teamRepository.searchByKeyword(keyword);
        
        return list;
    }

    /**
     * 팀에 가입된 멤버들의 정보를 가져오는 기능
     * @param teamId 가입된 멤버들을 읽어오고자 하는 팀의 번호(teamId)
     * @return 팀에 가입된 멤버들의 리스트
     * @author 서범수
     */
    public List<Users> readJoinedMembers(Integer teamId) {
        List<Users> joinedMembers = teamRepository.selectJoinedMembers(teamId);
        
        return joinedMembers;
        
    }

    /**
     * 팀 이름 중복 방지를 위해, DB에서 팀 이름을 비교하는 메서드
     * 
     * @param teamName 사용자가 입력한 바꿀 팀 이름.
     * @return 사용자가 입력한 값이 가능한 것인지 여부 확인
     * @author 서범수
     */
    public String checkTeamName(String teamName) {
        log.info("checkTeamName(teamName={}) 호출", teamName);
        
        Optional<Teams> result = teamRepository.findByTeamName(teamName);
        
        if (result.isPresent()) {
            return "nok";
        } else {
            return "ok";
        }
    }

    /**
     * 팀에 대한 정보(팀 이름, 팀 소개, 최대 인원 수)를 업데이트 하는 기능.
     * @param dto 업데이트 할 정보들을 가지고 있는 DTO
     * @return 업데이트 된 팀의 번호.
     * @author 서범수
     */
    @Transactional
    public Integer updateTeam(TeamsUpdateDto dto) {
        log.info("updateTeam(dto={})", dto);
        
        Teams entity = teamRepository.findByTeamId(dto.getTeamId()).get();
        entity.update(dto.getTeamName(), dto.getPurpose(), dto.getMaxMember());
        
        return entity.getTeamId();
        
    }

    /**
     * 팀의 비밀번호를 바꾸는 기능
     * @param password 바꾸려는 비밀번호.
     * @param teamId 비밀번호를 바꾸고자 하느 팀의 번호(teamId).
     * @author 서범수.
     */
    @Transactional
    public void changeTeamPassword(String password, Integer teamId) {
        log.info("changeTeamPassword(password={}, teamId={})", password, teamId);
        
        Teams entity = teamRepository.findByTeamId(teamId).get();
        entity.changePassword(password);
    }

    /**
     * 팀의 리더를 다른 멤버에게 양도하는 기능.
     * @param checkedMember VIEW에 있는 체크박스가 클릭되어 있는 멤버(단 한 명)
     * @param teamId 양도하고자 하는 팀의 번호(teamId).
     * @return 양도에 성공했으면 1, 실패했으면 0. (혹시 몰라서 만들었습니다...)
     * @author 서범수
     */
    public Integer handOverLeader(List<String> checkedMember, Integer teamId) {
        Integer handOverLeader = teamRepository.updateByUsername(checkedMember, teamId);
        return handOverLeader;
    }

    /**
     * 팀 폐쇄하기
     * @param teamId 삭제할 팀의 번호(teamId).
     * @author 서범수
     */
    public void deleteTeam(Integer teamId) {
        log.info("deleteTeam(teamId={}) 호출", teamId);
        
        teamRepository.deleteByTeamId(teamId);
        
    }



    
}
