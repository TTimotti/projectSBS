package pro.sbs.web;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.sbs.domain.Images;
import pro.sbs.domain.Teams;
import pro.sbs.dto.TeamsCreateDto;
import pro.sbs.service.ImagesService;
import pro.sbs.service.TeamService;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamsController {
    
    private final TeamService teamService;
    private final ImagesService imagesService;

    /**
     * 팀 생성 페이지로 이동 GET
     * @author 김지훈
     */
    @GetMapping("/teamCreate") 
    public void teamCreate() {
        log.info("teamCreate() 호출");

    }
    
    /**
     * 팀을 생성하고 팀 메인 페이지로 이동
     * @param dto
     * @param file
     * @return 리다이렉트 된 팀 상세페이지
     * @throws IOException
     * @author 김지훈
     */    
    @PostMapping("/teamCreate")
    public String createTeam(TeamsCreateDto dto, @RequestParam("teamImage") MultipartFile file) throws IOException {
        log.info("creatTeam(dto={}, file={}) 호출", dto, file);
        
        Integer fid = imagesService.saveTeamImage(file);
        
        dto.setFid(fid);
        
        Teams entity = teamService.createTeam(dto); // DB에 저장하는 서비스 호출

        return "redirect:/team/teamConfig?teamId=" + entity.getTeamId();
        
    }
    
    @GetMapping("/teamConfig") // TODO FIXME
    public void teamConfig(Model model, Integer teamId) throws IOException{
        log.info("teamConfig(model={}, teamId={}) 호출", model, teamId);
        
        Teams team = teamService.readTeam(teamId);        
        log.info("teamConfig.team = {}",team);        
        model.addAttribute("team", team);   
                
        List<Images> files = imagesService.read();        
        model.addAttribute("all",files);
        log.info("teamConfig.model = {}", model);
    }
}
