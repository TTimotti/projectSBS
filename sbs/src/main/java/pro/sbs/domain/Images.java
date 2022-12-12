package pro.sbs.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "TEAM_IMAGES")
@SequenceGenerator(name = "TEAM_IMAGES_SEQ2_GEN", sequenceName = "TEAM_IMAGES_SEQ2", initialValue = 1, allocationSize = 1)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
public class Images {

    // 테이블의 PK 컬럼 설정
    // DB에 저장될 사진 번호...
    @Id
    @GeneratedValue(generator = "TEAM_IMAGES_SEQ2_GEN", strategy = GenerationType.SEQUENCE)
    private Integer fid;
    
    @Column(nullable = false)
    private Integer fileId;
    
    @Column(nullable = false)
    private String OriginalName;
    
    @Column(nullable = false)
    private String fileUrl;
    
    @Column(nullable = false)
    private String extension;
}
