package pro.sbs.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.sbs.domain.Activity;
import pro.sbs.domain.MyActivityList;
import pro.sbs.domain.Teams;
import pro.sbs.dto.ActivityCreateDto;
import pro.sbs.dto.ActivityInfoDto;
import pro.sbs.dto.ActivityReadDto;
import pro.sbs.dto.ActivityUpdateDto;
import pro.sbs.repository.ActivityRepository;
import pro.sbs.repository.MyActivityListRepository;
import pro.sbs.repository.TeamRepository;

@RequiredArgsConstructor
@Service
@Slf4j
public class ActivityService {

    private final ActivityRepository activityRepository;
    
    private final TeamRepository teamRepository;

    private final MyActivityListRepository myActivityListRepository;
    
    /**
     * 
     * @param loginUser
     * // loginUser를 자바스크립트에서 가져올 때, 마지막에 ('=')라는 글자가 추가되기 때문에
     * // 마지막 글자를 제거하기 위해 loginUser2라는 변수를 새로 생성함.
     * @return 해당 loginUser가 가입한 활동에 대한 정보들을 리스트로 가져옴.
     * @author 추지훈
     */
    public List<Activity> readByLoginUser(String loginUser) {
        String loginUser2 = loginUser.substring(0, loginUser.length() - 1);
        log.info("아이디 가져옴 {}", loginUser2);
        List<Activity> list = activityRepository.selectYourActivity(loginUser2);
        return list;
    }
    
    /**
     * 
     * @return
     */
    public List<Activity> findByCreatedTimeOrderByDesc() {
        log.info("createList", activityRepository.selectByCreateTimeOrderByDesc());
        return activityRepository.selectByCreateTimeOrderByDesc();
    }
    
    
    
    /**
     *  활동 아이디 내림차순으로 
     *  활동 목록 불러오는 기능.
     */
    public List<Activity> read() {
        log.info("activityService read()");
        
        return activityRepository.findByOrderByActivityIdDesc();
    }
    
    /**
     * 종속시 
     * @param teamId
     * @return
     */
    @Transactional(readOnly = true)
    public List<ActivityReadDto> read(Integer teamId) {
        log.info("postService read()");
        
        List<Activity> list = activityRepository.findByTeamIdOrderByActivityIdDesc(teamId);
        
        return list.stream().map(ActivityReadDto::fromEntity).toList();
    }
    
    /**
     *  저장된 활동 시작 시간 내림차순으로 
     *  모든 활동 목록을 불러오는 기능.
     */
    public List<Activity> readByStartTime() {
        log.info("activityService readByStartTime()");
        
        return activityRepository.findByOrderByStartTimeDesc();
    }
    
    
//    /**
//     *  캘린더에서 날짜 선택시 input text에 있는 text값을 읽어와
//     *  String 타입으로 날짜에 해당되는 활동들을 검색하는 기능. 
//     */
//    public List<Activity> scTimeRead(String startTime) {
//               
//        List<Activity> list = activityRepository.searchByStTime(startTime);
//                
//        log.info("scheduleTime={}",list);
//        
//        return list;
//    }
    
    
    /*  날짜가 지난 종료된 활동 목록 불러오는 기능.
    */
   public List<Activity> readAcTimePast(Integer teamId){

       return activityRepository.startTimeBypast(teamId);
   }

   /*  진행중인 활동 목록 불러오는 기능.
    */
   public ActivityInfoDto readAcTimeProgress(Integer teamId){
	 List<Activity> activity = activityRepository.startTimeByProgress(teamId);
	 List<MyActivityList> myActivityList = myActivityListRepository.ActivityJoinMemberInfo2(teamId);
     ActivityInfoDto dto = ActivityInfoDto.builder().activityList(activity)
             .myActivityList(myActivityList).build();
     return dto;
   }
    
    /**
     * 활동 아이디값을 받아 해당 아이디값의 활동정보를 받아오는 기능
     */
    public Activity readAcInfoByActivityId(Integer activityId){

        return activityRepository.findById(activityId).get();
    }

    /**
     * 
     * 
     * @param dto
     * @return
     */
    public Activity create(ActivityCreateDto dto) {
        log.info("create(dto={}", dto);
        
        Teams team = teamRepository.findById(dto.getTeamId()).get();
        log.info("create(team = {}", team);
        
        Activity activity = Activity.builder().teamId(dto.getTeamId())
                .play(dto.getPlay())
                .place(dto.getPlace())
                .budget(dto.getBudget())
                .userName(dto.getUserName())
                .startTime(dto.toEntity().getStartTime())
                .build();
        log.info("create(activity = {}", activity);
        
        Activity entity = activityRepository.save(activity);
        
        log.info("create(entity = {}", entity);
        
        return entity;
    }

    public Activity readIndex(Integer id) {
        
        Activity entity = activityRepository.findByActivityId(id).get();
        return entity;
    }

    public ActivityInfoDto myActivityListJoinMember(Integer activityId) {
        log.info("myActivityListJoinMember()");
        Activity activity = activityRepository.findById(activityId).get();
        List<MyActivityList> myActivityList = myActivityListRepository.ActivityJoinMemberInfo(activityId);
        ActivityInfoDto dto = ActivityInfoDto.builder().activityId(activity.getActivityId())
                .budget(activity.getBudget()).place(activity.getPlace()).play(activity.getPlay())
                .startTime(activity.getStartTime()).teamId(activity.getTeamId())
                .myActivityList(myActivityList).build();
        
        return dto;
    }
    
    /**
     *  캘린더에서 날짜 선택시 input text에 있는 text값을 읽어와
     *  String 타입으로 날짜에 해당되는 활동들을 검색하는 기능. 
     */
    public List<Activity> scTimeRead(String startTime,Integer teamId) {

        List<Activity> list = activityRepository.searchByStTime(startTime,teamId);

        log.info("scheduleTime={}",list);

        return list;
    }
    
    @Transactional
    public Integer update(ActivityUpdateDto dto) {
        log.info("update(dto={}", dto);
        
       Activity entity = activityRepository.findById(dto.getActivityId()).get();
       
       log.info("entity = {}",entity);
       
       entity.update(dto.getPlay(), dto.getPlace(), dto.getBudget(), dto.toEntity().getStartTime());

        return entity.getActivityId();
    }

    public Integer delete(Integer id) {
        log.info("delete(id={})", id);

        activityRepository.deleteById(id);

        return id;
    }


}
