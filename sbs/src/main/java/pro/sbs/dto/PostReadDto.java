package pro.sbs.dto;

import pro.sbs.domain.Post;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PostReadDto {
    // field
    private Integer postId;
    private Integer teamId;
    private String title;
    private String content;
    private String author;

    
    /**
     * 이 readDto는 종속시에 post객체를 그대로 사용하면 문제가 생겨서 만듦.
     * 어짜피 써야하니까 컨트롤러에서도 그냥 readDto로 그려주면 편할듯 어짜피 post로 하나 똑같아서
     * 
     * @param entity
     * @return
     * @author 추
     */
    public static PostReadDto fromEntity(Post entity) {
        return PostReadDto.builder()
                .postId(entity.getPostId())
                .teamId(entity.getTeamId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .author(entity.getAuthor())
                .build();
    }
    
//    public static PostReadDto fromEntity(Post entity) {
//        return PostReadDto.builder()
//                .postId(entity.getPostId())
//                .teamId(entity.getTeam().getTeamId())
//                .title(entity.getTitle())
//                .content(entity.getContent())
//                .author(entity.getAuthor())
//                .build();
//    }
}
