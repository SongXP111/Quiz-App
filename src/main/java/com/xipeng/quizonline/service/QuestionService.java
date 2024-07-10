package com.xipeng.quizonline.service;

import com.xipeng.quizonline.model.Question;
import com.xipeng.quizonline.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Service 注解表示这是一个服务类，标记服务层组件。
 * @RequiredArgsConstructor 自动生成包含 final 和 @NonNull 字段的构造函数。
 * 该类实现了 IQuestionService 接口中的方法。
 */
@Service
@RequiredArgsConstructor
public class QuestionService implements IQuestionService {

    // 自动注入 QuestionRepository，用于与数据库进行交互
    private final QuestionRepository questionRepository;

    /**
     * 创建新问题
     * @param question 要创建的问题对象
     * @return 保存的问题对象
     */
    @Override
    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

    /**
     * 获取所有问题
     * @return 问题列表
     */
    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    /**
     * 根据 ID 获取问题
     * @param id 问题的 ID
     * @return 包含问题的 Optional 对象，如果问题存在则包含问题对象，否则为空
     */
    @Override
    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.findById(id);
    }

    /**
     * 获取所有独特的科目
     * @return 科目列表
     */
    @Override
    public List<String> getAllSubjects() {
        return questionRepository.findDistinctSubject();
    }

    /**
     * 更新问题
     * @param id 问题的 ID
     * @param question 包含更新数据的问题对象
     * @return 更新后的问题对象
     * @throws ChangeSetPersister.NotFoundException 如果问题不存在
     */
    @Override
    public Question updateQuestion(Long id, Question question) throws ChangeSetPersister.NotFoundException {
        Optional<Question> theQuestion = this.getQuestionById(id);
        if (theQuestion.isPresent()) {
            Question questionToUpdate = theQuestion.get();
            questionToUpdate.setQuestion(question.getQuestion());
            questionToUpdate.setChoices(question.getChoices());
            questionToUpdate.setCorrectAnswers(question.getCorrectAnswers());
            return questionRepository.save(questionToUpdate);
        } else {
            throw new ChangeSetPersister.NotFoundException();
        }
    }

    /**
     * 删除问题
     * @param id 问题的 ID
     */
    @Override
    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    /**
     * 获取特定科目的指定数量的问题
     * @param numOfQuestions 问题数量
     * @param subject 科目
     * @return 问题列表
     *
     * Pageable：提供了一种方便的方式来实现分页和排序功能，使你可以轻松地处理大数据集的分页查询和显示
     */
    @Override
    public List<Question> getQuestionsForUser(Integer numOfQuestions, String subject) {
        Pageable pageable = PageRequest.of(0, numOfQuestions);
        return questionRepository.findBySubject(subject, pageable).getContent();
    }
}
