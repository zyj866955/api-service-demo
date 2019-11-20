package com.zyj.test.apiservice.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Author: zhouyajun
 * Date: 2019-11-20
 */

@Entity
public class BlogEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String name;
    @Size(max=300)
    private String description;
    private String url;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PostEntity> posts;

    public long getId() {
        return id;
    }

    public BlogEntity setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public BlogEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public BlogEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public BlogEntity setUrl(String url) {
        this.url = url;
        return this;
    }

    public List<PostEntity> getPosts() {
        return posts;
    }

    public BlogEntity setPosts(List<PostEntity> posts) {
        this.posts = posts;
        return this;
    }
}
