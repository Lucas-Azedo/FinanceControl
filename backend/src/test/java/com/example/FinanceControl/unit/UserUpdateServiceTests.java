package com.example.FinanceControl.unit;

import com.example.FinanceControl.dto.request.userUpdate.*;
import com.example.FinanceControl.exception.businessExceptions.*;
import com.example.FinanceControl.model.Role;
import com.example.FinanceControl.model.User;
import com.example.FinanceControl.repository.RoleRepository;
import com.example.FinanceControl.repository.UserRepository;
import com.example.FinanceControl.service.user.UserUpdateService;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserUpdateServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserUpdateService userUpdateService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    private User authenticatedUser;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        authenticatedUser = new User();
        authenticatedUser.setId(java.util.UUID.randomUUID());
        authenticatedUser.setEmail("user@email.com");
        authenticatedUser.setName("User");
        authenticatedUser.setPassword("encodedPassword");

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(authenticatedUser.getEmail());
        SecurityContextHolder.setContext(securityContext);

        when(userRepository.findByEmail(authenticatedUser.getEmail()))
                .thenReturn(Optional.of(authenticatedUser));
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    // updateEmail tests

    @Test
    void updateEmail_shouldUpdateEmailSuccessfully() {
        UserUpdateEmailRequestDTO dto = new UserUpdateEmailRequestDTO();
        dto.setEmail("newemail@email.com");
        dto.setPassword("password");

        when(passwordEncoder.matches(dto.getPassword(), authenticatedUser.getPassword()))
                .thenReturn(true);

        when(userRepository.findByEmail(dto.getEmail()))
                .thenReturn(Optional.empty());

        userUpdateService.updateEmail(dto);

        assertEquals(dto.getEmail(), authenticatedUser.getEmail());
        verify(userRepository).save(authenticatedUser);
    }

    @Test
    void updateEmail_shouldThrowEmailAlreadyExistsException() {
        UserUpdateEmailRequestDTO dto = new UserUpdateEmailRequestDTO();
        dto.setEmail("existing@email.com");
        dto.setPassword("password");

        User otherUser = new User();
        otherUser.setId(java.util.UUID.randomUUID());
        otherUser.setEmail(dto.getEmail());

        when(passwordEncoder.matches(dto.getPassword(), authenticatedUser.getPassword()))
                .thenReturn(true);

        when(userRepository.findByEmail(dto.getEmail()))
                .thenReturn(Optional.of(otherUser));

        EmailAlreadyExistsException ex = assertThrows(EmailAlreadyExistsException.class,
                () -> userUpdateService.updateEmail(dto));
        assertEquals("E-mail já cadastrado", ex.getMessage());
    }

    @Test
    void updateEmail_shouldThrowInvalidPasswordException() {
        UserUpdateEmailRequestDTO dto = new UserUpdateEmailRequestDTO();
        dto.setEmail("any@email.com");
        dto.setPassword("wrongPassword");

        when(passwordEncoder.matches(dto.getPassword(), authenticatedUser.getPassword()))
                .thenReturn(false);

        assertThrows(InvalidPasswordException.class, () -> userUpdateService.updateEmail(dto));
    }

    // updateName tests

    @Test
    void updateName_shouldUpdateNameSuccessfully() {
        UserUpdateNameRequestDTO dto = new UserUpdateNameRequestDTO();
        dto.setName("New Name");
        dto.setPassword("password");

        when(passwordEncoder.matches(dto.getPassword(), authenticatedUser.getPassword()))
                .thenReturn(true);

        userUpdateService.updateName(dto);

        assertEquals(dto.getName(), authenticatedUser.getName());
        verify(userRepository).save(authenticatedUser);
    }

    @Test
    void updateName_shouldThrowInvalidPasswordException() {
        UserUpdateNameRequestDTO dto = new UserUpdateNameRequestDTO();
        dto.setName("New Name");
        dto.setPassword("wrongPassword");

        when(passwordEncoder.matches(dto.getPassword(), authenticatedUser.getPassword()))
                .thenReturn(false);

        assertThrows(InvalidPasswordException.class, () -> userUpdateService.updateName(dto));
    }

    // updatePassword tests

    @Test
    void updatePassword_shouldUpdatePasswordSuccessfully() {
        UserUpdatePasswordRequestDTO dto = new UserUpdatePasswordRequestDTO();
        dto.setPassword("oldPassword");
        dto.setNewPassword("newPassword");

        when(passwordEncoder.matches(dto.getPassword(), authenticatedUser.getPassword()))
                .thenReturn(true);

        when(passwordEncoder.encode(dto.getNewPassword()))
                .thenReturn("encodedNewPassword");

        userUpdateService.updatePassword(dto);

        assertEquals("encodedNewPassword", authenticatedUser.getPassword());
        verify(userRepository).save(authenticatedUser);
    }

    @Test
    void updatePassword_shouldThrowInvalidPasswordException() {
        UserUpdatePasswordRequestDTO dto = new UserUpdatePasswordRequestDTO();
        dto.setPassword("wrongPassword");
        dto.setNewPassword("newPassword");

        when(passwordEncoder.matches(dto.getPassword(), authenticatedUser.getPassword()))
                .thenReturn(false);

        assertThrows(InvalidPasswordException.class, () -> userUpdateService.updatePassword(dto));
    }

    // updateRole tests

    @Test
    void updateRole_shouldUpdateRoleSuccessfully() {
        UserUpdateRoleRequestDTO dto = new UserUpdateRoleRequestDTO();
        dto.setRoleName("ROLE_ADMIN");

        Role newRole = new Role();
        newRole.setName(dto.getRoleName());

        when(roleRepository.findByName(dto.getRoleName()))
                .thenReturn(Optional.of(newRole));

        userUpdateService.updateRole(dto);

        assertEquals(dto.getRoleName(), authenticatedUser.getRole().getName());
        verify(userRepository).save(authenticatedUser);
    }

    @Test
    void updateRole_shouldThrowRoleNotFoundException() {
        UserUpdateRoleRequestDTO dto = new UserUpdateRoleRequestDTO();
        dto.setRoleName("ROLE_UNKNOWN");

        when(roleRepository.findByName(dto.getRoleName()))
                .thenReturn(Optional.empty());

        RoleNotFoundException ex = assertThrows(RoleNotFoundException.class,
                () -> userUpdateService.updateRole(dto));
        assertTrue(ex.getMessage().contains("Role não encontrada"));
    }

    // getAuthenticatedUser negative test - email not found

    @Test
    void getAuthenticatedUser_shouldThrowEmailNotFoundException() {
        when(userRepository.findByEmail(authenticatedUser.getEmail()))
                .thenReturn(Optional.empty());

        UserUpdateEmailRequestDTO dto = new UserUpdateEmailRequestDTO();
        dto.setEmail("any@email.com");
        dto.setPassword("password");

        assertThrows(EmailNotFoundException.class, () -> userUpdateService.updateEmail(dto));
    }
}
