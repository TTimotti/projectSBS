package pro.sbs.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.sbs.domain.Post;
import pro.sbs.domain.Team;
import pro.sbs.dto.PostCreateDto;
import pro.sbs.dto.PostReadDto;
import pro.sbs.dto.PostUpdateDto;
import pro.sbs.repository.PostRepository;
import pro.sbs.repository.TeamRepository;

@RequiredArgsConstructor // final 필드를 초기화하는 생성자.
@Service // 스프링 컨텍스트에 Service 컴포넌트로 등록.
@Slf4j
public class PostService {

    private final TeamRepository teamRepository;
    
    private final PostRepository postRepository; // 생성자에 의한 의존성 주입.
    
    @Transactional(readOnly = true)
    public List<PostReadDto> read(Integer teamId) {
        log.info("postService read()");
        
        List<Post> list = postRepository.findByTeamTeamIdOrderByPostIdDesc(teamId);
        
        return list.stream().map(PostReadDto::fromEntity).toList();
    }
    
    @Transactional(readOnly = true)
    public Post readIndex(Integer id) {
        log.info("postService readIndex(Id) = {}", id);
        
        Post index = postRepository.findById(id).get();
        log.info("PostService readIndex index ={}", index);
        
        return index;
    }
    
    /**
     * 팀아이드를 넣은 팀 객체(team)를 포스트객체(post)에 빌더로 생성해야 save에 팀아이디가 종속된 포스트dto를 넣어
     * 저장할 수 있다. save는 
     * @param dto
     * @return
     */
    public Post create(PostCreateDto dto) {
        log.info("PostService create(dto={}", dto);
        
        Team team = teamRepository.findById(dto.getTeamId()).get();
        log.info("PostService create(team = {}", team);
        
        Post post = Post.builder().team(team)
                .title(dto.getTitle())
                .content(dto.getContent())
                .author(dto.getAuthor())
                .build();
        log.info("PostService create(post = {}", post);
        
        Post entity = postRepository.save(post);
        
        log.info("PostService create(entity = {}", entity);
        
        return entity;
    }

    @Transactional
    public Integer update(PostUpdateDto dto) {
        log.info("postService update(dto={})", dto);
        
        Post entity = postRepository.findById(dto.getId()).get(); // (1)
        entity.update(dto.getTitle(), dto.getContent()); // (2)
        
        return entity.getPostId();
    }
    
    public Integer delete(Integer id) {
        log.info("postService delete(id={})", id);
        
        postRepository.deleteById(id);
        
        return id;
    }
    
}
