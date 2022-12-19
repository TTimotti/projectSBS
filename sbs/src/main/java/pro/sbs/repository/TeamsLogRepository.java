package pro.sbs.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pro.sbs.domain.TeamsLog;

public interface TeamsLogRepository extends JpaRepository<TeamsLog, Integer>{
    
    List<TeamsLog> findByUserName(String loginUser2);

    /**
     * 
     * @param checkedMember 체크박스에 클릭된 멤버들을 TEAMS_LOG에서 삭제하는 기능.
     * @param teamId
     * @return 삭제된 멤버들의 수.
     */
    @Modifying
    @Transactional
    @Query("delete from TEAMS_LOG t where t.userName in (:checkedMember) and t.teamId= :teamId")
    Integer deleteByUsername(@Param(value = "checkedMember") List<String> checkedMember, @Param(value = "teamId") Integer teamId);

    @Modifying
    @Transactional
    void deleteByTeamId(Integer teamId);

    
    


}
