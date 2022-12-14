package pro.sbs.web;

import java.io.IOException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.sbs.dto.CreateUserDto;
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

    @GetMapping("/")
    public String home() {
        log.info("home() 호출");

        return "user/home";
    }
    
    @GetMapping("/signUp")
    public void signUp() {
        log.info("signUp() 호출");

    }
    
    /**
     * 유저 회원 가입
     * DTO를 넘기면 암호화된 정보를 포함하여 저장
     * 유저 개인 파일 보관 폴더 생성
     * @param CreateUserDto, File
     * @return 완료 후 로그인 페이지로 이동
     * 김지훈
     * @throws IOException 
     */
    @PostMapping("/signUp")
    public String signUp(CreateUserDto dto, MultipartFile image) throws IOException {
        log.info("signUp(dto={},image={}) 호출", dto, image);
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        
        log.info("1");
        Integer fid = imagesService.saveImage(image);
        log.info("fid={}",fid);
        dto.setFid(fid);
        log.info("3");
        usersService.createUser(dto);
        
        return "redirect:/login";

    }

}
