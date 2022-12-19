package pro.sbs.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.sbs.domain.Activity;
import pro.sbs.domain.MyActivityList;
import pro.sbs.dto.ActivityCreateDto;
import pro.sbs.dto.MyActivityListCreateDto;
import pro.sbs.service.ActivityService;
import pro.sbs.service.PostService;
import pro.sbs.service.TeamService;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping
public class ActivityController {

	private final PostService postService;
	
    private final TeamService teamService;
    
    private final ActivityService activityService;
    
    @GetMapping("/activity/partyIn")
    public void partyIn (Integer id, Integer userId, Integer activityd, Model model) {
        log.info("partyIn()");
        
    }
    
    @PostMapping("/activity/partyIn")
    public String partyIn(MyActivityListCreateDto dto, Integer id, Integer activityId, Integer userId, RedirectAttributes attrs) {
        log.info("partyIn() dto = {}", dto);
        dto.setTeamId(id);
        dto.setActivityId(activityId);
        dto.setUserId(userId);
        log.info("partyIn() dto2 = {}", dto);
        MyActivityList entity = activityService.create(dto);
        log.info("partyIn() entity = {}", entity);
        attrs.addFlashAttribute("attrs entity.getMyListId() ={} ", entity.getMyListId());
        
        return "redirect:/team/teamActivity?teamId=" + id;
    }
    
    @GetMapping("/activity/create")
    public void create (Integer id, Model model) {
        log.info("ActivityController create() teamId = {}", id);
        
    }
    
    @PostMapping("/activity/create")
    public String create(ActivityCreateDto dto, RedirectAttributes attrs, Integer id) {
        log.info("ActivityController create() dto = {}", dto);
        log.info("ActivityController create() teamId = {}", id);
        dto.setTeamId(id);
        log.info("ActivityController create() dto2 = {}", dto);
        Activity entity = activityService.create(dto);
        log.info("ActivityController create() entity = {}", entity);
        attrs.addFlashAttribute("createdId", entity.getActivityId());
        
        
        return "redirect:/team/teamActivity?teamId=" + id;
    }
    
    
    
    @PostMapping("/team/list") // 요청 URL/방식 매핑.
    public ResponseEntity<List<Activity>> home2( Model model) {
        log.info("home()");

        // 방목록 전체리스트
        List<Activity> list = activityService.readByStartTime();
        // 전체리스트 
        model.addAttribute("list",list); // 뷰에 전달하는 모델 데이터.
        
        return ResponseEntity.ok(list); 
        // View 이름. -> src.main.resources/templates/파일이름.html
    }
    
    /**
     *  날짜가 지난 종료된 활동 목록 불러오는 기능.
     */
    @GetMapping("/team/pastList") // 요청 URL/방식 매핑.
    public ResponseEntity<List<Activity>> home3(Model model) {
     // 현재 이전 활동 내역 조회
        List<Activity> list1 = activityService.readAcTimePast();
        model.addAttribute("pastList",list1);
        return ResponseEntity.ok(list1);
    }
    
    /**
     *  진행중인 활동 목록 불러오는 기능.
     */
    @GetMapping("/team/progressList") // 요청 URL/방식 매핑.
    public ResponseEntity<List<Activity>> home4(Model model) {
     // 기간이 지나지 않은 활동 내역 조회
        List<Activity> list2 = activityService.readAcTimeProgress();
        model.addAttribute("progressList",list2);
        return ResponseEntity.ok(list2);
    }
    
    /**
     *  캘린더에서 날짜 선택시 input text에 있는 text값을 읽어와
     *  String 타입으로 날짜에 해당되는 활동들을 검색하는 기능. 
     */
    @GetMapping("/scTimeSearch")
    public ResponseEntity<List<Activity>> scTimeSearch(String startTime) {

        log.info("scTimeSearch()");

        log.info("scheduleTime={}", startTime);
      
        List<Activity> list = new ArrayList<>();
        
        list = activityService.scTimeRead(startTime);

        return ResponseEntity.ok(list);
    }
    
    /**
     *  활동 아이디를 테이블에서 받아 해당 아이디의 
     *  활동 정보를 받아서 팝업 레이어에 전달해주는 기능.
     */
    @GetMapping("/activityInfo")
    public void readAcInfoById(Integer activityId, Model model ) {
    	
    	log.info("readAcInfoById()");
    	log.info("activityId={}", activityId);
    	List<Activity> info = activityService.readAcInfoByactivityId(activityId);
    	log.info("info={}",info);
    	model.addAttribute("info", info);
    } 
}

