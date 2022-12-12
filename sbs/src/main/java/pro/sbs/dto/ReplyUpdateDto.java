package pro.sbs.dto;

import pro.sbs.domain.Reply;

import lombok.Data;

@Data
public class ReplyUpdateDto {
    
    private Integer replyId;
    private String replyText; // getReplyText(), setReplyText()
    
    public Reply toEntity() {
        return Reply.builder().replyId(replyId).replyText(replyText).build(); 
    }

}
