package pro.sbs.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.sbs.dto.ReplyReadDto;
import pro.sbs.dto.ReplyRegisterDto;
import pro.sbs.dto.ReplyUpdateDto;
import pro.sbs.service.ReplyService;

@Slf4j
@RequiredArgsConstructor
@RestController 
@RequestMapping("/api/reply")
public class ReplyRestController {

    private final ReplyService replyService;
    
    @PostMapping // 댓글 등록 REST 서비스
    public ResponseEntity<Integer> registerReply(@RequestBody ReplyRegisterDto dto) {
       
        log.info("ReplyRestController create dto = {}", dto);
        
        Integer replyId = replyService.create(dto);
        
        log.info("ReplyRestController create replyId = {}", replyId);
        
        return ResponseEntity.ok(replyId); 
    }
    
    
    @GetMapping("/all/{postId}")
   
    public ResponseEntity<List<ReplyReadDto>> readAllReplies(
            @PathVariable Integer postId) {
   
        log.info("ReplyRestController readAll postId = {}", postId);
        
        List<ReplyReadDto> list = replyService.readReplies(postId);
        log.info("ReplyRestController List<Dto> list = {}", list);
        log.info("@ of list size = ", list.size());
        
        return ResponseEntity.ok(list); 
    }
    
    @GetMapping("/{replyId}")
    public ResponseEntity<ReplyReadDto> getReply(@PathVariable Integer replyId) {
        log.info("ReplyRestController getReply replyId = {}", replyId);
        
        ReplyReadDto dto = replyService.readReply(replyId);
        
        return ResponseEntity.ok(dto);
    }
    
    @DeleteMapping("/{replyId}")
    public ResponseEntity<Integer> deleteReply(@PathVariable Integer replyId) {
        log.info("ReplyRestController deleteReply(replyId={})", replyId);
        
        Integer result = replyService.delete(replyId);
        
        return ResponseEntity.ok(result);
    }
    
    @PutMapping("/{replyId}")
    public ResponseEntity<Integer> updateReply(
            @PathVariable Integer replyId, 
            @RequestBody ReplyUpdateDto dto) {
        log.info("ReplyRestController updateReply(replyId={}, dto={})", replyId, dto);
        
        dto.setReplyId(replyId); 
        Integer result = replyService.update(dto);
        
        return ResponseEntity.ok(result);
    }
}
