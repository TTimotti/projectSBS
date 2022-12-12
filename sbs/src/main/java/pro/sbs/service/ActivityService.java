package pro.sbs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.sbs.domain.Activity;
import pro.sbs.repository.ActivityRepository;

@RequiredArgsConstructor
@Service
@Slf4j
public class ActivityService {

    private final ActivityRepository activityRepository;
    
    
    public List<Activity> read() {
        log.info("activityService read()");
        
        return activityRepository.findByOrderByActivityIdDesc();
    }
    
    public List<Activity> readByStartTime() {
        log.info("activityService readByStartTime()");
        
        return activityRepository.findByOrderByStartTimeDesc();
    }
    
    public List<Activity> scTimeRead(String startTime) {
        
        
//      Date date = Date.valueOf(scheduleTime);
                
        List<Activity> list = activityRepository.searchByStTime(startTime);
                
        log.info("scheduleTime={}",list);
        
        return list;
    }
    
    public List<Activity> readAcTimePast(){
        
        return activityRepository.startTimeBypast();
    }
    
    public List<Activity> readAcTimeProgress(){
        
        return activityRepository.startTimeByProgress();
    }
    
//    public Page<Activity> getlist(int page) {
//      
//      Pageable pageable = PageRequest.of(page, 10);
//      return this.activityRepository.findAll(pageable);
//      
//    }
}
