package pro.sbs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pro.sbs.domain.Activity;
import pro.sbs.domain.Teams;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {

    
    Optional<Activity> findByActivityId(Integer activityId);
    
   List<Activity> findByOrderByActivityIdDesc();
    
    List<Activity> findByOrderByStartTimeDesc();
    
    List<Activity> findByTeamIdOrderByActivityIdDesc(Integer teamId);
    
    
    /**
     * 
     * @param loginUser2
     * @return loginUser가 가입한 활동들에 대한 정보들을 리스트로 가져옴.
     * @author 추
     */
    @Query("select t.activityId from ACTIVITIES t where t.activityId in (select l.activityId from MYACTIVITYLIST l where l.userName = :userName)")
    List<Activity> selectYourActivity(@Param(value = "userName") String loginUser2);
    
    
//    @Query("select a from ACTIVITIES a where teamId like :teamId and to_char(a.createdTime, 'yyyy-MM-dd') like :createdTime order by createdTime desc")
//    Activity findByLastModifiedTime(@Param(value = "createdTime") String createdTime,
//                                    @Param(value = "teamId")Integer teamId);
    
    @Query("select a from ACTIVITIES a order by createdTime desc")
    List<Activity> selectByCreateTimeOrderByDesc();
    
    
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
