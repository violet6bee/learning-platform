package shvalieva.learning_platform.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import shvalieva.learning_platform.dto.TagRequestDTO;
import shvalieva.learning_platform.dto.TagResponseDTO;
import shvalieva.learning_platform.entity.TagEntity;
import shvalieva.learning_platform.mapper.TagMapper;
import shvalieva.learning_platform.repository.TagRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    public List<TagResponseDTO> getAllTags() {
        return tagRepository.findAll().stream()
                .map(tagMapper::toDto)
                .collect(Collectors.toList());
    }

    public TagResponseDTO getTagById(Long id) {
        TagEntity entity = tagRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Тег с id: " + id + " не найден"));
        return tagMapper.toDto(entity);
    }

    public TagResponseDTO createTag(TagRequestDTO tagRequestDTO) {
        TagEntity entity = tagMapper.fromRequestDto(tagRequestDTO);
        entity.setId(null);
        TagEntity savedEntity = tagRepository.save(entity);
        return tagMapper.toDto(savedEntity);
    }

    public TagResponseDTO updateTag(Long id, TagRequestDTO tagRequestDTO) {
        TagEntity entity = tagRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Тег с id: " + id + " не найден"));
        entity.setName(tagRequestDTO.getName());
        tagRepository.save(entity);
        return tagMapper.toDto(entity);
    }

    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}
