package com.zyj.test.apiservice.controllers;

import com.zyj.test.apiservice.dto.BlogDTO;
import com.zyj.test.apiservice.dto.BlogsDTO;
import com.zyj.test.apiservice.dto.ReferenceDTO;
import com.zyj.test.apiservice.entities.BlogEntity;
import com.zyj.test.apiservice.entities.PostEntity;
import com.zyj.test.apiservice.repository.BlogRepository;
import com.zyj.test.apiservice.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: zhouyajun
 * Date: 2019-11-20
 */

@RestController
@RequestMapping(value = "/blogs", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
public class BlogController {

    @Autowired
    private BlogRepository blogRepo;
    @Autowired
    private PostRepository postRepo;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity createBlog(UriComponentsBuilder uriBuilder, @RequestBody BlogDTO blogDto) {
        BlogEntity blog = mapToEntry(blogDto);
        blogRepo.save(blog);

        UriComponents uriComponents =
                uriBuilder.path("blogs/{id}").buildAndExpand(blog.getId());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponents.toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    private BlogEntity mapToEntry(BlogDTO blogDto) {
        return new BlogEntity()
                .setUrl(blogDto.getUrl())
                .setDescription(blogDto.getDescription())
                .setName(blogDto.getName());
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public BlogsDTO getAll() {
        Collection<BlogEntity> blogs = (Collection<BlogEntity>) blogRepo.findAll();
        BlogsDTO blogList = mapToDTO(blogs);
        return blogList;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public BlogDTO getBlog(@PathVariable("id") Long id) {
        BlogEntity blog = blogRepo.findById(id).get();
        BlogDTO blogDTO = mapToDTO(blog);
        return blogDTO;
    }

    @RequestMapping(value = "/{blogId}/posts", method = RequestMethod.GET)
    public List<ReferenceDTO> getAllBlogPosts(@PathVariable("blogId") Long blogId) {
        BlogEntity blog = blogRepo.findById(blogId).get();
        List<PostEntity> posts = blog.getPosts();
        return mapToDTO(blog.getId(), posts);
    }

    //let's ignore the blogId for the sake of simplicity, because the postId is unique and and not context is necessary
    @RequestMapping(value = "/{blogId}/posts/{postId}", method = RequestMethod.GET)
    public PostEntity getBlogPost(@PathVariable("blogId") Long blogId, @PathVariable("postId") Long postId) {
        PostEntity post = postRepo.findById(postId).get();
        return post;
    }

    private BlogDTO mapToDTO(BlogEntity blog) {
        List<PostEntity> posts = blog.getPosts();
        List<ReferenceDTO> postsDTO = mapToDTO(blog.getId(), posts);
        BlogDTO blogDTO = new BlogDTO()
                .setName(blog.getName())
                .setDescription(blog.getDescription())
                .setPosts(postsDTO)
                .setUrl(blog.getUrl());
        return blogDTO;
    }

    private List<ReferenceDTO> mapToDTO(long blogId, List<PostEntity> posts) {
        return posts.stream()
                .map(post -> mapToDTO(blogId, post))
                .collect(Collectors.toList());
    }

    private ReferenceDTO mapToDTO(long blogId, PostEntity post){
        long id = post.getId();
        String title = post.getTitle();
        String href = "/blogs/" + blogId + "/posts/" + id;
        ReferenceDTO ref = new ReferenceDTO().setId(id).setName(title).setHref(href);
        return ref;
    }

    private BlogsDTO mapToDTO(Collection<BlogEntity> blogs) {
        List<ReferenceDTO> blogRefs = blogs.stream()
                .map(blogEntry -> {
                    String name = blogEntry.getName();
                    long id = blogEntry.getId();
                    String href = "/blogs/" + id;
                    ReferenceDTO ref = new ReferenceDTO().setId(id).setName(name).setHref(href);
                    return ref;
                })
                .collect(Collectors.toList());
        return new BlogsDTO()
                .setBlogs(blogRefs)
                .setOffset(0)
                .setLimit(50)
                .setCount(blogs.size());
    }
}
