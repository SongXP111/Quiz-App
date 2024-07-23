package com.xipeng.quizonline.controller;

import com.xipeng.quizonline.model.Question;
import com.xipeng.quizonline.service.IQuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    /**
     * 将 HTTP GET 请求映射到“/question/{id}”端点，用于根据 ID 获取特定的问题。
     *
     * @param id 要获取的问题的 ID。
     * @return 返回包含问题的响应实体，状态码为 200 (OK)。
     * @throws ChangeSetPersister.NotFoundException 如果指定 ID 的问题不存在。
     *
     * @PathVariable 是一个用于处理 URL 路径变量的注解，常用于 RESTful API的控制器方法中，可以将 URL 路径中的动态部分绑定到方法参数上
     */
    @GetMapping("/question/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        Optional<Question> question = questionService.getQuestionById(id);
        if (question.isPresent()) {
            return ResponseEntity.ok(question.get());
        } else {
            throw new ChangeSetPersister.NotFoundException();
        }
    }

    /**
     * 将 HTTP PUT 请求映射到“/{id}/update”端点，用于更新特定的问题。
     *
     * @param id 要更新的问题的 ID。
     * @param question 包含更新数据的问题对象。
     * @return 返回包含已更新问题的响应实体，状态码为 200 (OK)。
     *
     * @PutMapping 将 HTTP PUT 请求映射到“/{id}/update”端点，用于更新特定的问题。
     */
    @PutMapping("/question/{id}/update")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id, @RequestBody Question question) throws ChangeSetPersister.NotFoundException {
        Question updatedQuestion = questionService.updateQuestion(id, question);
        return ResponseEntity.ok(updatedQuestion);
    }

    /**
     * 将 HTTP DELETE 请求映射到“/{id}/delete”端点，用于删除特定的问题。
     *
     * @param id 要删除的问题的 ID。
     * @return 返回状态码为 204 (No Content) 的响应实体。
     *
     * @DeleteMapping 将 HTTP DELETE 请求映射到“/{id}/delete”端点，用于删除特定的问题。
     */

    @DeleteMapping("/question/{id}/delete")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * 将 HTTP GET 请求映射到“/subjects”端点，用于获取所有独特的科目。
     *
     * @return 返回包含科目列表的响应实体，状态码为 200 (OK)。
     */
    @GetMapping("/subjects")
    public ResponseEntity<List<String>> getAllSubjects() {
        List<String> subjects = questionService.getAllSubjects();
        return ResponseEntity.ok(subjects);
    }

    /**
     * 将 HTTP GET 请求映射到“/fetch-question-for-user”端点，用于获取特定科目的指定数量的随机问题。
     *
     * @param numOfQuestions 要获取的问题数量。
     * @param subject 问题所属的科目。
     * @return 返回包含随机问题列表的响应实体，状态码为 200 (OK)。
     *
     * @RequestParam 用于将请求参数绑定到方法参数上
     */
    @GetMapping("/quiz/fetch-question-for-user")
    public ResponseEntity<List<Question>> getQuestionsForUser(
            @RequestParam Integer numOfQuestions, @RequestParam String subject) {
        List<Question> allQuestions = questionService.getQuestionsForUser(numOfQuestions, subject);
        List<Question> mutableQuestions = new ArrayList<>(allQuestions);
        Collections.shuffle(mutableQuestions);

        int availableQuestions = Math.min(mutableQuestions.size(), numOfQuestions);
        List<Question> randomQuestions = mutableQuestions.subList(0, availableQuestions);
        return ResponseEntity.ok(randomQuestions);
    }
}
