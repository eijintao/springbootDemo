package com.projectdemo.springbootdemo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * asus 梅锦涛
 * 2022/5/8
 *
 * @author mjt
 */
@Data
@Entity(name = "t_comment") // 设置ORM实体类，并指定映射的表名
public class RepositoryComment {

    @Id // 表明映射对应的主键id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 设置主键自增策略
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
    @Column(name = "a_id") //指定映射的表字段名
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
