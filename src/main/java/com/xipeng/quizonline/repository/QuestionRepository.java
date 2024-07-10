package com.xipeng.quizonline.repository;

import com.xipeng.quizonline.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * QuestionRepository 接口，用于定义对 Question 实体的数据库操作。
 * 继承自 JpaRepository，提供基本的 CRUD 操作和分页支持。
 */
public interface QuestionRepository extends JpaRepository<Question, Long> {
    /**
     * 使用自定义的 JPQL 查询，获取所有独特的科目。
     *
     * @return 独特科目的列表。
     *
     * @Query 允许在 Spring Data JPA 的 Repository 接口方法上定义自定义的 JPQL 或 SQL 查询
     */
    @Query("SELECT DISTINCT q.subject FROM Question q")
    List<String> findDistinctSubject();

    /**
     * 根据科目获取分页问题列表。
     *
     * @param subject  科目名称。
     * @param pageable 分页信息。
     * @return 分页的 Question 对象列表。
     */
    Page<Question> findBySubject(String subject, Pageable pageable);
}
