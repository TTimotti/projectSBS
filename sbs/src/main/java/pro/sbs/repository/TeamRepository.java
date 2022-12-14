package pro.sbs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pro.sbs.domain.Teams;

public interface TeamRepository extends JpaRepository<Teams, Integer> {

    List<Teams> findByOrderByTeamIdDesc();

}
