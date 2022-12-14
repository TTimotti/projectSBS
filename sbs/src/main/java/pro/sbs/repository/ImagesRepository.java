package pro.sbs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pro.sbs.domain.Images;
import pro.sbs.domain.Teams;

public interface ImagesRepository extends JpaRepository<Images, Integer> {

    List<Images> findByOrderByFidDesc();

}
