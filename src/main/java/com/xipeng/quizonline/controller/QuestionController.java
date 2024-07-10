package com.xipeng.quizonline.controller;

import com.xipeng.quizonline.model.Question;
import com.xipeng.quizonline.service.IQuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED; // HTTP 状态码 201

/**
 * @RestController 标记该类为 Spring MVC 的控制器，并且每个方法的返回值都会被自动转换为 JSON 格式的响应体。
 * @RequestMapping 将该控制器的所有请求映射到“/api/quizzes”路径下。
 * @RequiredArgsConstructor Lombok注解，自动生成包含 final 字段的构造函数，用于依赖注入。
 * 依赖：一个对象依赖于另一个对象才能完成其功能。
 * 注入：将依赖对象传递给需要它的对象的过程。
 */
@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
public class QuestionController {
    private final IQuestionService questionService;
    /**
     * 将 HTTP POST 请求映射到“/create-new-question”端点，用于创建新问题。
     *
     * @param question 请求体中的问题对象，使用 @Valid 进行验证。
     * @return 返回包含已创建问题的响应实体，状态码为 201 (Created)。
     *
     * @PostMapping 将 HTTP POST 请求映射到“/create-new-question”端点，用于创建新问题
     * @Valid 用于验证请求体
     * @RequestBody 用于将请求体绑定到方法参数question
     * ResponseEntity：用于构建HTTP响应
     */
    @PostMapping("/create-new-question")
    public ResponseEntity<Question> createQuestion(@Valid @RequestBody Question question) {
        Question createdQuestion = questionService.createQuestion(question);
        return ResponseEntity.status(CREATED).body(createdQuestion);
    }

    /**
     * 将 HTTP GET 请求映射到“/all-questions”端点，用于获取所有问题。
     *
     * @return 返回包含问题列表的响应实体，状态码为 200 (OK)。
     */
    @GetMapping("/all-questions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        List<Question> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }
}
