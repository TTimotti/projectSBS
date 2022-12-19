package pro.sbs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pro.sbs.domain.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {

    List<Reply> findByPostPostIdOrderByReplyIdDesc(Integer postId);
    
    @Query("select r from REPLIES r where r.post.postId = :postId order by r.replyId desc")
    List<Reply> selectAllReplies(@Param(value = "postId") Integer postId);
}
