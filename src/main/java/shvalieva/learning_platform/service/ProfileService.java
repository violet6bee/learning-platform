package shvalieva.learning_platform.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import shvalieva.learning_platform.dto.ProfileRequestDTO;
import shvalieva.learning_platform.dto.ProfileResponseDTO;
import shvalieva.learning_platform.entity.ProfileEntity;
import shvalieva.learning_platform.entity.UserEntity;
import shvalieva.learning_platform.mapper.ProfileMapper;
import shvalieva.learning_platform.repository.ProfileRepository;
import shvalieva.learning_platform.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProfileService {

    private ProfileRepository profileRepository;
    private ProfileMapper profileMapper;
    private final UserRepository userRepository;

    public List<ProfileResponseDTO> getAllProfiles() {
        return profileRepository.findAll().stream()
                .map(profileMapper::toDto)
                .collect(Collectors.toList());
    }

    public ProfileResponseDTO getProfileById(Long id) {
        ProfileEntity entity = profileRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Профиль с id: " + id + " не найден"));
        return profileMapper.toDto(entity);
    }

    public ProfileResponseDTO createProfile(Long userId, ProfileRequestDTO dto) {

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Пользователь с id " + userId + " не найден"));
        ProfileEntity profile = profileMapper.fromRequestDto(dto);
        profile.setUser(user);
        user.setProfileEntity(profile);
        ProfileEntity savedProfile = profileRepository.save(profile);
        return profileMapper.toDto(savedProfile);
    }

    public ProfileResponseDTO updateProfile(Long id, ProfileRequestDTO profileRequestDTO) {
        ProfileEntity entity = profileRepository.findById(id)
                        .orElseThrow(()-> new EntityNotFoundException("Профиль с id: " + id + " не найден"));
        entity.setBiography(profileRequestDTO.getBiography());
        entity.setPhone(profileRequestDTO.getPhone());
        entity.setAvatarUrl(profileRequestDTO.getAvatarUrl());
        profileRepository.save(entity);
        return profileMapper.toDto(entity);
    }

}
