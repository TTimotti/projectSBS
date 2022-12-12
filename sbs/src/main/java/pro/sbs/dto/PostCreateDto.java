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
    
    // DTO 객체를 엔터티 객체로 변환 리턴하는 메서드 - PostService에서 PostRepository를 호출할 대 사용.
    public Post toEntity() {
        return Post.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
    
//   public void PostCreateDto() {
//       this.title = title;
//       this.content = content;
//       this.author = author;
//       this.teamId = teamId;
//       
//   }

    
    public PostCreateDto fromEntity(Post entity) {
        return PostCreateDto.builder()
                .teamId(entity.getTeam().getTeamId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .author(entity.getAuthor())
                .build();
    }
    
    public Integer getAddTeamId(Integer id) {
        this.teamId = id;
        return this.teamId;
    }

    public PostCreateDto postCreateDto(String title, String content, String author, Integer teamId) {
        
        this.title = title;
        this.content = content;
        this.author = author;
        this.teamId = teamId;
        
        return this;
    }
    
}