package pro.sbs.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
@Entity(name = "POSTS")
@SequenceGenerator(name = "POSTS_SEQ_GEN", sequenceName = "POST_SEQ", initialValue = 1, allocationSize = 1)
public class Post extends BaseTimeEntity {
    // field
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POSTS_SEQ_GEN")
//    @Column(nullable = false, name = "post_id")
    private Integer postId;

    @ManyToOne(fetch = FetchType.LAZY)
//    @Column(nullable = false, name = "team_id")
    private Team team;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false, length = 1000)
    private String content;
    @Column(nullable = false)
    private String author;

    public Post update(String title, String content) {
        this.title = title;
        this.content = content;

        return this;
    }

    public Post createDto(Team teamId) {

        this.team = teamId;

        return this;
    }

}
