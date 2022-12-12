package pro.sbs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pro.sbs.domain.User;

public interface UserRepository extends JpaRepository<User, Integer>{

    List<User> findByOrderByUserIdDesc();
    
    @EntityGraph(attributePaths = "roles")
    Optional<User> findByUserName(String userName);
    
    List<User> findByPassword(String password);
    
    List<User> findByUserIdContainingOrPasswordIgnoreCaseContaining(Integer userId, String password);
    
    @Query("select u from USERS u where u.userId like ('%' || :userId || '%') and lower(u.password) like lower('%' || :password || '%')")
    List<User> searchByPw(@Param(value="userId") Integer userId, @Param(value = "password") String password);
}
