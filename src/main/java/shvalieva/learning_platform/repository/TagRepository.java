package shvalieva.learning_platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shvalieva.learning_platform.entity.TagEntity;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, Long> {

}
