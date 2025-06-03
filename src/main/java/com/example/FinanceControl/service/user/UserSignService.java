package com.example.FinanceControl.service.user;

import com.example.FinanceControl.dto.request.UserRequestDTO;
import com.example.FinanceControl.dto.request.UserSignInRequestDTO;
import com.example.FinanceControl.dto.request.UserSignUpRequestDTO;
import com.example.FinanceControl.dto.response.UserSignInResponseDTO;
import com.example.FinanceControl.dto.response.UserSignUpResponseDTO;
import com.example.FinanceControl.exception.EmailNotFoundException;
import com.example.FinanceControl.exception.InvalidCredentialsException;
import com.example.FinanceControl.model.User;
import com.example.FinanceControl.repository.UserRepository;
import com.example.FinanceControl.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSignService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public UserSignUpResponseDTO signUp(UserSignUpRequestDTO dto){
        UserRequestDTO userRequest = new UserRequestDTO(
                dto.getName(),
                dto.getEmail(),
                dto.getPassword()
        );

        return userService.createUser(userRequest);
    }

    public UserSignInResponseDTO signIn(UserSignInRequestDTO dto){
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new EmailNotFoundException("E-mail nao encontrado. "));


        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Senha inválida.");
        }


        String token = tokenService.generateToken(user);
        return new UserSignInResponseDTO(user.getId(), token);
    }


}
