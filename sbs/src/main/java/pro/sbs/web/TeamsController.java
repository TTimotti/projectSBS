package pro.sbs.web;


import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.sbs.domain.Activity;
import pro.sbs.domain.Images;
import pro.sbs.domain.Teams;
import pro.sbs.domain.Users;
import pro.sbs.dto.PostReadDto;
import pro.sbs.dto.TeamsCreateDto;
import pro.sbs.dto.TeamsJoinDto;
import pro.sbs.dto.TeamsUpdateDto;
import pro.sbs.service.ActivityService;
import pro.sbs.service.ImagesService;
import pro.sbs.service.PostService;
import pro.sbs.service.TeamLogService;
import pro.sbs.service.TeamService;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamsController {
    
    private final TeamService teamService;
    private final ImagesService imagesService;
    private final TeamLogService teamLogService;
    private final PostService postService;
    private final ActivityService activityService;

    /**
     * 팀 생성 페이지로 이동 GET
     * @author 김지훈
     * @author 서범수
     */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")    
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
     * @author 김지훈, 서범수
     * 수정사항: team 생성 시, leader도 teamsLog에 저장되도록 수정.
     */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/teamCreate")
    public String createTeam(TeamsCreateDto dto, @RequestParam("teamImage") MultipartFile file) throws IOException {
        log.info("creatTeam(dto={}, file={}) 호출", dto, file);
        
        Integer fid = imagesService.saveTeamImage(file);
        dto.setFid(fid);
        
        Teams entity = teamService.createTeam(dto); // DB에 저장하는 서비스 호출
 
        // 수정사항 (by 서범수)
        TeamsJoinDto teamJoinDto = new TeamsJoinDto(entity.getTeamId(), entity.getLeader());
        teamLogService.add(teamJoinDto);
        
        return "redirect:/team/teamConfig?teamId=" + entity.getTeamId();
        
    }
    
    /**
     * 팀 리더가 관리자 페이지로 들어갈 때, 팀에 대한 정보를 불러오는 기능
     * @param model
     * @param teamId 관리자 페이지로 이동하고자 하는 팀의 번호(teamId)
     * @throws IOException
     * @author 김지훈, 서범수.
     */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/teamConfig") 
    public void teamConfig(Model model, Integer teamId) throws IOException{
        log.info("teamConfig(model={}, teamId={}) 호출", model, teamId);
        
        Teams team = teamService.readTeam(teamId);        
        log.info("teamConfig.team = {}",team);        
        model.addAttribute("team", team);
        
        // 팀에 가입한 멤버들을 가져옵니다. (teamId, User, teamLog 이용)
        
        List<Users> joinedMembers = teamService.readJoinedMembers(teamId);
        model.addAttribute("joinedMembers", joinedMembers);
        
        List<Images> files = imagesService.read();        
        model.addAttribute("all",files);
        log.info("teamConfig.model = {}", model);
        
        
    }
    
    /**
     * 팀에 대한 정보(팀 이름, 팀 소개, 최대 인원 수)를 업데이트 하는 기능.
     * @param dto 업데이트 할 정보들을 가지고 있는 DTO
     * @return 팀 관리자 페이지로 리다이렉트.
     * @author 서범수
     */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/updateTeam")
    public String updateTeam(TeamsUpdateDto dto, @RequestParam("teamImage") MultipartFile file, Model model) throws IOException{
        log.info("updateTeam(dto={}, file={}) 호출", dto, file);
        
        Integer teamId = teamService.updateTeam(dto);
        
        imagesService.updateTeamImage(file, dto.getTeamId());
        
        return "redirect:/team/teamActivity?teamId=" + teamId;
    }
    
    /**
     * 
     * @param teamId
     * @return ajax 방식, teamId와 일치하는 팀 정보를 가져옴.
     * @author 서범수
     */
    @GetMapping("/join/{teamId}")
    @ResponseBody
    public ResponseEntity<Teams> readTeamByTeamId(@PathVariable Integer teamId) {
        log.info("readTeamByTeamId(teamId={}) 호출", teamId);
        Teams team = teamService.readTeam(teamId);
        log.info("{}", team);
        
        return ResponseEntity.ok(team);
    }
    
    /**
     * 
     * @param teamId
     * @return ajax 방식, 해당 teamId와 일치하는 팀의 현재 인원 수를 가져옴.
     * @author 서범수
     */
    @GetMapping("/countMembers/{teamId}")
    @ResponseBody
    public ResponseEntity<Integer> countMembers(@PathVariable Integer teamId) {
        log.info("countMembers(teamId={}) 호출", teamId);
        Integer members = teamService.countMembers(teamId);
        
        return ResponseEntity.ok(members);
    }
    
    /**
     * 
     * @param loginUser
     * @return ajax 방식, loginUser(=userName)가 가입한 모임들을 가져옴.
     * @author 서범수
     */
    @PostMapping("/readByLoginUser/")
    @ResponseBody
    public ResponseEntity<List<Teams>> readByLoginUser(@RequestBody String loginUser) {
        log.info("readByLoginUser(loginUser={}) 호출", loginUser);
        
        List<Teams> list = teamService.readByLoginUser(loginUser);
        
        return ResponseEntity.ok(list);
    }
    
    /**
     * 
     * @param dto
     * @return ajax 방식, loginUser가 모임에서 활동할 수 있도록 teamsLog에 (userName, teamID) 저장.
     * @author 서범수
     */
    @PostMapping("/success")
    @ResponseBody
    public ResponseEntity<Integer> teamJoinSuccess(@RequestBody TeamsJoinDto dto) {
        log.info("teamJoinSuccess(dto={}) 호출", dto);
        
        Integer history = teamLogService.add(dto);

        return ResponseEntity.ok(history);
        
    }
    
    /**
     * 
     * @param keyword 검색한 값.
     * @param model html로 보낼 데이터
     * @return 사용자가 입력한 값을 DB에서 존재하는 팀들의 제목과 비교 후, 팀 정보들을 가져옴.
     * @author 서범수
     */
    @GetMapping("/search")
    public String search(String keyword, Model model) {
        log.info("search(keyword={}) 호출", keyword);
        
        List<Teams> list = teamService.search(keyword);
        model.addAttribute("list", list);
        
        return "/home";
    }
    
    /**
     * 
     * @param teamId 팀 메인 페이지로 들어갈 팀의 번호.
     * @param model
     * @author 추지훈.
     * 팀 메인 페이지로 들어가는 기능. (ActivityController -> TeamsController로 옮김)
     */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/teamActivity")
    public void team(Integer teamId, Model model) {
        log.info("team()");
        log.info("teamId = {}", teamId);
        
        Teams team = teamService.readTeam(teamId);
        
        model.addAttribute("team", team);
        
        List<PostReadDto> post = postService.read(teamId);
        
        model.addAttribute("post", post);
        
        List<Activity> active = activityService.read();
        
        log.info("active = {}", active);
        
        model.addAttribute("active", active);
        
    }
    
    /**
     * 팀 이름 중복 방지를 위해, DB에서 팀 이름을 비교하는 메서드
     * 
     * @param teamName 사용자가 입력한 
     * @return ajax방식, 사용자가 입력한 값이 가능한 것인지 여부 확인
     * @author 서범수
     */
    @GetMapping("/checkTeamId")
    @ResponseBody
    public ResponseEntity<String> checkTeamName(String teamName) {
        log.info("checkTeamName(teamName={}) 호출", teamName);
        
        String result = teamService.checkTeamName(teamName);
        
        return ResponseEntity.ok(result);
    }
    
    /**
     * 팀의 비밀번호를 바꾸는 기능
     * @param password 바꾸려는 비밀번호.
     * @param teamId 비밀번호를 바꾸고자 하느 팀의 번호(teamId).
     * @return 팀 관리자 페이지로 리다이렉트.
     * @author 서범수.
     */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/changeTeamPassword")
    public String changeTeamPassword(@RequestParam(value = "changePassword") String password, Integer teamId) {
        log.info("changeTeamPassword(password= {}, teamId = {}) 호출", password, teamId);
        
        teamService.changeTeamPassword(password, teamId);
        
        return "redirect:/team/teamConfig?teamId=" + teamId;
        
    }

    /**
     * 팀 리더가, 팀에 가입된 멤버들을 탈퇴시키는 기능.
     * @param checkedMember 체크박스에 클릭되어 있는 멤버들(복수 선택 가능)
     * @param teamId 멤버들을 탈퇴시키고자 하는 팀의 번호(teamId).
     * @return 탈퇴시키고 난 후, 팀 관리자 페이지로 리다이렉트.
     * @author 서범수
     */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/kickOutMembers")
    public String kickOutMembers(@RequestParam(value ="checkedMember[]") List<String> checkedMember, Integer teamId) {
        log.info("kickOutMembers(checkedMember={}, teamId={}) 호출", checkedMember, teamId);
        
        Integer kickOutMembers = teamLogService.kickOutMembers(checkedMember, teamId);
        
    return "redirect:/team/teamConfig?teamId=" + teamId;
    }
    
    /**
     * 팀의 리더를 다른 멤버에게 양도하는 기능.
     * @param checkedMember VIEW에 있는 체크박스가 클릭되어 있는 멤버(단 한 명)
     * @param teamId 양도하고자 하는 팀의 번호(teamId).
     * @return 팀 메인 페이지로 이동.
     * @author 서범수
     */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/handOverLeader")
    public String handOverLeader(@RequestParam(value ="checkedMember[]") List<String> checkedMember, Integer teamId) {
        log.info("handOverLeader(checkedMember={}, teamId={}) 호출", checkedMember, teamId);
        
        Integer handOverLeader = teamService.handOverLeader(checkedMember, teamId);
        
        return "redirect:/team/teamActivity?teamId=" + teamId;
    }
    
    /**
     * 팀 폐쇄하기
     * @param teamId 삭제할 팀의 번호(teamId).
     * @return 사이트 메인 페이지로 이동.
     * @author 서범수
     */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/deleteTeam")
    public String deleteTeam(Integer teamId) {
        log.info("deleteTeam(teamId= {}) 호출", teamId);
        
        teamService.deleteTeam(teamId);
        teamLogService.deleteByTeamId(teamId);
        
        return "redirect:/";
    }
    

    
    
}
