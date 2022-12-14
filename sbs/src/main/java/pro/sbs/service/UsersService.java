package pro.sbs.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.sbs.domain.Users;
import pro.sbs.dto.CreateUserDto;
import pro.sbs.repository.UsersRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class UsersService {
    
    private final UsersRepository usersRepository;
    
    @Transactional(readOnly = true) // 검색 속도가 빨라짐.
    public List<Users> read() {
        log.info("read() 호출");
        
        return usersRepository.findByOrderByUserIdDesc();
    }
    
    public Users createUser(CreateUserDto dto) {
        return usersRepository.save(dto.toEntity());
    }

}
