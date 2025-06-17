package com.example.FinanceControl.unit;

import com.example.FinanceControl.dto.request.UserRequestDTO;
import com.example.FinanceControl.dto.request.userSign.UserSignInRequestDTO;
import com.example.FinanceControl.dto.request.userSign.UserSignUpRequestDTO;
import com.example.FinanceControl.dto.response.UserSignInResponseDTO;
import com.example.FinanceControl.dto.response.UserSignUpResponseDTO;
import com.example.FinanceControl.exception.businessExceptions.EmailNotFoundException;
import com.example.FinanceControl.exception.businessExceptions.InvalidCredentialsException;
import com.example.FinanceControl.model.User;
import com.example.FinanceControl.repository.UserRepository;
import com.example.FinanceControl.security.TokenService;
import com.example.FinanceControl.service.user.UserService;
import com.example.FinanceControl.service.user.UserSignService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserSignServiceTests {

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private UserSignService userSignService;

    private AutoCloseable closeable;

    @BeforeEach
    void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception{
        closeable.close();
    }
    @Test
    void signUp_shouldReturnUserSignUpResponseDTO() {
        UserSignUpRequestDTO signUpDTO = new UserSignUpRequestDTO();

        signUpDTO.setName("John");
        signUpDTO.setEmail("john@email.com");
        signUpDTO.setPassword("123456");

        UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000000");

        UserSignUpResponseDTO expectedResponse = new UserSignUpResponseDTO(
                userId, "fakeToken", "john", "john@email.com"
        );

        when(userService.createUser(any(UserRequestDTO.class)))
                .thenReturn(expectedResponse);

        UserSignUpResponseDTO response = userSignService.signUp(signUpDTO);

        assertNotNull(response);
        assertEquals(expectedResponse.getEmail(), response.getEmail());
        assertEquals(expectedResponse.getName(), response.getName());

        verify(userService, times(1)).createUser(any(UserRequestDTO.class));
    }

    @Test
    void signIn_shouldReturnToken_whenCredentialsAreValid() {
        String email = "john@email.com";
        String password = "123456";
        UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000000");

        UserSignInRequestDTO signInDTO = new UserSignInRequestDTO();
        signInDTO.setEmail(email);
        signInDTO.setPassword(password);

        User user = new User();
        user.setId(userId);
        user.setEmail(email);
        user.setPassword("encodedPassword");

        when(userRepository.findByEmail(email))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(password, "encodedPassword"))
                .thenReturn(true);

        when(tokenService.generateToken(user))
                .thenReturn("validToken");

        UserSignInResponseDTO response = userSignService.signIn(signInDTO);

        assertNotNull(response);
        assertEquals(user.getId(), response.getId());
        assertEquals("validToken", response.getToken());

        verify(userRepository, times(1)).findByEmail(email);
        verify(passwordEncoder, times(1)).matches(password, "encodedPassword");
        verify(tokenService, times(1)).generateToken(user);
    }

    @Test
    void signIn_shouldThrowEmailNotFoundException_whenEmailNotFound() {
        UserSignInRequestDTO signInDTO = new UserSignInRequestDTO();
        signInDTO.setEmail("unknown@email.com");
        signInDTO.setPassword("any");

        when(userRepository.findByEmail("unknown@email.com"))
                .thenReturn(Optional.empty());

        assertThrows(EmailNotFoundException.class, () -> {
            userSignService.signIn(signInDTO);
        });

        verify(userRepository, times(1)).findByEmail("unknown@email.com");
        verify(passwordEncoder, never()).matches(any(), any());
        verify(tokenService, never()).generateToken(any());
    }

    @Test
    void signIn_shouldThrowInvalidCredentialsException_whenPasswordIsWrong() {
        UserSignInRequestDTO signInDTO = new UserSignInRequestDTO();
        signInDTO.setEmail("john@email.com");
        signInDTO.setPassword("wrongPassword");

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail("john@email.com");
        user.setPassword("encodedPassword");

        when(userRepository.findByEmail("john@email.com"))
                .thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongPassword", "encodedPassword"))
                .thenReturn(false);

        assertThrows(InvalidCredentialsException.class, () -> {
            userSignService.signIn(signInDTO);
        });

        verify(userRepository, times(1)).findByEmail("john@email.com");
        verify(passwordEncoder, times(1)).matches("wrongPassword", "encodedPassword");
        verify(tokenService, never()).generateToken(any());
    }
}
