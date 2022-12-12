package pro.sbs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pro.sbs.domain.TeamLog;

public interface TeamLogRepository extends JpaRepository<TeamLog, Integer>{
    
    List<TeamLog> findByUserName(String loginUser2);

    
    


}
