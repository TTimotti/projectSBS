package pro.sbs.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
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
import pro.sbs.dto.PostUpdateDto;
import pro.sbs.dto.ReplyReadDto;
import pro.sbs.service.PostService;
import pro.sbs.service.ReplyService;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping
public class PostController {
    
    
    private final PostService postService;
    
    private final ReplyService replyService;
    
    
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping({"/post/detail", "/post/modify"})
    public void detail(Integer id, Model model) {
        log.info("postController datail(id={})", id);
        log.info("postController modify(id={})", id);
        
            Post post = postService.readIndex(id);
        
        model.addAttribute("post", post);
        
    }
    
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/post/create")
    public void create(Integer id, Model model) {
        log.info("PostController Get create()");
        model.addAttribute("id", id);
        
    }
    
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/post/create")
    public String create(PostCreateDto dto, RedirectAttributes attrs, Integer id) {
        log.info("PostController Post create() dto = {}", dto);
        log.info("PostController create teamId = {}", id);
        
        dto.setTeamId(id);
        log.info("PostController Post create() dto2 = {}", dto);
        log.info("PostController Post create() dto.getTeamId = {}", dto.getTeamId());
        Post entity = postService.create(dto);
        log.info("PostController Post create() entity = {}", entity);
        attrs.addFlashAttribute("createdId", entity.getPostId());
        
        Integer teamId = dto.getTeamId();
        
        return "redirect:/team/teamActivity?teamId=" + teamId;
    }
    
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/post/update")
    public String update(PostUpdateDto dto) {
        log.info("PostController update(dto = {})",dto);
        
        Integer postId = postService.update(dto);
        
        log.info("PostController postId={}", postId);
        
        return "redirect:/post/detail?id=" + dto.getId();
    }
    
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/post/delete")
    public String delete(Integer id, RedirectAttributes attrs) {
        List<ReplyReadDto> list = new ArrayList<>();
        Post post = postService.readIndex(id);
        list = replyService.readReplies(id);
        log.info("replyList = {}", list);
        log.info("replylist.get(i).getReplyId() = {}", list.get(0).getReplyId());
        
        
        for(int i = 0; i < list.size(); i++) {
            replyService.delete(list.get(i).getReplyId());
            log.info("list.get(i).getReplyId() = {}", list.get(i).getReplyId());
        }
        
        log.info("PostController delete(id={})", id);
        
        Integer postId = postService.delete(id);
        attrs.addFlashAttribute("deletedPostId", postId);
        log.info("postController delete postId = {}", postId);
        
        return "redirect:/team/teamActivity?teamId=" + post.getTeam().getTeamId();
    }
    
    @GetMapping("/post/search")
    public String search(String type, String keyword, Model model) throws IndexOutOfBoundsException {
        log.info("search(type={}, keyword={})", type, keyword);
        
        List<Post> post = postService.search(type, keyword);
        model.addAttribute("post", post);
        log.info("list ={}", post);
   
        Integer teamId = post.get(0).getTeam().getTeamId();
        log.info("teamId ={}", teamId);
        
        return "redirect:/team/teamActivity?teamId=" + teamId;
    }
    
    
}