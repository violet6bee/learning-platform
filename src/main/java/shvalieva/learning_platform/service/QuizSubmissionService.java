package shvalieva.learning_platform.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shvalieva.learning_platform.dto.QuizSubmissionRequestDTO;
import shvalieva.learning_platform.dto.QuizSubmissionResponseDTO;
import shvalieva.learning_platform.entity.QuizEntity;
import shvalieva.learning_platform.entity.QuizSubmissionEntity;
import shvalieva.learning_platform.entity.UserEntity;
import shvalieva.learning_platform.mapper.QuizSubmissionMapper;
import shvalieva.learning_platform.repository.QuizRepository;
import shvalieva.learning_platform.repository.QuizSubmissionRepository;
import shvalieva.learning_platform.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class QuizSubmissionService {

    private QuizSubmissionRepository quizSubmissionRepository;
    private QuizRepository quizRepository;
    private UserRepository userRepository;
    private QuizSubmissionMapper quizSubmissionMapper;

    @Transactional(readOnly = true)
    public QuizSubmissionResponseDTO getById(Long id) {
        QuizSubmissionEntity entity = quizSubmissionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Quiz submission not found with id " + id));
        return quizSubmissionMapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    public List<QuizSubmissionResponseDTO> getByQuiz(Long quizId) {
        return quizSubmissionRepository.findAllByQuizId(quizId)
                .stream()
                .map(quizSubmissionMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<QuizSubmissionResponseDTO> getByStudent(Long studentId) {
        return quizSubmissionRepository.findAllByStudentId(studentId)
                .stream()
                .map(quizSubmissionMapper::toDto)
                .toList();
    }

    @Transactional
    public QuizSubmissionResponseDTO create(QuizSubmissionRequestDTO dto) {
        QuizEntity quiz = quizRepository.findById(dto.getQuizId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Викторина с id: " + dto.getQuizId() + " не найдена"));
        UserEntity student = userRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Пользователь с id:  " + dto.getStudentId() + " не найден"));
        QuizSubmissionEntity entity = quizSubmissionMapper.fromRequestDto(dto);
        entity.setQuiz(quiz);
        entity.setStudent(student);
        if (entity.getTakenAt() == null) {
            entity.setTakenAt(LocalDateTime.now());
        }
        QuizSubmissionEntity saved = quizSubmissionRepository.save(entity);
        return quizSubmissionMapper.toDto(saved);
    }

    @Transactional
    public void delete(Long id) {
        quizSubmissionRepository.deleteById(id);
    }
}
