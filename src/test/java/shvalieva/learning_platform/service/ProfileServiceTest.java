package shvalieva.learning_platform.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shvalieva.learning_platform.dto.ProfileRequestDTO;
import shvalieva.learning_platform.dto.ProfileResponseDTO;
import shvalieva.learning_platform.entity.ProfileEntity;
import shvalieva.learning_platform.entity.UserEntity;
import shvalieva.learning_platform.mapper.ProfileMapper;
import shvalieva.learning_platform.repository.ProfileRepository;
import shvalieva.learning_platform.repository.UserRepository;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProfileMapper profileMapper;

    @InjectMocks
    private ProfileService profileService;

    @Test
    void testGetAllProfiles() {
        ProfileEntity p1 = new ProfileEntity();
        ProfileEntity p2 = new ProfileEntity();

        when(profileRepository.findAll()).thenReturn(Arrays.asList(p1, p2));
        when(profileMapper.toDto(any(ProfileEntity.class))).thenReturn(new ProfileResponseDTO());

        var result = profileService.getAllProfiles();

        assertEquals(2, result.size());
        verify(profileRepository).findAll();
        verify(profileMapper, times(2)).toDto(any(ProfileEntity.class));
    }

    @Test
    void testGetProfileById_Success() {
        ProfileEntity entity = new ProfileEntity();
        ProfileResponseDTO dto = new ProfileResponseDTO();

        when(profileRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(profileMapper.toDto(entity)).thenReturn(dto);

        ProfileResponseDTO result = profileService.getProfileById(1L);

        assertNotNull(result);
        verify(profileRepository).findById(1L);
        verify(profileMapper).toDto(entity);
    }

    @Test
    void testGetProfileById_NotFound() {
        when(profileRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> profileService.getProfileById(1L));
    }

    @Test
    void testCreateProfile_Success() {
        UserEntity user = new UserEntity();
        user.setId(1L);

        ProfileRequestDTO requestDTO = new ProfileRequestDTO();
        requestDTO.setBiography("Bio");
        requestDTO.setPhone("123");
        requestDTO.setAvatarUrl("avatar");

        ProfileEntity entity = new ProfileEntity();
        ProfileEntity savedEntity = new ProfileEntity();
        ProfileResponseDTO responseDTO = new ProfileResponseDTO();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(profileMapper.fromRequestDto(requestDTO)).thenReturn(entity);
        when(profileRepository.save(entity)).thenReturn(savedEntity);
        when(profileMapper.toDto(savedEntity)).thenReturn(responseDTO);

        ProfileResponseDTO result = profileService.createProfile(1L, requestDTO);

        assertNotNull(result);
        verify(userRepository).findById(1L);
        verify(profileRepository).save(entity);
        verify(profileMapper).toDto(savedEntity);
        assertEquals(user, entity.getUser());
    }

    @Test
    void testCreateProfile_UserNotFound() {
        ProfileRequestDTO dto = new ProfileRequestDTO();

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> profileService.createProfile(1L, dto));
    }

    @Test
    void testUpdateProfile_Success() {
        ProfileEntity entity = new ProfileEntity();
        entity.setBiography("Old bio");
        entity.setPhone("Old phone");
        entity.setAvatarUrl("Old avatar");

        ProfileRequestDTO dto = new ProfileRequestDTO();
        dto.setBiography("New bio");
        dto.setPhone("New phone");
        dto.setAvatarUrl("New avatar");

        ProfileResponseDTO responseDTO = new ProfileResponseDTO();

        when(profileRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(profileRepository.save(entity)).thenReturn(entity);
        when(profileMapper.toDto(entity)).thenReturn(responseDTO);

        ProfileResponseDTO result = profileService.updateProfile(1L, dto);

        assertNotNull(result);
        assertEquals("New bio", entity.getBiography());
        assertEquals("New phone", entity.getPhone());
        assertEquals("New avatar", entity.getAvatarUrl());

        verify(profileRepository).save(entity);
        verify(profileMapper).toDto(entity);
    }

    @Test
    void testUpdateProfile_NotFound() {
        ProfileRequestDTO dto = new ProfileRequestDTO();

        when(profileRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> profileService.updateProfile(1L, dto));
    }
}
