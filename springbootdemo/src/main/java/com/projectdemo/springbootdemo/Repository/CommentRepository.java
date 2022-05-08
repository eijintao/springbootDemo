package com.projectdemo.springbootdemo.Repository;

import com.projectdemo.springbootdemo.entity.RepositoryComment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * asus 梅锦涛
 * 2022/5/8
 *
 * @author mjt
 */
public interface CommentRepository extends JpaRepository<RepositoryComment,Integer> {
}
