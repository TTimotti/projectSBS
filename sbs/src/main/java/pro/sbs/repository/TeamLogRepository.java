package pro.sbs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pro.sbs.domain.Team;
import pro.sbs.domain.TeamLog;
import pro.sbs.domain.User;

public interface TeamLogRepository extends JpaRepository<TeamLog, Integer>{
    
    List<TeamLog> findByUserName(String loginUser2);

    
    


}
