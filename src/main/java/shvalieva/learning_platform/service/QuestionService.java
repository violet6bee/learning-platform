package shvalieva.learning_platform.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shvalieva.learning_platform.dto.QuestionRequestDTO;
import shvalieva.learning_platform.dto.QuestionResponseDTO;
import shvalieva.learning_platform.entity.QuestionEntity;
import shvalieva.learning_platform.entity.QuizEntity;
import shvalieva.learning_platform.mapper.QuestionMapper;
import shvalieva.learning_platform.repository.QuestionRepository;
import shvalieva.learning_platform.repository.QuizRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class QuestionService {

    private QuestionRepository questionRepository;
    private QuizRepository quizRepository;
    private QuestionMapper questionMapper;

    @Transactional(readOnly = true)
    public QuestionResponseDTO getById(Long id) {
        QuestionEntity entity = questionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(" Вопрос не найден"));
        return questionMapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    public List<QuestionResponseDTO> getByQuizId(Long quizId) {
        return questionRepository.findByQuizId(quizId).stream()
                .map(questionMapper::toDto)
                .toList();
    }

    @Transactional
    public QuestionResponseDTO createQuestion(QuestionRequestDTO dto) {
        QuizEntity quiz = quizRepository.findById(dto.getQuizId())
                .orElseThrow(() -> new EntityNotFoundException("Викторина не найдена"));
        QuestionEntity entity = questionMapper.fromRequestDto(dto);
        entity.setQuiz(quiz);
        QuestionEntity saved = questionRepository.save(entity);
        return questionMapper.toDto(saved);
    }

    @Transactional
    public void deleteQuestion(Long id) {
        if (!questionRepository.existsById(id)) {
            throw new EntityNotFoundException("Вопрос не найден");
        }
        questionRepository.deleteById(id);
    }
}
