package pro.sbs.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.sbs.domain.Post;
import pro.sbs.dto.PostCreateDto;
import pro.sbs.service.PostService;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping
public class PostController {
    
//    private final TeamService teamService;
    
    private final PostService postService;
    
    @GetMapping({"/post/detail", "/post/modify"})
    public void detail(Integer id, Model model) {
        log.info("postController datail(id={})", id);
        log.info("postController modify(id={})", id);
        
            Post post = postService.readIndex(id);
        
        model.addAttribute("post", post);
        
    }
    
    @GetMapping("/post/create")
    public void create(Integer id, Model model) {
        log.info("PostController Get create()");
        
    }
    
    /**
     * 게시물 작성.
     * @param dto
     * @param attrs
     * @param id
     * @return
     * @author 추
     */
    @PostMapping("/post/create")
    public String create(PostCreateDto dto, RedirectAttributes attrs, Integer id) {
        log.info("PostController Post create() dto = {}", dto);
        log.info("PostController create teamId = {}", id);
        // 여기서 종속시에 타입이 달라 아이디를 직접 넣어줘야해서 이렇게함 
        dto.setTeamId(id);
        log.info("PostController Post create() dto2 = {}", dto);
        Post entity = postService.create(dto);
        log.info("PostController Post create() entity = {}", entity);
        attrs.addFlashAttribute("createdId", entity.getPostId());
        
        return "redirect:/team/teamActivity?id=" + id;
    }
    
//    @PostMapping("/post/update")
//    public String update(PostUpdateDto dto) {
//        log.info("PostController update(dto = {})",dto);
//        
//        Integer postId = postService.update(dto);
//        
//        log.info("PostController postId={}", postId);
//        
//        return "redirect:/post/detail?id=" + dto.getId();
//    }
    
    @PostMapping("/post/delete")
    public String delete(Integer id, RedirectAttributes attrs) {
        log.info("PostController delete(id={})", id);
        
        Integer postId = postService.delete(id);
        attrs.addFlashAttribute("deletedPostId", postId);
        log.info("postController delete postId = {}", postId);
        
        return "redirect:/";
    }
    
    
    
}