package com.xipeng.quizonline.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/*
* Question类
* @Getter/@Setter：自动为字段生成Getter和Setter方法
* @Entity：标记一个类为持久化实体。当一个类被标为实体时，JPA将其映射到数据库中的一张表，并将类的实例映射到表中的一行
* */
@Getter
@Setter
@Entity
public class Question {
    /*
    * @Id：标记实体类的主键字段，每个实体类必须有一个主键字段，用于唯一标识实体对象
    * @GeneratedValue：配置主键字段的生成策略
    * strategy：指定了主键生成的策略
    * GenerationType.IDENTITY：指定主键由数据库自动生成
    * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String question;

    @NotBlank
    private String subject;

    @NotBlank
    private String questionType;

    /*
    * @NotBlank：用于验证字符串字段不能为空、空字符串或只包含空白字符
    * @ElementCollection：用于将集合类型的字段映射到数据库表中的多个行，适用于基本类型或可嵌入类的集合
    */
    @NotBlank
    @ElementCollection
    private List<String> choices;

    @NotBlank
    @ElementCollection
    private List<String> correctAnswers;
}
