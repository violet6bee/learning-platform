package shvalieva.learning_platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import shvalieva.learning_platform.entity.LessonEntity;

@Repository
public interface LessonRepository extends JpaRepository<LessonEntity, Long> {

    @Modifying
    @Query("DELETE FROM LessonEntity l WHERE l.id = :id")
    void deleteById(@Param("id") Long id);
}
