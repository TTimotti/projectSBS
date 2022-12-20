package pro.sbs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pro.sbs.domain.Users;
import pro.sbs.dto.MyTeamListDto;

public interface UsersRepository extends JpaRepository<Users, Integer> {

    List<Users> findByOrderByUserIdDesc();

    @EntityGraph(attributePaths = "roles")
    Optional<Users> findByUserName(String userName);

    Optional<Users> findByUserId(Integer userId);
    
    /**
     * 유저가 가입한 팀 목록을 불러오는 기능
     * @param userName 로그인한 유저
     * @author 서범수
     */
    @Query("select new pro.sbs.dto.MyTeamListDto(t.teamId, t.teamName, tl.createdTime, t.leader) from TEAMS_LOG tl, TEAMS t where tl.userName = :userName and tl.teamId = t.teamId")
    List<MyTeamListDto> selectTeamsLogByUserName(@Param(value = "userName") String userName);
    
    

}
