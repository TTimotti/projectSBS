package pro.sbs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import pro.sbs.domain.Teams;
import pro.sbs.domain.Users;

public interface UsersRepository extends JpaRepository<Users, Integer> {

    List<Users> findByOrderByUserIdDesc();

    @EntityGraph(attributePaths = "roles")
    Optional<Users> findByUserName(String userName);

    Optional<Users> findByUserId(Integer userId);

}
