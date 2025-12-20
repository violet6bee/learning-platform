package shvalieva.learning_platform.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shvalieva.learning_platform.dto.ModuleRequestDTO;
import shvalieva.learning_platform.dto.ModuleResponseDTO;
import shvalieva.learning_platform.service.ModuleService;

@RestController
@RequestMapping("/modules")
@AllArgsConstructor
public class ModuleController {

    private ModuleService moduleService;

    @GetMapping("/{id}")
    public ResponseEntity<ModuleResponseDTO> getModuleById(@PathVariable Long id) {
        ModuleResponseDTO dto = moduleService.getModuleById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<ModuleResponseDTO> createModule(@Valid @RequestBody ModuleRequestDTO dto) {
        ModuleResponseDTO module = moduleService.createModule(dto);
        return ResponseEntity.ok(module);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModule(@PathVariable Long id) {
        moduleService.deleteModule(id);
        return ResponseEntity.noContent().build();
    }
}
