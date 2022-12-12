package pro.sbs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pro.sbs.domain.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {

   List<Activity> findByOrderByActivityIdDesc();
    
    List<Activity> findByOrderByStartTimeDesc();
        
    @Query("select a from ACTIVITIES a where to_char(a.startTime, 'yyyy-MM-dd') like :startTime order by startTime desc")
    List<Activity> searchByStTime(@Param(value = "startTime") String startTime);
    
    //현재 날짜 이전의 팀 활동기간 조회
    @Query("select a from ACTIVITIES a where to_char(a.startTime, 'yyyy-MM-dd') < to_char(sysdate, 'yyyy-MM-dd') order by startTime desc")
    List<Activity> startTimeBypast();
    //진행중인 팀 활동 기간 조회
    @Query("select a from ACTIVITIES a where to_char(a.startTime, 'yyyy-MM-dd') >= to_char(sysdate, 'yyyy-MM-dd') order by startTime")
    List<Activity> startTimeByProgress();
    
    List<Activity> findByOrderByTeamIdDesc();
}
