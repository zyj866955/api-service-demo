package com.zyj.test.apiservice.repository;

import com.zyj.test.apiservice.entities.BlogEntity;
import com.zyj.test.apiservice.entities.CommentEntity;
import com.zyj.test.apiservice.entities.PostEntity;
import de.svenjacobs.loremipsum.LoremIpsum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 在项目服务启动的时候就去加载一些数据或做一些事情，只要实现CommandLineRunner接口
 * Spring Boot应用程序在启动后，会遍历CommandLineRunner接口的实例并运行它们的run方法
 */

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DatabaseInitializer.class);
    LoremIpsum loremIpsum = new LoremIpsum();

    @Autowired
    private BlogRepository repo;

    @Override
    public void run(String... strings) throws Exception {
        log.info("Creating dummy data.");

        List<BlogEntity> blogs = createBlogs(6);
        repo.saveAll(blogs);
    }

    private List<CommentEntity> createComments(int amount) {
        return Stream.generate(() -> new CommentEntity()
                .setAuthor(createRandomName())
                .setCreatedDateTime(LocalDateTime.now())
                .setContent(createRandomCommentText())
        )
                .limit(amount)
                .collect(Collectors.toList());
    }

    private List<PostEntity> createPosts(int amount) {
        return Stream.generate(() -> {
                    String title = createRandomPostTitle();
                    String slug = toSlug(title);
                    return new PostEntity()
                            .setAuthor(createRandomName())
                            .setCreatedDateTime(LocalDateTime.now())
                            .setTeaser(loremIpsum.getParagraphs(1))
                            .setContent(loremIpsum.getParagraphs(7))
                            .setComments(createComments(5))
                            .setTitle(title)
                            .setSlug(slug)
                            .setViewCount(random.nextInt(1000))
                            .setTags(createRandomTags())
                            .setFeaturedImage(slug + ".png");
                }
        )
                .limit(amount)
                .collect(Collectors.toList());
    }

    private List<BlogEntity> createBlogs(int amount) {
        return Stream.generate(() -> {
                    String title = createRandomBlogTitle();
                    return new BlogEntity()
                            .setName(title)
                            .setDescription(loremIpsum.getParagraphs(1))
                            .setPosts(createPosts(9))
                            .setUrl("http://www." + toSlug(title) + ".com");
                }
        )
                .limit(amount)
                .collect(Collectors.toList());
    }

    private Random random = new Random();

    private List<String> firstNames = Arrays.asList("Max", "Paul", "Tim", "Nils", "Angela", "Maria", "Lea", "Sven", "Helena");
    private List<String> lastNames = Arrays.asList("Müller", "Schmidt", "Merkel", "Henkel", "Lange", "Marx", "Heine", "Fischer", "Bauer");

    private String createRandomName() {
        String firstName = getRandomElement(firstNames);
        String lastName = getRandomElement(lastNames);
        return firstName + " " + lastName;
    }

    private List<String> comments = Arrays.asList("Cool!", "Awesome!", "Thanks!", "Well done!", "That's terrible.", "I like nuts.");

    private String createRandomCommentText() {
        return getRandomElement(comments);
    }

    private List<String> parts1 = Arrays.asList("Creating", "Analysing", "Designing", "Implementing", "Investigating", "Ignoring");
    private List<String> parts2 = Arrays.asList("Performance", "Footprint", "Code", "Quality", "Architecture", "Speed", "Test Code", "Communication");
    private List<String> parts3 = Arrays.asList("of Microservices", "of Docker", "of Spring Boot", "of a Vaadin application", "of RESTful Services", "of SOAP Services", "of angular.js", "of react.js");

    private String createRandomPostTitle() {
        String part1 = getRandomElement(parts1);
        String part2 = getRandomElement(parts2);
        String part3 = getRandomElement(parts3);
        return part1 + " " + part2 + " " + part3;
    }

    private List<String> blogTitles = Arrays.asList("Java Ecosystem", "Web Development", "Web Architecture", "Software Architecture", "Software Archaeology", "Test Driven Development", "Model Driven Development", "Software Craftsmanship", "Build and Delivery");

    private String createRandomBlogTitle() {
        return getRandomElement(blogTitles);
    }

    private String getRandomElement(List<String> list) {
        return list.get(random.nextInt(list.size()));
    }

    private List<String> tags = Arrays.asList("Java", "Web", "Build", "Architecture", "Development", "Integration", "Modelling", "REST", "Scalability", "Cloud", "SOAP", "HTTP", "Swagger", "Best Practice", "Test", "TDD", "Vaadin", "Clean Code", "HATEOAS", "Spring Boot");

    private String[] createRandomTags() {
        return new String[]{getRandomElement(tags), getRandomElement(tags), getRandomElement(tags)};
//        return Arrays.asList(getRandomElement(tags), getRandomElement(tags), getRandomElement(tags));
    }

    public String toSlug(String blogTitle) {
        return blogTitle.replaceAll(" ", "_").toLowerCase();
    }
}

