package pro.sbs.dto;

import pro.sbs.domain.Post;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PostReadDto {
    
    private Integer postId;
    private Integer teamId;
    private String title;
    private String content;
    private String author;

    
    public static PostReadDto fromEntity(Post entity) {
        return PostReadDto.builder()
                .postId(entity.getPostId())
                .teamId(entity.getTeam().getTeamId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .author(entity.getAuthor())
                .build();
    }
}
