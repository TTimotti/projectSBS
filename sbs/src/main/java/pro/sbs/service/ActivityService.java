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
import pro.sbs.dto.ActivityReadDto;
import pro.sbs.dto.MyActivityListCreateDto;
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
    
    
    /**
     *  캘린더에서 날짜 선택시 input text에 있는 text값을 읽어와
     *  String 타입으로 날짜에 해당되는 활동들을 검색하는 기능. 
     */
    public List<Activity> scTimeRead(String startTime) {
               
        List<Activity> list = activityRepository.searchByStTime(startTime);
                
        log.info("scheduleTime={}",list);
        
        return list;
    }
    
    
    /**
     *  날짜가 지난 종료된 활동 목록 불러오는 기능.
     */
    public List<Activity> readAcTimePast(){
        
        return activityRepository.startTimeBypast();
    }
    
    /**
     *  진행중인 활동 목록 불러오는 기능.
     */
    public List<Activity> readAcTimeProgress(){
        
        return activityRepository.startTimeByProgress();
    }
    
    /**
     * 활동 아이디값을 받아 해당 아이디값의 활동정보를 받아오는 기능
     */
    public List<Activity> readAcInfoByactivityId(Integer activityId){
    	
    	return activityRepository.ActivityInfo(activityId);
    }

    /**
     * 
     * 
     * @param dto
     * @return
     * @author 추
     */
    public Activity create(ActivityCreateDto dto) {
        log.info("create(dto={}", dto);
        
        Teams team = teamRepository.findById(dto.getTeamId()).get();
        log.info("create(team = {}", team);
        
        /**
         * 이거 액티비티 종속시키면 팀객체에서 가져오는 teamId를 넣어줘야함.
         */
//        Activity activity = Activity.builder().team(team)
        Activity activity = Activity.builder().teamId(dto.getTeamId())
                .play(dto.getPlay())
                .place(dto.getPlace())
                .build();
        log.info("create(activity = {}", activity);
        
        Activity entity = activityRepository.save(activity);
        
        log.info("create(entity = {}", entity);
        
        return entity;
    }

    public MyActivityList create(MyActivityListCreateDto dto) {

        MyActivityList myList = MyActivityList.builder().TeamId(dto.getTeamId())
                .activityId(dto.getActivityId())
                .userId(dto.getUserId())
                .build();
        log.info("create(myList) = {}", myList);
        
        MyActivityList entity = myActivityListRepository.save(myList);
        
        
        return entity;
    }


}
