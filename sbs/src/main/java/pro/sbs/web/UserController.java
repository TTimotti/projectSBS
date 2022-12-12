package pro.sbs.web;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.sbs.domain.Team;
import pro.sbs.domain.TeamLog;
import pro.sbs.domain.User;
import pro.sbs.dto.UserCashDto;
import pro.sbs.dto.UserPasswordChangeDto;
import pro.sbs.dto.UserCreateDto;
import pro.sbs.dto.UserUpdateDto;
import pro.sbs.service.TeamLogService;
import pro.sbs.service.TeamService;
import pro.sbs.service.UserService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {
    
private final UserService userService;
private final TeamLogService teamLogService;
private final TeamService teamService;


//    @GetMapping("/user/myPage") // ?userId={userId} (userId = ${ user.userId }) } 는 알아서 캐치..
//    public void myPage(Model model, Integer userId) {
//        log.info("mypage(id = {})", userId);
//        User user = userService.read(userId);
//        model.addAttribute("user", user);
//        
//   
//    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user/myPage")
    public void myPage2(Principal principal, Model model) {
        log.info("유저의 고유 아이디 = {}", principal.getName());
        String userName = principal.getName();
        User user = userService.readByUserName(userName);
        log.info("가져온 값은 = {} ", user.getUserId());
        model.addAttribute("user", user);
        
        
        // Security를 사용하면  /user/myPage?userId={userId} (userId = ${ user.userId }),
        // return "redirect:/post/detail?id=" + dto.getId(); 를 사용하지 않아도 됨!!!!!!!!
   
    }
    @PostMapping("/user/myPage")
    public void myPagePOST(Model model, Integer userId) {
        log.info("mypage(id = {})", userId);
        User user = userService.read(userId);
        model.addAttribute("user", user);
        
    }
    
    @GetMapping("/user/update")
    public void update(Integer userId, Model model) {
        log.info("update(id) id = {}", userId);
        
        User user = userService.read(userId);
        log.info("user = {}", user);
        
        model.addAttribute("user", user);
    }
    
    @PostMapping("/user/update")
    public String updatePost(UserUpdateDto dto) {
        log.info("updateDto(dto) = {}", dto);
        Integer userId = userService.Update(dto);
        
        return "redirect:/user/myPage?userId=" + dto.getUserId() ;
    }
    
    @GetMapping("/user/create")
    public void create() {
        log.info("create() GET");
        
    }
    
    @GetMapping("/user/checkid")
    @ResponseBody // 컨트롤러 메서드가 리턴하는 값이 뷰의 이름이 아니라 클라이언트로 직접 전송되는 데이터인 경우 사용
    public ResponseEntity<String> checkUsername(String userName){
        log.info("checkUserName() = {}", userName);
    
        String result = userService.checkUsername(userName);
        
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/user/create")
    public String create(UserCreateDto dto) {
        
        userService.createUser(dto);
        
        return "/user/list";
    }
    
    @GetMapping("/user/list")
    public String list(Model model) {
        log.info("user()");
        List<User> list = userService.read();
        model.addAttribute("list",list);
        return "/user/list";
    }
    
    @GetMapping("/user/joinedTeamList")
    public void joinedTeamList() {
        log.info("가입한 모임을 보여주는 중....");
        
        // 로그인한 회원 정보를 가져온 후, 가입한 목록을 SQL을 통해서 가져온다.
    }
    
    @PostMapping("/user/readByLoginUser/")
    @ResponseBody
    public ResponseEntity<List<Team>> readByLoginUser(@RequestBody String loginUser) {
        log.info("로그인확인 {}", loginUser);
        
        List<Team> list = teamService.readByLoginUser(loginUser);
        
        return ResponseEntity.ok(list);
    }
    
    @GetMapping("/user/checkpw")
    @ResponseBody
    public ResponseEntity<String> checkPw(Integer userId, String password) {
        log.info("User id = {}, password = {}", userId, password);
        
        String result = userService.checkPw(userId, password);
        
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/user/delete")
    public String delete(Integer userId) {
        log.info("delete(userId) = {}", userId);
        
        Integer result = userService.delete(userId);
        
        return "redirect:/user/list";
    }
    
    @GetMapping("/user/passwordChange")
    public void passwordChange(Integer userId, Model model) {
        log.info("passwordChange(userId = {})", userId);
        
        User user = userService.read(userId);
        
        model.addAttribute("user", user);
    }
    
    @PostMapping("/user/passwordChange")
    public String passwordChange(UserPasswordChangeDto dto) {
        log.info("changePw dto = {}", dto);
        
        User user = userService.read(dto.getUserId());
        
        log.info("user = {}", user);
        
        Integer result = userService.passwordChange(dto);
        
        return "redirect:/user/myPage?userId=" + dto.getUserId();
    }

    @GetMapping("/user/cash")
    public void cash(Integer userId, Model model) {
        log.info("cash = {}", userId);
        User user = userService.read(userId);
        model.addAttribute("user", user);
    }
    
    @PostMapping("/user/cash")
    public String cash(UserCashDto dto) {
        log.info("cash = {}", dto);
        Integer result = userService.cash(dto);
        
        return "redirect:/user/myPage?userId=" + dto.getUserId();
    }
}
