package pro.sbs.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.sbs.domain.Activity;
import pro.sbs.domain.Team;
import pro.sbs.dto.PostReadDto;
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
    
    @GetMapping("/team/teamActivity")
    public void team(Integer id, Model model) {
        log.info("teamController main()");
        
        Team team = teamService.readTeam(id);
        
        model.addAttribute("team", team);
        
        List<PostReadDto> post = postService.read(id);
        
        model.addAttribute("post", post);
        
        List<Activity> active = activityService.read();
        
        model.addAttribute("active", active);
        
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
    
    @PostMapping("/team/pastList") // 요청 URL/방식 매핑.
    public ResponseEntity<List<Activity>> home3( Model model) {
        
     // 현재 이전 활동 내역 조회
        List<Activity> list = activityService.readAcTimePast();
        
        return ResponseEntity.ok(list);
    }
    @PostMapping("/team/ProgressList") // 요청 URL/방식 매핑.
    public ResponseEntity<List<Activity>> home4( Model model) {
        
     // 기간이 지나지 않은 활동 내역 조회
        List<Activity> list = activityService.readAcTimeProgress();
        
        return ResponseEntity.ok(list);
    }
    
    
    @GetMapping("/scTimeSearch")
    public ResponseEntity<List<Activity>> scTimeSearch(String startTime) {

        log.info("scTimeSearch()");

        log.info("scheduleTime={}", startTime);
//      Date a = Date.valueOf(scheduleTime);        
        List<Activity> list = new ArrayList<>();
        
        list = activityService.scTimeRead(startTime);
        
        
        log.info("finallist ={}", list.toString());
    

        return ResponseEntity.ok(list);
    }
    
    
    
//    @GetMapping("/search")
//    public String search(String keyword, Model model) {
//        log.info("search(keyword={})", keyword);
//        
//        List<Team> list = postService.search(keyword);
//        model.addAttribute("list", list);
//        
//        return "/team/list";
//    }
    
    
    // MIn
//    @PostMapping("/team/list") // 요청 URL/방식 매핑.
//    public ResponseEntity<List<Activity>> home2(Model model) {
//        log.info("home()");
//        
//        // 방목록 전체리스트
//        List<Activity> list = activityService.read(); // DB에서 포스트 목록 전체 검색.
//
//        // 전체리스트 
//        model.addAttribute("list", list); // 뷰에 전달하는 모델 데이터.
//        
//        return ResponseEntity.ok(list); 
//        // View 이름. -> src.main.resources/templates/파일이름.html
//    }
//    
//    
//    @GetMapping("/scTimeSearch")
//    public ResponseEntity<List<Activity>> scTimeSearch(String startTime) {
//
//        log.info("scTimeSearch()");
//
//        log.info("scheduleTime={}", startTime);
////      Date a = Date.valueOf(scheduleTime);        
//        List<Activity> list = new ArrayList<>();
//        
//        list = activityService.scTimeRead(startTime);
//        
//        log.info("finallist ={}", list.toString());
//    
//
//        return ResponseEntity.ok(list);
//    }
    
    
}

