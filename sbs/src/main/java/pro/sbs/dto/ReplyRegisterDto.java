package pro.sbs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplyRegisterDto {
    private Integer postId; 
    private String replyText;  
    private String writer; 
    
    
}
