package pro.sbs.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString(exclude = { "post" })
@Entity(name = "REPLIES") 
@SequenceGenerator(name = "REPLIES_SEQ_GEN", sequenceName = "REPLIES_SEQ",  initialValue = 1, allocationSize = 1)
public class Reply extends BaseTimeEntity {
    
    
    @Id
    @Column(nullable = false, name = "reply_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REPLIES_SEQ_GEN")
    private Integer replyId; // 댓글 번호
    
    @ManyToOne(fetch = FetchType.LAZY) 
    private Post post; // 댓글이 달린 포스트
    
    @Column(nullable = false, length = 1000, name = "reply_text") 
    private String replyText; // 댓글 내용
    
    @Column(nullable = false)
    private String writer; // 댓글 작성자.
    
    public Reply update(String replyText) {
        this.replyText = replyText;
        return this;
    }

}
