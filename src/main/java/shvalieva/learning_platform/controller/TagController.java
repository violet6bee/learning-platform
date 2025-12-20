package shvalieva.learning_platform.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shvalieva.learning_platform.dto.TagRequestDTO;
import shvalieva.learning_platform.dto.TagResponseDTO;
import shvalieva.learning_platform.service.TagService;

import java.util.List;

@RestController
@RequestMapping("/tags")
@AllArgsConstructor
public class TagController {

    private TagService tagService;

    @GetMapping
    public List<TagResponseDTO> getAllTags() {
        return tagService.getAllTags();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagResponseDTO> getTagById(@PathVariable("id") Long id) {
        TagResponseDTO tagResponseDTO = tagService.getTagById(id);
        return ResponseEntity.ok(tagResponseDTO);
    }

    @PostMapping
    public ResponseEntity<TagResponseDTO> createTag(@Valid @RequestBody TagRequestDTO tagRequestDTO) {
        TagResponseDTO createdTag = tagService.createTag(tagRequestDTO);
        return ResponseEntity.ok(createdTag);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagResponseDTO> updateTag(@PathVariable("id") Long id, @Valid @RequestBody TagRequestDTO tagRequestDTO) {
        TagResponseDTO updatedTag = tagService.updateTag(id, tagRequestDTO);
        return ResponseEntity.ok(updatedTag);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable("id") Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}
