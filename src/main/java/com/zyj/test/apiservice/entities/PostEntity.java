package com.zyj.test.apiservice.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Author: zhouyajun
 * Date: 2019-11-20
 */

@Entity
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private LocalDateTime createdDateTime;
    private String title;
    private String author;
    private String slug;
    private int viewCount;
    private String featuredImage;
    private String[] tags;
    @Size(max = 300)
    private String teaser;
    @Lob
    private String content;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CommentEntity> comments;

    public PostEntity setId(long id) {
        this.id = id;
        return this;
    }

    public PostEntity setAuthor(String author) {
        this.author = author;
        return this;
    }

    public PostEntity setComments(List<CommentEntity> comments) {
        this.comments = comments;
        return this;
    }

    public PostEntity setContent(String content) {
        this.content = content;
        return this;
    }

    public PostEntity setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
        return this;
    }

    public PostEntity setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
        return this;
    }

    public PostEntity setSlug(String slug) {
        this.slug = slug;
        return this;
    }

    public PostEntity setTags(String[] tags) {
        this.tags = tags;
        return this;
    }

    public PostEntity setTeaser(String teaser) {
        this.teaser = teaser;
        return this;
    }

    public PostEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public PostEntity setViewCount(int viewCount) {
        this.viewCount = viewCount;
        return this;
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getSlug() {
        return slug;
    }

    public int getViewCount() {
        return viewCount;
    }

    public String getFeaturedImage() {
        return featuredImage;
    }

    public String[] getTags() {
        return tags;
    }

    public String getTeaser() {
        return teaser;
    }

    public String getContent() {
        return content;
    }

    public List<CommentEntity> getComments() {
        return comments;
    }
}
