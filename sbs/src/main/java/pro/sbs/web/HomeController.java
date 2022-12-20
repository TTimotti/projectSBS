package pro.sbs.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.sbs.domain.Teams;
import pro.sbs.service.TeamService;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    
    private final TeamService teamService;

    @GetMapping("/") 
    public String home(String keyword, Model model) {
        log.info("home() 호출");
        if(keyword!=null) {
            List<Teams> list = teamService.search(keyword);
            model.addAttribute("list", list);
        } else {
            List<Teams> list = teamService.read();
            model.addAttribute("list", list);
        }
        
        return "/home";
        

    }
}
