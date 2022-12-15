package pro.sbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pro.sbs.domain.MyActivityList;

public interface MyActivityListRepository extends JpaRepository<MyActivityList, Integer> {

}
