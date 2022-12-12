package pro.sbs.dto;

import pro.sbs.domain.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PostUpdateDto {
    
    private Integer id;
    private String title;
    private String content;
    private Integer teamId;
    
    public Post toEntity() {
        return Post.builder().postId(id).title(title).content(content).build();
    }
    
    public static PostUpdateDto fromEntity(Post entity) {
        return PostUpdateDto.builder()
                .teamId(entity.getTeam().getTeamId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .build();
    }
}