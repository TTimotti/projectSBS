package pro.sbs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pro.sbs.domain.Teams;

public interface TeamRepository extends JpaRepository<Teams, Integer> {

    List<Teams> findByOrderByTeamIdDesc(); // 모든 팀 검색
    
    Optional<Teams> findByTeamName(String teamName); // 단일 팀을 이름으로 검색

    Optional<Teams> findByTeamId(Integer teamId); // 단일 팀을 아이디로 검색

}
