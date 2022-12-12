package pro.sbs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pro.sbs.domain.Images;

public interface TeamImageRepository extends JpaRepository<Images, Integer>{

    Optional<Images> findByFileId(Integer teamId);

}
