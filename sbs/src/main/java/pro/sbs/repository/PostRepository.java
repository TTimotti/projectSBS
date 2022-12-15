package pro.sbs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pro.sbs.domain.Post;

public interface PostRepository  extends JpaRepository<Post, Integer>{

    // select * from POSTS order by ID desc
    List<Post> findByOrderByPostIdDesc();
    
    /**
     * 팀테이블에 종속되어있는 포스트의 아이디를 검색해 팀_ 팀아이디를 가져오는 메서드.
     * 일단 아래 메서드로 대체 
     * @param teamId
     * @return
     * @author 추
     */
//    List<Post> findByTeamTeamIdOrderByPostIdDesc(Integer teamId);
    
    List<Post> findByTeamIdOrderByPostIdDesc(Integer teamId);
    
}
