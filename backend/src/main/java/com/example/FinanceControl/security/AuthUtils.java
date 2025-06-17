package com.example.FinanceControl.security;

import com.example.FinanceControl.exception.businessExceptions.UnauthorizedException;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

@NoArgsConstructor
public class AuthUtils {

    public static UUID getAuthenticatedUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null && authentication.getPrincipal() instanceof UserAuthenticated userAuthenticated){
            return userAuthenticated.getId();
        }

        throw new UnauthorizedException("Usuário não autenticado.");
    }
}
