package com.zyj.test.apiservice.repository;


import com.zyj.test.apiservice.entities.BlogEntity;
import org.springframework.data.repository.CrudRepository;

public interface BlogRepository extends CrudRepository<BlogEntity, Long> {

}