package pro.sbs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.sbs.domain.MyActivityList;
import pro.sbs.dto.MyActivityListCreateDto;
import pro.sbs.repository.MyActivityListRepository;

@RequiredArgsConstructor
@Service
@Slf4j
public class MyActivityListService {

    private final MyActivityListRepository myActivityListRepository;
    
    public List<MyActivityList> read() {
        List<MyActivityList> list = myActivityListRepository.findAll();
        
        log.info("MyActivityList All ={} ", list);
        return myActivityListRepository.findAll();
    }
    
    public MyActivityList create(MyActivityListCreateDto dto) {
        

        MyActivityList myList = MyActivityList.builder().teamId(dto.getTeamId())
                .activityId(dto.getActivityId())
                .userName(dto.getUserName())
                .nickName(dto.getNickname())
                .build();
        log.info("create(myList) = {}", myList);
        
        MyActivityList entity = myActivityListRepository.save(myList);
        
        
        return entity;
    }
    
    public MyActivityList createActivityCreate(Integer teamId, Integer activityId, String userName, String nickName) {
        

        MyActivityList myList = MyActivityList.builder().teamId(teamId)
                .activityId(activityId)
                .userName(userName)
                .nickName(nickName)
                .build();
        log.info("create(myList) = {}", myList);
        
        MyActivityList entity = myActivityListRepository.save(myList);
        
        
        return entity;
    }

    public String readByUserName(String userName) {
        log.info("readByUserName dto = {}", userName);
        
        
        String mylist = myActivityListRepository.findByNickNameByUserName(userName);
        log.info("readByUserId mylist = {}", mylist);
        
        
        return mylist;
    }

    public Integer delete(Integer id) {
        log.info("delete(id = {} )", id);
        
        myActivityListRepository.deleteById(id);
        
        return id;
    }

    public List<MyActivityList> readIndex(Integer teamId) {
        log.info("readIndex teamId= {}", teamId);
        
        return null;
    }
}
