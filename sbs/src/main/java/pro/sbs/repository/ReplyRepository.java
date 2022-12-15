package pro.sbs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pro.sbs.domain.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {

    /**
     * 이것도 똑같이 종속시에 사용하는 쿼리문 
     * 일단 아래꺼로 대체..
     * @param postId
     * @return
     * @author 추
     */
//    List<Reply> findByPostPostIdOrderByReplyIdDesc(Integer postId);
    
    List<Reply> findByPostIdOrderByReplyIdDesc(Integer postId);
    
//    @Query("select r from REPLIES r where r.post.postId = :postId order by r.replyId desc")
//    List<Reply> selectAllReplies(@Param(value = "postId") Integer postId);
    
    @Query("select r from REPLIES r where r.postId = :postId order by r.replyId desc")
    List<Reply> selectAllReplies(@Param(value = "postId") Integer postId);
}
