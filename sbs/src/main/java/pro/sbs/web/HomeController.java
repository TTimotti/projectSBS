package pro.sbs.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.sbs.domain.Team;
import pro.sbs.domain.TeamLog;
import pro.sbs.dto.TeamJoinDto;
import pro.sbs.service.TeamLogService;
import pro.sbs.service.TeamService;

@Slf4j
@RequiredArgsConstructor
@Controller
public class HomeController {
    
    private final TeamService teamService;
    private final TeamLogService teamLogService;
    
    @GetMapping("/")
    public String home(Model model) {
        log.info("SBS 실행준비 중입니다...");
        List<Team> teams = teamService.read();
        model.addAttribute("teams", teams);
        log.info("test = {} ", teams);
        return "/home";
    }
    

    
    @GetMapping("/home/all")
    @ResponseBody
    public ResponseEntity<List<Team>> readAllTeams() {
        log.info("ㅇㄴㅁㅇㄴㅇㄴㅁ");
        List<Team> list = teamService.readTeam();
        return ResponseEntity.ok(list);
    }
    

    
    @PostMapping("/home/success")
    @ResponseBody
    public ResponseEntity<Integer> teamJoinSuccess(@RequestBody TeamJoinDto dto) {
        log.info("가입 중... {}", dto);
        
        Integer history = teamLogService.add(dto);

        return ResponseEntity.ok(history);
        
    }
}
