package pro.sbs.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;
import pro.sbs.domain.Reply;

@Builder
@Data
public class ReplyReadDto {

       /**
        * @author 추
        */
        private Integer replyId; 
        private Integer postId; 
        private String replyText; 
        private String writer; 
        private LocalDateTime createdTime; 
        private LocalDateTime modifiedTime; 
        
        /**
         * 밑에껀 종속시 사용할꺼 마찬가지로 종속됐을땐 타입변환해서 만들어줘야해서 만들어놈
         * @param entity
         * @return
         */
        public static ReplyReadDto fromEntity(Reply entity) {
            
            return ReplyReadDto.builder()
                    .replyId(entity.getReplyId())
                    .postId(entity.getPostId())
                    .replyText(entity.getReplyText())
                    .writer(entity.getWriter())
                    .createdTime(entity.getCreatedTime())
                    .modifiedTime(entity.getModifiedTime())
                    .build();
        }
        
        /**
         * 종속시 사용
         */
//        public static ReplyReadDto fromEntity(Reply entity) {
//            
//            return ReplyReadDto.builder()
//                    .replyId(entity.getReplyId())
//                    .postId(entity.getPost().getPostId())
//                    .replyText(entity.getReplyText())
//                    .writer(entity.getWriter())
//                    .createdTime(entity.getCreatedTime())
//                    .modifiedTime(entity.getModifiedTime())
//                    .build();
//        }
}
