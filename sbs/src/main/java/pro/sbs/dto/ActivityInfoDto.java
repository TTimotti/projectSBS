package pro.sbs.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pro.sbs.domain.MyActivityList;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ActivityInfoDto {

	
	private Integer activityId;
	private LocalDateTime startTime;
	private Integer budget;
	private String play;
	private String place;
	private Integer teamId;
	private List<MyActivityList> myActivityList;
	
	
}
