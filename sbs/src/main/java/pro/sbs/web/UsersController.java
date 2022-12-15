package pro.sbs.web;

import java.io.IOException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.sbs.domain.Users;
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
     */
    @GetMapping("/myPage")
    public void myPage(Model model, String userName) {
        log.info("mypage(name = {})", userName);
        
        Users user = usersService.read(userName);
        
        log.info("user = {}", user);
        
        model.addAttribute("users", user);
        
    }
}
