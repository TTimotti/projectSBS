package pro.sbs.web;

import java.util.List;

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
import pro.sbs.domain.Teams;
import pro.sbs.dto.MyActivityListCreateDto;
import pro.sbs.service.ActivityService;
import pro.sbs.service.MyActivityListService;
import pro.sbs.service.PostService;
import pro.sbs.service.TeamService;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping
public class MyAcListController {

    private final PostService postService;

    private final TeamService teamService;

    private final ActivityService activityService;

    private final MyActivityListService myActivityListService;

    
    
//    @GetMapping("/myAcList/partyin")
//    public ResponseEntity<List<MyAcListReadDto>> partyin(@PathVariable Integer id) {
//        log.info("partyIn()");
//        
//        List<MyAcListReadDto> list = 
//        
//        return ResponseEntity.ok(id);
//    }

    
    @GetMapping("/myAcList/partyin")
    public void partyin(Integer id, Model model) {
        
        Teams team = teamService.readTeam(id);
        log.info("team = {}", team);
        model.addAttribute("team", team);
        
        Activity active = activityService.readIndex(id);
        log.info("active = {}", active);
        
        model.addAttribute("active", active);
        
//        ActivityReadDto activeDto = activityService.readIndex(id);
//        log.info("activeDto = {}", activeDto);
//        model.addAttribute("activeDto", activeDto);
        
  }
    
    
    @PostMapping("/myAcList/partyin")
    public String partyin(MyActivityListCreateDto dto, Integer id, RedirectAttributes attrs) {
        log.info("partyIn() dto = {}", dto);
        log.info("partyIn() id = {}", id);
        dto.setTeamId(id);
        //        dto.setUserId(userId);
        log.info("partyIn() dto2 = {}", dto);
        MyActivityList entity = myActivityListService.create(dto);
        log.info("partyIn() entity = {}", entity);
        // userId로 바꿔서 넣어야함.
        List<MyActivityList> mylist = myActivityListService.readByUserName(id);
        log.info("partyin mylist = {}", mylist);

        return "redirect:/team/teamActivity?teamId=" + id;
    }
}
