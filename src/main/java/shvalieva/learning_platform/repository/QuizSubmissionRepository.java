package shvalieva.learning_platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shvalieva.learning_platform.entity.QuizSubmissionEntity;

import java.util.List;

@Repository
public interface QuizSubmissionRepository extends JpaRepository<QuizSubmissionEntity, Long> {
    List<QuizSubmissionEntity> findAllByQuizId(Long quizId);
    List<QuizSubmissionEntity> findAllByStudentId(Long studentId);
}
