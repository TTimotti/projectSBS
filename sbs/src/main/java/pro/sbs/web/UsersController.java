package pro.sbs.web;

import java.io.File;
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
import pro.sbs.domain.Images;
import pro.sbs.domain.Users;
import pro.sbs.dto.UsersCashDto;
import pro.sbs.dto.UsersCreateDto;
import pro.sbs.dto.UsersUpdateDto;
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
    public void myPage(Model model, String userName, Integer userId) {
        log.info("myPage(userName = {}, userId = {})", userName, userId);
                
        Users user = null;
        if (userName == null) {
            user = usersService.read(userId);
        } else {
            user = usersService.read(userName);
        }
        
        Integer fid = user.getFid();
        Images image = imagesService.readByFid(fid);

        
        log.info("user = {}", user);
        log.info("image = {}", image);
        
        model.addAttribute("users", user);
        model.addAttribute("image", image);
        
    } 
    
    
    /**
     * 아이디 중복 체크 기능
     * 
     * @param userName
     * @return 중복 = nok , 중복 아님 = ok return
     * @author 이존규
     */
    @GetMapping("/checkid")
    @ResponseBody // 컨트롤러 메서드가 리턴하는 값이 뷰의 이름이 아니라 클라이언트로 직접 전송되는 데이터인 경우 사용
    public ResponseEntity<String> checkUsername(String userName) {
        log.info("checkUserName() = {}", userName);

        String result = usersService.checkUsername(userName);

        return ResponseEntity.ok(result);
    }
    
    /**
     * 입력받은 아이디 값과 비밀번호 값을 비교
     * @param userId 
     * @param password
     * @return 비밀번호 일치 = ok / 비밀번호 불일치 = nok
     * @author 이존규
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
     * @author 이존규
     */
    @PostMapping("/cash")
    public String cash(UsersCashDto dto) throws UnsupportedEncodingException {
        log.info("cash = {}", dto);
        
        String result = usersService.cash(dto);
        
        String encodedParam = URLEncoder.encode(result);
        
        return "redirect:/user/myPage?userName="+ encodedParam;
    }
    
    /**
     * user id를 이용하여 update 홈페이지로 이동
     * @param userId 
     * @author 이존규
     */
    @GetMapping("/update")
    public void update(Integer userId, Model model) {
        log.info("update(id) id = {}", userId);

        Users user = usersService.read(userId);
        log.info("user = {}", user);

        model.addAttribute("user", user);
    }
    
    /**
     * user update 정보를 받아서 update 실행
     * @param user Update dto
     * @return 수정된 유저의 마이페이지
     * @author 이존규
     */
    @PostMapping("/update")
    public String updatePost(UsersUpdateDto dto) {
        log.info("updateDto(dto) ={}", dto);

        Integer userId = usersService.update(dto);

        String encodedParam = URLEncoder.encode(dto.getUserName());
        
        return "redirect:/user/myPage?userName="+ encodedParam;
    }
}
