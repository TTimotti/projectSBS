package pro.sbs.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.sbs.domain.Activity;
import pro.sbs.dto.ActivityCreateDto;
import pro.sbs.dto.ActivityInfoDto;
import pro.sbs.dto.ActivityUpdateDto;
import pro.sbs.dto.PostReadDto;
import pro.sbs.service.ActivityService;
import pro.sbs.service.PostService;
import pro.sbs.service.TeamService;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping
public class ActivityController {

    private final PostService postService;

    private final ActivityService activityService;
    
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/team/teamNoticePost")
    public ResponseEntity<List<PostReadDto>> teamNoticePost(Integer teamId) {

        log.info("teamNoticePost()");
        log.info("id={}", teamId);
        List<PostReadDto> post = postService.read(teamId);
        log.info("teamNoticePOst List<PostReadDto> post = {}", post);
        return ResponseEntity.ok(post);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping({ "/activity/detail", "/activity/modify" })
    public void detail(Integer id, Model model) {
        log.info("detail, modify  activityId = {}", id);
        Activity activity = activityService.readIndex(id);
        log.info("detail, modify  active = {}", activity);
        model.addAttribute(activity);
        model.addAttribute(id);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/activity/update")
    public String update(ActivityUpdateDto dto) {
        log.info("update(dto = {})", dto);
        Integer activityId = activityService.update(dto);

        log.info("activityId = {}", activityId);

        return "redirect:/activity/detail?id=" + dto.getActivityId();
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/activity/delete")
    public String delete(Integer id, RedirectAttributes attrs) {

        log.info("delete(id={})", id);

        Integer activityId = activityService.delete(id);
        attrs.addFlashAttribute("deletedPostId", activityId);
        log.info("postController delete postId = {}", activityId);

        return "redirect:/";
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/activity/create")
    public void create(Integer id, Model model) {
        log.info("ActivityController create() teamId = {}", id);
        model.addAttribute("id", id);
    }

    /**
     * 활동 생성 기능
     * @param id
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/activity/create")
    public String create(Integer id, ActivityCreateDto dto) {

        log.info("ActivityController create() dto2 = {}", dto);
        Activity entity = activityService.create(dto);
        log.info("ActivityController create() entity = {}", entity);
        Integer teamId = dto.getTeamId();

        log.info("id={}", teamId);

        return "redirect:/team/teamActivity?teamId=" + teamId;
    }
    
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/team/list") // 요청 URL/방식 매핑.
    public ResponseEntity<List<Activity>> home2(Model model) {
        log.info("home()");

        List<Activity> list = activityService.readByStartTime();

        model.addAttribute("list", list);

        return ResponseEntity.ok(list);
    }

    /*
    *  날짜가 지난 종료된 활동 목록 불러오는 기능.
    */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/team/pastList") // 요청 URL/방식 매핑.
    public ResponseEntity<List<Activity>> home3(Integer teamId, Model model) {
        // 현재 이전 활동 내역 조회
        List<Activity> list1 = activityService.readAcTimePast(teamId);
        model.addAttribute("pastList", list1);
        return ResponseEntity.ok(list1);
    }

    /*
    *  진행중인 활동 목록 불러오는 기능.
    */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/team/progressList") // 요청 URL/방식 매핑.
    public ResponseEntity<List<Activity>> home4(Integer teamId, Model model) {
        // 기간이 지나지 않은 활동 내역 조회
        List<Activity> list2 = activityService.readAcTimeProgress(teamId);
        model.addAttribute("progressList", list2);
        return ResponseEntity.ok(list2);
    }

    /**
     * 캘린더에서 날짜 선택시 input text에 있는 text값을 읽어와 String 타입으로 날짜에 해당되는 활동들을 검색하는 기능.
     */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/scTimeSearch")
    public ResponseEntity<List<Activity>> scTimeSearch(String startTime, Integer teamId) {

        log.info("scTimeSearch()");

        log.info("scheduleTime={}", startTime);

        List<Activity> list = new ArrayList<>();

        list = activityService.scTimeRead(startTime, teamId);

        return ResponseEntity.ok(list);
    }

    /**
     * 활동 아이디를 테이블에서 받아 해당 아이디의 활동 정보를 받아서 팝업 레이어에 전달해주는 기능.
     */
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/activityInfo")
    public ResponseEntity<ActivityInfoDto> myActivityList(Integer activityId) {

        log.info("MyActivityList()");
        ActivityInfoDto info = activityService.myActivityListJoinMember(activityId);
        return ResponseEntity.ok(info);
    }

}
