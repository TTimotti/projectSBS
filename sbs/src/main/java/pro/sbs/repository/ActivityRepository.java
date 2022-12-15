package pro.sbs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pro.sbs.domain.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {

   List<Activity> findByOrderByActivityIdDesc();
    
   /**
    *  활동 시간으로 검색해서 활동 다불러오는거
    * @return
    */
    List<Activity> findByOrderByStartTimeDesc();
    
    /**
     * 활동아이디로 검색해서 팀아이디 가져오는거
     * @param teamId
     * @return
     */
    List<Activity> findByTeamIdOrderByActivityIdDesc(Integer teamId);
    
    /**
     * 캘린더에서 받은 날짜에 해당되는 활동 리스트를 보여주는 기능 
     * @param startTime
     * @return
     */
    @Query("select a from ACTIVITIES a where to_char(a.startTime, 'yyyy-MM-dd')"
    		+ " like :startTime order by startTime desc")
    List<Activity> searchByStTime(@Param(value = "startTime") String startTime);
    
    //현재 날짜 이전의 팀 활동기간 조회
    @Query("select a from ACTIVITIES a where to_char(a.startTime, 'yyyy-MM-dd') "
    		+ "< to_char(sysdate, 'yyyy-MM-dd') order by startTime desc")
    List<Activity> startTimeBypast();
    
    //진행중인 팀 활동 기간 조회
    @Query("select a from ACTIVITIES a where to_char(a.startTime, 'yyyy-MM-dd')"
    		+ " >= to_char(sysdate, 'yyyy-MM-dd') order by startTime")
    List<Activity> startTimeByProgress();
    
    /**
     * ActivityId로 해당 활동 정보 조회 
     * @return
     */
    @Query("select a from ACTIVITIES a where activityId like :activityId")
    List<Activity> ActivityInfo(@Param(value = "activityId") Integer activityId);
    
    
    /**
     * 팀 아이디로 불러오기
     * @return
     */
    List<Activity> findByOrderByTeamIdDesc();
}
