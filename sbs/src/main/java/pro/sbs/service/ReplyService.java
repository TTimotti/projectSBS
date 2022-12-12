package pro.sbs.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.sbs.domain.Post;
import pro.sbs.domain.Reply;
import pro.sbs.dto.ReplyReadDto;
import pro.sbs.dto.ReplyRegisterDto;
import pro.sbs.dto.ReplyUpdateDto;
import pro.sbs.repository.PostRepository;
import pro.sbs.repository.ReplyRepository;

@Slf4j
@Service // 스프링 컨텍스에 Service 컴포넌트로 등록 -> 의조넝 ㅈ주입이 가능해짐.
@RequiredArgsConstructor
public class ReplyService {

    private final PostRepository postRepository;
    
    private final ReplyRepository replyRepository;
    
    public Integer create(ReplyRegisterDto dto) {
        log.info("ReplyService dto ={}", dto);
        
        Post post = postRepository.findById(dto.getPostId()).get();
        
        log.info("ReplyService create postId ={}", post);
        
        Reply reply = Reply.builder().post(post)
                .replyText(dto.getReplyText())
                .writer(dto.getWriter())
                .build();
        
        log.info("ReplyService Reply = {}", reply);
        
        reply = replyRepository.save(reply);
        
        return reply.getReplyId();
    }
    
    public List<ReplyReadDto> readReplies(Integer postId) {
        log.info("ReplyService postId = {}", postId);
        
        List<Reply> list = replyRepository.selectAllReplies(postId);
        
        log.info("ReplyService List<Reply> list = {}", list);
        
        // List<Reply>를 List<ReplyReadDto> 변환해서 리턴.
        return list.stream().map(ReplyReadDto::fromEntity).toList();
    }

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

    @Transactional
    public Integer update(ReplyUpdateDto dto) {
        log.info("ReplyService update(dto={})", dto);
        
        // 수정하려는 댓글 아이디로 댓글 엔터티 객체를 검색.
        Reply entity = replyRepository.findById(dto.getReplyId()).get();
        // 데이터베이스 테이블에서 검색한 엔터티 객체를 수정.
        entity.update(dto.getReplyText());
        // @Transactional이 적용된 경우에 메서드 실행이 끝날 때 DB에 자동으로 save됨.
        
        return entity.getReplyId(); // 수정한 엔터티의 아이디를 리턴.
    }
}
