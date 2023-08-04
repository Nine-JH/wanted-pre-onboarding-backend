package me.jh9.wantedpreonboarding.article.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;

@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id")
    private Long memberId;
    private String title;
    private String content;

    protected Article() {}

    private Article(Long id, Long memberId, String title, String content) {
        this.id = id;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
    }

    public static Article create(Long memberId, String title, String content) {
        return new Article(null, memberId, title, content);
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public boolean isWriter(Long targetMemberId) {
        return memberId.equals(targetMemberId);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Article article)) {
            return false;
        }

        return id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
