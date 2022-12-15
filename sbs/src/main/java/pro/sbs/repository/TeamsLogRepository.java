package pro.sbs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pro.sbs.domain.TeamsLog;

public interface TeamsLogRepository extends JpaRepository<TeamsLog, Integer>{
    
    List<TeamsLog> findByUserName(String loginUser2);

    
    


}
