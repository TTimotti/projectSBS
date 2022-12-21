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
        return myActivityListRepository.findAll();
    }
    
    public MyActivityList create(MyActivityListCreateDto dto) {
        

        MyActivityList myList = MyActivityList.builder().TeamId(dto.getTeamId())
                .activityId(dto.getActivityId())
                .userName(dto.getUserName())
                .nickName(dto.getNickname())
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
}
