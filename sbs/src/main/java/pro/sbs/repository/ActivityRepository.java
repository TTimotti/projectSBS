package pro.sbs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pro.sbs.domain.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {

    
    Optional<Activity> findByActivityId(Integer activityId);
    
   List<Activity> findByOrderByActivityIdDesc();
    
    List<Activity> findByOrderByStartTimeDesc();
    
    List<Activity> findByTeamIdOrderByActivityIdDesc(Integer teamId);
    
    /**
     * 캘린더에서 받은 날짜에 해당되는 활동 리스트를 보여주는 기능 
     * @param startTime
     * @return
     */
    @Query("select a from ACTIVITIES a where teamId like :teamId and to_char(a.startTime, 'yyyy-MM-dd')"
    		+ " like :startTime order by startTime desc")
    List<Activity> searchByStTime(@Param(value = "startTime") String startTime,
    							  @Param(value = "teamId")Integer teamId);
    				
    
    //현재 날짜 이전의 팀 활동기간 조회
    @Query("select a from ACTIVITIES a where teamId like :teamId and to_char(a.startTime, 'yyyy-MM-dd') "
    		+ "< to_char(sysdate, 'yyyy-MM-dd') order by startTime desc")
    List<Activity> startTimeBypast(@Param(value = "teamId") Integer teamId);
    
    //진행중인 팀 활동 기간 조회
    @Query("select a from ACTIVITIES a where teamId like :teamId and to_char(a.startTime, 'yyyy-MM-dd')"
    		+ " >= to_char(sysdate, 'yyyy-MM-dd') order by startTime")
    List<Activity> startTimeByProgress(@Param(value = "teamId") Integer teamId);
    
    /**
     * ActivityId로 해당 활동 정보 조회 
     * @return
     */
    @Query("select a from ACTIVITIES a where activityId like :activityId")
    List<Activity> ActivityInfo(@Param(value = "activityId") Integer activityId);
    
    
    List<Activity> findByOrderByTeamIdDesc();
}
