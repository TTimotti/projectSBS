package pro.sbs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pro.sbs.domain.MyActivityList;

public interface MyActivityListRepository extends JpaRepository<MyActivityList, Integer> {

    List<MyActivityList> findByOrderByUserNameDesc();
    
    List<MyActivityList> findByUserName(Integer userId);
    
    /**
     * 활동에 참여한 유저 목록 출력 기능
     * @param activityId
     * @return
     */
    @Query("select a from MYACTIVITYLIST a where activityId like :activityId")
    List<MyActivityList> ActivityJoinMemberInfo(@Param(value = "activityId") Integer activityId);
    
    
}
