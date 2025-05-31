package com.example.FinanceControl.service.user;

import com.example.FinanceControl.dto.request.UserSignInRequestDTO;
import com.example.FinanceControl.dto.request.UserSignUpRequestDTO;
import com.example.FinanceControl.dto.response.UserSignInResponseDTO;
import com.example.FinanceControl.dto.response.UserSignUpResponseDTO;
import com.example.FinanceControl.exception.EmailAlreadyExistsException;
import com.example.FinanceControl.exception.EmailNotFoundException;
import com.example.FinanceControl.model.User;
import com.example.FinanceControl.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserSignService {

    private final UserRepository userRepository;

    public UserSignUpResponseDTO signUn(UserSignUpRequestDTO dto){
        if(userRepository.findByEmail(dto.getEmail()).isPresent()){
            throw new EmailAlreadyExistsException("E-mail já cadastrado.");
        }
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        //TEMPORARIO - REMOVER APOS ADICAO DE BYCRPT
        user.setPassword(dto.getPassword());

        // user.setPassword(passwordEncoder.encode(dto.getPassword()));

        user = userRepository.save(user);

        String token = generateToken(user); // JWT no futuro



        return new UserSignUpResponseDTO(user.getId(), token, user.getName(), user.getEmail());
    }

    public UserSignInResponseDTO signIn(UserSignInRequestDTO dto){
        User user = userRepository.findByEmail(dto.getEmail())
        .orElseThrow(() -> new EmailNotFoundException("E-mail nao encontrado. "));

        // COMPARAÇÃO SIMPLES - TEMPORÁRIO (sem BCrypt)
        if (!dto.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("Senha incorreta."); // ou crie InvalidCredentialsException
        }

        //ADICIONAR BCRYPT PARA SEGURANCA
        //if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
        //    throw new InvalidCredentialsException("Senha inválida.");
        //}

        String token = generateToken(user); // JWT no futuro
        return new UserSignInResponseDTO(user.getId(), token);
    }

    private String generateToken(User user) {
        return UUID.randomUUID().toString(); // JWT no futuro
    }
}
