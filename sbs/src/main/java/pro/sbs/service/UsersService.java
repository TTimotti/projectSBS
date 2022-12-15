package pro.sbs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.sbs.domain.Users;
import pro.sbs.dto.UsersCashDto;
import pro.sbs.dto.UsersCreateDto;
import pro.sbs.repository.UsersRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class UsersService {

    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;

    /**
     * 모든 유저들 검색
     * 
     * @return 검색된 List<Users>
     * @author 김지훈
     */
    @Transactional(readOnly = true) // 검색 속도가 빨라짐.
    public List<Users> read() {
        log.info("read() 호출");

        return usersRepository.findByOrderByUserIdDesc();
    }

    /**
     * DTO 기반 Users 생성, DB에 저장
     * 
     * @param dto
     * @return 생성된 Users
     * @author 김지훈
     */
    public Users createUser(UsersCreateDto dto) {
        return usersRepository.save(dto.toEntity());
    }

    /**
     * user name을 이용하여 users 객체 호출
     * 
     * @param userName
     * @return Users 객체
     * @author 이존규
     */
    @Transactional(readOnly = true)
    public Users read(String userName) {

        log.info("userId = {}", userName);
        return usersRepository.findByUserName(userName).get();
    }

    /**
     * user id를 이용하여 users 객체 호출
     * 
     * @param userId
     * @return Users 객체
     * @author 이존규
     */
    public Users read(Integer userId) {

        log.info("userId ={}", userId);
        return usersRepository.findById(userId).get();
    }

    /**
     * user id와 그 id 에 맞는 비밀번호를 확인
     * 
     * @param userId, password
     * @return 일치하면 ok, 일치하지 않으면 nok 값을 리턴.
     * @author 이존규
     */
    public String checkPw(Integer userId, String password) {
        log.info("checkPw userid = {} password = {}", userId, password);

        Users user = usersRepository.findById(userId).get();

        log.info("ckpw user = {}", user);
        String enCodingPw = user.getPassword();

        log.info(enCodingPw);

        Boolean confirm = confirm(password, enCodingPw);

        log.info("confirm = {}", confirm);

        if (confirm == true) {
            return "ok";
        } else {
            return "nok";
        }
    }

    /**
     * 암호화된 비밀번호와 암호화되지 않은 비밀번호를 비교하는 메서드
     * 
     * @param password  기존의 패스워드
     * @param password2 암호화된 패스워드
     * @return 일치 / 불일치 값을 참 / 거짓으로 return
     * @author 이존규
     */
    public boolean confirm(String password, String password2) {
        return passwordEncoder.matches(password, password2);
    }

    /**
     * 캐시충전 
     * @param dto (user id, user name, 캐쉬 금액)
     * @return 캐쉬 충전된 계정의 이름을 return
     * @author 이존규
     */
    @Transactional
    public String cash(UsersCashDto dto) {
        log.info("cash + ? = {}", dto.getCash());

        Users entity = usersRepository.findById(dto.getUserId()).get();

        entity.chargeCash(dto.getCash());

        log.info("cash = {}, {}",dto.getUserName(), dto.getCash());

        return dto.getUserName();

    }

    /**
     * 아이디 중복체크 기능
     * @param userName 
     * @return 중복 - nok, 중복 아님 - ok
     * @author 이존규
     */
    @Transactional(readOnly = true)
    public String checkUsername(String userName) {
        log.info("checkUsername(username={})", userName);

        Optional<Users> result = usersRepository.findByUserName(userName);
        if (result.isPresent()) { // username이 일치하는 생성된 객체가 존재하는 경우.
            return "nok";
        } else { // username이 일치하는 생성된 객체가 존재하지 않는 경우.
            return "ok";
        }
    }
 
}
