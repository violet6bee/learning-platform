package shvalieva.learning_platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shvalieva.learning_platform.entity.QuestionEntity;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
    List<QuestionEntity> findByQuizId(Long quizId);
    List<QuestionEntity> findAllByQuizId(Long quizId);
}
