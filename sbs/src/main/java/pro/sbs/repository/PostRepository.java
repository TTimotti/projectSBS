package pro.sbs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pro.sbs.domain.Post;

public interface PostRepository  extends JpaRepository<Post, Integer>{

    // select * from POSTS order by ID desc
    List<Post> findByOrderByPostIdDesc();
    
    List<Post> findByTeamTeamIdOrderByPostIdDesc(Integer teamId);

    
    
//    @Query(
//"insert into POSTS (title, content, author, teamTeamId) VALUES ('test', 'test', 'test', 2)"            
//    )
//    Post saveDto(PostCreateDto dto);
//    
    
}
