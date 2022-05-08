package com.projectdemo.springbootdemo.mapper;

import com.projectdemo.springbootdemo.entity.Article;
import org.apache.ibatis.annotations.Mapper;

/**
 * asus 梅锦涛
 * 2022/5/8
 *
 * @author mjt
 */
@Mapper
public interface ArticleMapper {

    public Article selectArticle(Integer id);

}
