package com.projectdemo.springbootdemo.mapper;

import com.projectdemo.springbootdemo.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * asus 梅锦涛
 * 2022/5/8
 *
 * @author mjt
 */
@Mapper
public interface CommentMapper {

    @Select("SELECT * FROM t_comment WHERE id =#{id}")
    public Comment findById(Integer id);
}
