package shvalieva.learning_platform.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shvalieva.learning_platform.dto.ProfileRequestDTO;
import shvalieva.learning_platform.dto.ProfileResponseDTO;
import shvalieva.learning_platform.entity.ProfileEntity;
import shvalieva.learning_platform.service.ProfileService;

import java.util.List;

@RestController
@RequestMapping("/profiles")
@AllArgsConstructor
public class ProfileController {

    private ProfileService profileService;

    @GetMapping
    public List<ProfileResponseDTO> getAllProfiles() {
        return profileService.getAllProfiles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponseDTO> getProfileById (@PathVariable("id") Long id) {
        ProfileResponseDTO profileResponseDTO = profileService.getProfileById(id);
        return ResponseEntity.ok(profileResponseDTO);
    }

    @PostMapping("users/{userId}/profile")
    public ResponseEntity<ProfileResponseDTO> createProfile (@PathVariable Long userId,
                                                             @Valid @RequestBody ProfileRequestDTO profileRequestDTO) {
        ProfileResponseDTO createdProfile  = profileService.createProfile(userId, profileRequestDTO);
        return ResponseEntity.ok(createdProfile);
    }

    @PutMapping("/users/{userId}/profile")
    public ResponseEntity<ProfileResponseDTO> updateProfile(@PathVariable Long userId,
                                                            @Valid @RequestBody ProfileRequestDTO dto
    ) {
        ProfileResponseDTO updatedProfile =
                profileService.updateProfile(userId, dto);
        return ResponseEntity.ok(updatedProfile);
    }
}
