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

    
    

    
    @GetMapping("/myAcList/partyin")
    public void partyin(Integer id, Model model) {
        
        Activity active = activityService.readIndex(id);
        log.info("active = {}", active);
        
        model.addAttribute("active", active);
        
  }
    
    
    @PostMapping("/myAcList/partyin")
    public String partyin(MyActivityListCreateDto dto, RedirectAttributes attrs) {
//        Teams team = teamService.readTeam(id);
//        log.info("team = {}", team);
//        Activity active = activityService.readIndex(id);
//        log.info("active = {}", active);
//        dto.setTeamId(team.getTeamId());
//        dto.setActivityId(active.getActivityId());
        
        log.info("partyIn() dto = {}", dto);
        String uName = dto.getUserName();
        String mylist = myActivityListService.readByUserName(uName);
        log.info("partyin mylist = {}", mylist);
        dto.setNickname(mylist);
        log.info("partyIn() dto2 = {}", dto);
        MyActivityList entity = myActivityListService.create(dto);
        log.info("partyIn() entity = {}", entity);
        
        log.info("partyIn() entity = {}", dto.getTeamId());
        

        return "redirect:/team/teamActivity?teamId=" + dto.getTeamId();
    }
    
    @PostMapping("/myAcList/delete")
    public String delete(Integer id, RedirectAttributes attrs) {
        log.info("delete(id={})", id);
        Integer myActListId = myActivityListService.delete(id);
        attrs.addFlashAttribute("myActListId", myActListId);
        log.info("delete mylistId = {})", myActListId);
        
        return "redirect:/";
    }
}
