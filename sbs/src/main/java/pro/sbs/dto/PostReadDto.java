package pro.sbs.dto;

import pro.sbs.domain.Post;

import java.time.LocalDateTime;

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
    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;

    
    public static PostReadDto fromEntity(Post entity) {
        return PostReadDto.builder()
                .postId(entity.getPostId())
                .teamId(entity.getTeam().getTeamId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .author(entity.getAuthor())
                .createdTime(entity.getCreatedTime())
                .modifiedTime(entity.getModifiedTime())
                .build();
    }
}
