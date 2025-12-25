package shvalieva.learning_platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shvalieva.learning_platform.entity.QuizEntity;

@Repository
public interface QuizRepository extends JpaRepository<QuizEntity, Long> {
}
