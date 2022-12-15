package pro.sbs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.sbs.domain.Reply;
import pro.sbs.dto.ReplyReadDto;
import pro.sbs.dto.ReplyRegisterDto;
import pro.sbs.repository.ReplyRepository;

@Slf4j
@Service // 스프링 컨텍스에 Service 컴포넌트로 등록 -> 의조넝 ㅈ주입이 가능해짐.
@RequiredArgsConstructor
public class ReplyService {

//    private final PostRepository postRepository;
    
    private final ReplyRepository replyRepository;
    
    /**
     * 댓글 생성 메서드. 
     * @param dto
     * @return
     * @author 추
     */
    public Integer create(ReplyRegisterDto dto) {
        log.info("ReplyService dto ={}", dto);
        
        // 종속시 밑에도 바꿔야함.
//        Post post = postRepository.findById(dto.getPostId()).get();
//        log.info("ReplyService create postId ={}", post);
        
        Reply reply = Reply.builder().postId(dto.getPostId())
                .replyText(dto.getReplyText())
                .writer(dto.getWriter())
                .build();
        
        log.info("ReplyService Reply = {}", reply);
        
        reply = replyRepository.save(reply);
        
        return reply.getReplyId();
    }
    
    /**
     * 게시물마다 댓글 읽어오는 메서드 이것도 굳이 dto를 쓴 이유는 종속때 필요하기 때문 
     * 그냥 List<Reply> 쓰는거랑 똑같아서 그냥 이걸로 쓰면됌.
     * @param postId
     * @return
     */
    public List<ReplyReadDto> readReplies(Integer postId) {
        log.info("ReplyService postId = {}", postId);
        
        List<Reply> list = replyRepository.selectAllReplies(postId);
        
        log.info("ReplyService List<Reply> list = {}", list);
        
        // List<Reply>를 List<ReplyReadDto> 변환해서 리턴.
        return list.stream().map(ReplyReadDto::fromEntity).toList();
    }

    /**
     * 댓글 하나만 가져오는 메서드 수정 삭제시 모달로 넘기기위함.
     * @param replyId
     * @return
     */
    public ReplyReadDto readReply(Integer replyId) {
        log.info("ReplyService readReply(replyId={})", replyId);
        
        Reply entity = replyRepository.findById(replyId).get();
        
        return ReplyReadDto.fromEntity(entity);
    }

    public Integer delete(Integer replyId) {
        log.info("ReplyService delete(replyId={})", replyId);
        
        replyRepository.deleteById(replyId);
        
        return replyId;
    }

//    @Transactional
//    public Integer update(ReplyUpdateDto dto) {
//        log.info("ReplyService update(dto={})", dto);
// 
//        Reply entity = replyRepository.findById(dto.getReplyId()).get();
//        
//        entity.update(dto.getReplyText());
//        
//        
//        return entity.getReplyId(); 
//    }
}
