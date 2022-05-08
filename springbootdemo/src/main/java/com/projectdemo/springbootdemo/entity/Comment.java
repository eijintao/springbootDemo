package com.projectdemo.springbootdemo.entity;

import lombok.Data;

import java.util.Date;

/**
 * asus 梅锦涛
 * 2022/5/8
 *
 * @author mjt
 */
@Data
public class Comment {

    /**
     * 评论id
     */
    private Integer id;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论作者
     */
    private String author;

    /**
     * 关联的文章id
     */
    private Integer aId;

    /**
     * 数据有效状态，0-有效，1-无效
     */
    private String status;

    /**
     * 开始时间
     */
    private Date createtime;

    /**
     * 更新时间
     */
    private Date updatetime;

}
