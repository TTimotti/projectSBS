package pro.sbs.web;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.sbs.domain.Images;
import pro.sbs.domain.Team;
import pro.sbs.dto.TeamJoinDto;
import pro.sbs.dto.TeamRegisterDto;
import pro.sbs.service.ImageService;
import pro.sbs.service.TeamLogService;
import pro.sbs.service.TeamService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class TeamController {
    
    private final TeamService teamService;
    private final ImageService imageService;
    private final TeamLogService teamLogService;
    
    private Integer fileId = 1;
    
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/team/teamCreate")
    public void create() {
        log.info("멤버 정보를 작성합니다.");
    }
    
    // @RequestParam("가져올 변수명") 타입 [JAVA에서 사용할 이름]
    @PostMapping("/team/teamCreate")
    public String create(TeamRegisterDto dto, @RequestParam("teamImage") MultipartFile file) throws IllegalStateException, IOException {
        log.info("작성한 정보를 가져옵니다. dto={}, img={}", dto);
        
        teamService.registerTeam(dto);
        Team entity = teamService.readTeam(dto);
        TeamJoinDto teamJoinDto = new TeamJoinDto(entity.getTeamId(), entity.getTeamLeader());
        teamLogService.add(teamJoinDto);
        imageService.saveFile(file, entity);
        
        return "redirect:/";
    }
// -------------------------------------------------------------------------------------------- //
    @GetMapping("/post/showImage")
    public Resource showImage(Model model) throws MalformedURLException {
        log.info("사진 정보 연습하기");
        Images files = imageService.readFile(fileId);
        log.info("사진을 가져왔는지 확인합니다. {}", files);
        model.addAttribute("image", files);

        
        return new UrlResource("file:" + files.getFileUrl());
        
    }
    

// -------------------------------------------------------------------------------------------- //
    
    @GetMapping({"/team/teamInfo", "/team/teamPhoto", "/team/teamMember", "/team/teamManage", "/team/teamPost"})
    public void settingPage(Model model, Integer teamId) {
        log.info("잘 클릭했는지 확인합니다. {}", teamId);
        Team team = teamService.readTeam(teamId);
        log.info("모임을 가져왔는지 확인합니다. {}", team);
        
        model.addAttribute("team", team);
        
        log.info("관리자 페이지로 이동합니다..");
    }
    
//    @PostMapping("team/teamInfo")
//    public String teamUpdate(TeamUpdateDto dto) {
//        log.info("모임을 변경합니다... 모임번호 = {}", dto.getTeamId());
//        
//         FIXME
//        Integer teamId = teamService.update(dto);
//        log.info("번호를 가져왔을까요? {}", teamId);
////      포스트 수정 성공 후에는 상세 페이지로 이동(redirect)
//        return "redirect:/team/teamInfo?teamId=" + teamId;
//        
//}
            
    @GetMapping("/team/teamList")
    public String teamList(Model model) {
        log.info("모임 목록으로 이동합니다...");
        List<Team> list = teamService.readTeam();
        model.addAttribute("teams", list);
        return "/team/teamList";
    }
    
    @GetMapping("/team/checkid")
    @ResponseBody //
    public ResponseEntity<String> checkTeamName(String teamName) {
        log.info("팀 이름을 확인 중입니다... {}", teamName);
        
        String result = teamService.checkTeamName(teamName);
        
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/team/delete")
    public String deleteTeam(Integer teamId) {
        log.info("모임 삭제할 준비를 합니다. {}번.", teamId);
        teamService.deleteTeam(teamId);
        return "redirect:/";
    }
    
    @GetMapping("/team/search")
    public String search(String keyword, Model model) {
        log.info("검색 키워드 = {})", keyword);
        
        List<Team> list = teamService.search(keyword);
        log.info("갖고 왔나요? {}", list);
        model.addAttribute("list", list);
        
        return "/home";
    }
    
    @GetMapping("/team/teamSearch")
    public void teamSearch(Model model) {
        log.info("검색 페이지로 이동합니다...");
        List<Team> list = teamService.readTeam();
        model.addAttribute("teams", list);
    }
    
    @GetMapping("/team/join/{teamId}")
    @ResponseBody
    public ResponseEntity<Team> readTeamByTeamId(@PathVariable Integer teamId) {
        log.info("가입 창을 확인하기 위한 준비중...{}", teamId);
        Team team = teamService.readTeam(teamId);
        log.info("{}", team);
        
        return ResponseEntity.ok(team);
    }
    
    @GetMapping("/team/countMembers/{teamId}")
    @ResponseBody
    public ResponseEntity<Integer> countMembers(@PathVariable Integer teamId) {
        log.info("해당 팀의 번호는... {}", teamId);
        Integer members = teamService.countMembers(teamId);
        log.info("멤버 수는 ... {}", members);
        
        return ResponseEntity.ok(members);
    }
}



