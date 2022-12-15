package pro.sbs.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.sbs.domain.Users;
import pro.sbs.dto.UsersCashDto;
import pro.sbs.dto.UsersCreateDto;
import pro.sbs.service.ImagesService;
import pro.sbs.service.UsersService;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
public class UsersController {
 
    private final PasswordEncoder passwordEncoder;
    private final UsersService usersService;
    private final ImagesService imagesService;

    
    @GetMapping("/signUp")
    public void signUp() { 
        log.info("signUp() 호출");

    }
    
    /**
     * 유저 회원 가입
     * DTO를 넘기면 암호화된 정보를 포함하여 저장
     * 유저 개인 파일 보관 폴더 생성
     * @param CreateUserDto, File
     * @return 완료 후 로그인 페이지로 이동     * 
     * @throws IOException 
     * @author 김지훈
     */
    @PostMapping("/signUp")
    public String signUp(UsersCreateDto dto, MultipartFile image) throws IOException {
        log.info("signUp(dto={},image={}) 호출", dto, image);
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        
        Integer fid = imagesService.saveImage(image); // 이미지 객체 생성 후 DB에 저장     
        
        dto.setFid(fid); // DTO에 FID 초기화
        
        usersService.createUser(dto); // Users 생성하는 서비스 호출
        
        return "redirect:/login";

    }

    
    /**
     * 마이페이지
     * 
     * @param userName = 로그인한 아이디
     * @return 마이페이지로 이동
     * @author 이존규
     */
    @GetMapping("/myPage")
    public void myPageNameG(Model model, String userName) {
        log.info("mypage(name = {})", userName);
        
        Users user = usersService.read(userName);
        
        log.info("user = {}", user);
        
        model.addAttribute("users", user);
        
    } 
    
    /**
     * 마이페이지 (뒤로가기 등으로 POST 값이 주어질 경우)
     * 
     * @param userid = 로그인한 아이디의 id값
     * @return 마이페이지로 이동
     * @author 이존규
     */
    @PostMapping("/myPage")
    public void myPageIdP(Model model, Integer userId) {
        log.info("mypage(id = {})", userId);
        Users user = usersService.read(userId);
        model.addAttribute("users", user);

    }
    
    /**
     * 입력받은 아이디 값과 비밀번호 값을 비교
     * @param userId 
     * @param password
     * @return 비밀번호 일치 = ok / 비밀번호 불일치 = nok
     */
    @GetMapping("/checkpw")
    @ResponseBody
    public ResponseEntity<String> checkPw(Integer userId, String password) {
        log.info("User id = {}, password = {}", userId, password);

        String result = usersService.checkPw(userId, password);

        return ResponseEntity.ok(result);
    }
    
    /**
     * 캐쉬 충전 페이지로 이동
     * @param userId 로그인한 user의 id
     * @author 이존규
     */
    @GetMapping("/cash")
    public void cash(Integer userId, Model model) {
        log.info("cash = {}", userId);
        Users user = usersService.read(userId);
        model.addAttribute("user", user);
    }
    
    
    /**
     * userId, username, 입력받은 캐쉬를 dto로 받아서 저장
     * @param dto
     * @return myPage로 redirect 
     */
    @PostMapping("/cash")
    public String cash(UsersCashDto dto) throws UnsupportedEncodingException {
        log.info("cash = {}", dto);
        
        String result = usersService.cash(dto);
        
        String encodedParam = URLEncoder.encode(result);
        
        return "redirect:/user/myPage?userName="+ encodedParam;
    }
    
}
