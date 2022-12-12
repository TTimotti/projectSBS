package pro.sbs.service;



import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.sbs.domain.User;
import pro.sbs.dto.UserCashDto;
import pro.sbs.dto.UserCreateDto;
import pro.sbs.dto.UserPasswordChangeDto;
import pro.sbs.dto.UserUpdateDto;
import pro.sbs.repository.UserRepository;

@Slf4j
@RequiredArgsConstructor // final 필드를 초기화하는 생성자.
@Service // 스프링 컨텍스트에 Service 컴포넌트로 등록.
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    
    @Transactional(readOnly = true)
    public User read(Integer id) {
        log.info("User read(id = {})", id);
        
        return userRepository.findById(id).get();
    }
    
    public String checkUsername(String userName) {
        log.info("checkUsername(username)={})", userName);
        
        Optional<User> result = userRepository.findByUserName(userName);
        
        if (result.isPresent()) { // username이 일치하는 생성된 객체가 존재하는 경우,
            return "nok";
            } else { // username이 일치하는 생성된 객체가 존재하지 않는 경우.
            return "ok";
            }
        }
    
    public User createUser(UserCreateDto dto) {
        log.info("createUser(dto)={}", dto);
        
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        User entity = userRepository.save(dto.toEntity());
        log.info("entity = {}", entity);
        
        return entity;
    }
    
    @Transactional(readOnly = true)
    public List<User> read() {
        log.info("read all");
        
        return userRepository.findByOrderByUserIdDesc();
    }
    
    @Transactional
    public Integer Update(UserUpdateDto dto) {
        log.info("user update (dto) = {}", dto);
        
        User entity = userRepository.findById(dto.getUserId()).get();
        
        entity.update(dto.getNickname(), dto.getEmail(), dto.getPhone(), dto.getGender(), dto.getBirthday(), dto.getLocationId());
        
        return entity.getUserId();
    }
    
    @Transactional
    public Integer passwordChange(UserPasswordChangeDto dto) {
        log.info("pc Service dto = {}", dto);
        
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        
        User entity = userRepository.findById(dto.getUserId()).get();
        
        entity.passwordChange(dto.getPassword());
        
        log.info("password change dto = {}", dto);
        
        return entity.getUserId();
    }
    
    @Transactional
    public Integer cash(UserCashDto dto) {
        log.info("cash + ? = {}", dto.getCash());
        
        User entity = userRepository.findById(dto.getUserId()).get();
        
        entity.cash(dto.getCash());
        
        log.info("cash ++ ");
        
        return dto.getUserId();
    }
    
    public String checkPw(Integer userId, String password) {
        log.info("checkPw userid = {} password = {}", userId, password);
        
        User user = userRepository.findById(userId).get();
        
        log.info("ckpw user = {}", user);
        String encodingPw = user.getPassword();
        
        log.info(encodingPw);
        
        Boolean confirm = confirm(password, encodingPw);
        
        log.info("confirm = {}", confirm);
        
        if (confirm == true) {
            return "ok";
        } else {
            return "nok";
        }
    }

    private Boolean confirm(String password, String password2) {
        return passwordEncoder.matches(password, password2);
    }
    
    public Integer delete(Integer userId) {
        log.info("delete");
        
        userRepository.deleteById(userId);
        
        return userId;
    }

    public User readByUserName(String userName) {
        User entity = userRepository.findByUserName(userName).get();
        return entity;
    }
}

