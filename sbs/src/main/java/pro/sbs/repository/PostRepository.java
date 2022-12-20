package pro.sbs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pro.sbs.domain.Post;

public interface PostRepository  extends JpaRepository<Post, Integer>{

    // select * from POSTS order by ID desc
    List<Post> findByOrderByPostIdDesc();
    
    List<Post> findByTeamTeamIdOrderByPostIdDesc(Integer teamId);

    @Query(
"select p from POSTS p where lower(p.title) like lower('%' || :keyword || '%') or lower(p.content) like lower('%' || :keyword || '%') order by p.postId desc"
)
    List<Post> searchKeywordOrderByModfiedTime(@Param(value = "keyword") String keyword);  
    
    
    List<Post> findByTitleIgnoreCaseContainingOrderByPostIdDesc(String title);
    
    List<Post> findByContentIgnoreCaseContainingOrderByPostIdDesc(String content);
    
    List<Post> findByAuthorIgnoreCaseContainingOrderByPostIdDesc(String author);
    
    List<Post> findByTitleIgnoreCaseContainingOrContentIgnoreCaseContainingOrderByPostIdDesc(String title, String content);
    
    
}
