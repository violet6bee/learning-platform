package shvalieva.learning_platform.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shvalieva.learning_platform.dto.QuizRequestDTO;
import shvalieva.learning_platform.dto.QuizResponseDTO;
import shvalieva.learning_platform.entity.CourseEntity;
import shvalieva.learning_platform.entity.QuizEntity;
import shvalieva.learning_platform.mapper.QuizMapper;
import shvalieva.learning_platform.repository.CourseRepository;
import shvalieva.learning_platform.repository.QuizRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class QuizService {

    private QuizRepository quizRepository;
    private QuizMapper quizMapper;
    private CourseRepository courseRepository;

    @Transactional(readOnly = true)
    public QuizResponseDTO getQuizById(Long id) {
        QuizEntity entity = quizRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Тест с идентифкатором " + id + " не найден."));
        return quizMapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    public List<QuizResponseDTO> getAllQuizzes() {
        return quizRepository.findAll().stream()
                .map(quizMapper::toDto)
                .toList();
    }

    @Transactional
    public QuizResponseDTO createQuiz(QuizRequestDTO quizDto) {
        CourseEntity course = courseRepository.findById(quizDto.getCourseId())
                .orElseThrow(() -> new EntityNotFoundException("Курс не найден"));
        QuizEntity entity = quizMapper.fromRequestDto(quizDto);
        entity.setCourse(course);
        QuizEntity saved = quizRepository.save(entity);
        return quizMapper.toDto(saved);
    }

    @Transactional
    public void deleteQuiz(Long id) {
        quizRepository.deleteById(id);
    }
}