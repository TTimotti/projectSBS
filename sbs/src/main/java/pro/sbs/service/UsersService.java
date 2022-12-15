package pro.sbs.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.sbs.domain.Users;
import pro.sbs.dto.UsersCreateDto;
import pro.sbs.repository.UsersRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class UsersService {
    
    private final UsersRepository usersRepository;
    
    /**
     * 모든 유저들 검색
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
     * @param dto
     * @return 생성된 Users
     * @author 김지훈
     */
    public Users createUser(UsersCreateDto dto) {
        return usersRepository.save(dto.toEntity());
    }

    
    /**
     * user name을 이용하여 users 객체 호출
     * @param userName
     * @return Users 객체
     */
    @Transactional(readOnly = true)
    public Users read(String userName) {
        
        log.info("userId = {}", userName);
        return usersRepository.findByUserName(userName).get();
    }
    
}
