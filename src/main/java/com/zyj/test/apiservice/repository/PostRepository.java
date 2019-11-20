package com.zyj.test.apiservice.repository;


import com.zyj.test.apiservice.entities.PostEntity;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<PostEntity, Long> {

}
