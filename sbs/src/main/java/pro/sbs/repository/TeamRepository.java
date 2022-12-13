package pro.sbs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pro.sbs.domain.Team;
public interface TeamRepository extends JpaRepository<Team, Integer> {
    
    List<Team> findByOrderByTeamIdDesc();
    
    
    // Optional??
    Optional<Team> findByTeamName(String teamName);

    Optional<Team> findByTeamId(Integer teamId);

    // JPQL
    // 테이블 내 컬럼은 모두 JAVA 식으로 바꾸기.
    @Query("select t from TEAMS t where t.teamId in (select l.teamId from TEAMS_LOG l where l.userName = :userName)")
    List<Team> selectYourTeams(@Param(value = "userName") String loginUser2);

    @Query("select t from TEAMS t where lower(t.purpose) like lower('%' || :keyword || '%') order by t.teamId desc")
    List<Team> searchByKeyword(@Param(value = "keyword") String keyword);

    //select * from TEAMS where TEAM_ID in (select TEAM_ID from TEAMS_LOG where USER_NAME = 'tester02');
    
    
//    @Query(
//            "SELECT t.teamId, t.teamName, t.purpose, c.categoryName, t.teamMax FROM TEAMS t LEFT JOIN CATEGORYS c ON c.categoryId = t.categoryId"
//                )
//                List<TeamDto> findGetCategory();

                List<Team> findByCategoryIdOrderByCategoryIdDesc(Integer categoryId);

                @Query("select count(t.teamId) from TEAMS_LOG t where t.teamId = :teamId")
                Integer countById(@Param(value = "teamId") Integer teamId);
}
