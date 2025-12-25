package shvalieva.learning_platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shvalieva.learning_platform.entity.AnswerOptionEntity;

import java.util.List;

@Repository
public interface AnswerOptionRepository extends JpaRepository<AnswerOptionEntity, Long> {
    List<AnswerOptionEntity> findByQuestionId(Long questionId);
    List<AnswerOptionEntity> findAllByQuestionId(Long questionId);
}
