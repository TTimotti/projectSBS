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
public class PostCreateDto {
    private String title;
    private String content;
    private String author;
    private Integer teamId;
    
    /**
     * post 타입에 빌더를 리턴해야 저장할 수 있음.
     * @return
     * @author 추
     */
    public Post toEntity() {
        return Post.builder()
                .teamId(teamId)
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
    
    /**
     * 이건 종속때 사용하는거
     * @param entity
     * @return
     */
//    public PostCreateDto fromEntity(Post entity) {
//        return PostCreateDto.builder()
//                .teamId(entity.getTeam().getTeamId())
//                .title(entity.getTitle())
//                .content(entity.getContent())
//                .author(entity.getAuthor())
//                .build();
//    }
    
}