package pro.sbs.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pro.sbs.domain.Teams;
import pro.sbs.domain.Users;

public interface TeamRepository extends JpaRepository<Teams, Integer> {

    List<Teams> findByOrderByTeamIdDesc(); // 모든 팀 검색
    
    Optional<Teams> findByTeamName(String teamName); // 단일 팀을 이름으로 검색

    /**
     * 
     * @param teamId
     * @return teamId와 일치하는 팀 정보를 가져옴.
     * @author 서범수
     */
    Optional<Teams> findByTeamId(Integer teamId); // 단일 팀을 아이디로 검색
    
    
    /**
     * 
     * @param teamId
     * @return 해당 teamId와 일치하는 팀의 현재 인원 수를 가져 옴.
     * @author 서범수
     */
    @Query("select count(t.teamId) from TEAMS_LOG t where t.teamId = :teamId")
    Integer countByTeamId(@Param(value = "teamId") Integer teamId);
    
    /**
     * 
     * @param loginUser2
     * @return loginUser가 가입한 팀들에 대한 정보들을 리스트로 가져옴.
     * @author 서범수
     */
    @Query("select t from TEAMS t where t.teamId in (select l.teamId from TEAMS_LOG l where l.userName = :userName)")
    List<Teams> selectYourTeams(@Param(value = "userName") String loginUser2);
    
    /**
     * 
     * @param keyword 사용자가 입력한 값
     * @return 사용자가 입력한 값을 DB에서 존재하는 팀들의 제목과 비교 후, 팀 정보들을 가져옴.
     * @author 서범수
     */
    @Query("select t from TEAMS t where lower(t.teamName) like lower('%' || :keyword || '%') or lower(t.teamId) like lower('%' || :keyword || '%') or lower(t.purpose) like lower('%' || :keyword || '%') order by t.teamId desc")
    List<Teams> searchByKeyword(@Param(value = "keyword") String keyword);

    /**
     * 
     * @param teamId
     * @return 팀에 가입된 멤버들의 리스트.
     * @author 서범수
     */
    @Query("select u from USERS u where u.userName in (select t.userName from TEAMS_LOG t where t.teamId = :teamId)")
    List<Users> selectJoinedMembers(@Param(value = "teamId") Integer teamId);

    /**
     * 
     * @param checkedMember 양도하고자 하는 팀의 멤버(단 한 명)
     * @param teamId
     * @return 업데이트에 성공하면 1.
     */
    @Modifying
    @Transactional
    @Query("update TEAMS t set t.leader = :checkedMember where t.teamId = :teamId")
    Integer updateByUsername(@Param(value = "checkedMember") List<String> checkedMember, @Param(value = "teamId") Integer teamId);

    /**
     * 
     * @param teamId 폐쇄하고자 하는 팀의 번호(teamId)
     */
    @Modifying
    @Transactional
    void deleteByTeamId(Integer teamId);


}
