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
@RestController // REST 서비스에서 사용되는 컨트롤러 객체
// -> @Controller 클래스의 메서드 리턴값(String) => 뷰 이름.
// -> @RestControlleer 클래스의 메서드 리턴값 => 클라이언트로 전송되는 값!
@RequestMapping("/api/reply")
public class ReplyRestController {

    private final ReplyService replyService;
    
    @PostMapping // 댓글 등록 REST 서비스
    public ResponseEntity<Integer> registerReply(@RequestBody ReplyRegisterDto dto) {
        // Ajax 요청에서 서버로 보내는 데이터 HTTP 패킷의 body에 포함됨.
        // @RequestBody: HTTP 패킷 vody에포함된 데이터(파라미터)를 분석할 때 사용.
        log.info("ReplyRestController create dto = {}", dto);
        
        Integer replyId = replyService.create(dto);
        
        log.info("ReplyRestController create replyId = {}", replyId);
        
        // 작성된 댓글의 아이디를 응답 데이터(response data)로 만들어서 Ajax 요청에 대한 성공 응답을 전송
        return ResponseEntity.ok(replyId); 
    }
    
    
    @GetMapping("/all/{postId}")
    // REST controller에서 ResponseEntity를 리턴할 때, 데이터 타입으로 엔터티 클래스를 사용하면 안됨. 
    public ResponseEntity<List<ReplyReadDto>> readAllReplies(
            @PathVariable Integer postId) {
        //@PathVariable: 요청 주소에서 변수처럼 값이 변하는 부분.
        log.info("ReplyRestController readAll postId = {}", postId);
        
        List<ReplyReadDto> list = replyService.readReplies(postId);
        log.info("ReplyRestController List<Dto> list = {}", list);
        log.info("@ of list size = ", list.size());
        
        return ResponseEntity.ok(list); // ok 결과는 성공입니다.
        // Java의 List 타입 객체가 JSON()
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
        
        dto.setReplyId(replyId); // DTO에 댓글 아이드를 저장.
        Integer result = replyService.update(dto);
        
        return ResponseEntity.ok(result);
    }
}
