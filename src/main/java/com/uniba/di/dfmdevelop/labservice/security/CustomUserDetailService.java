package com.uniba.di.dfmdevelop.labservice.security;

import com.uniba.di.dfmdevelop.labservice.model.UtenteGenerico;
import com.uniba.di.dfmdevelop.labservice.registration.token.ConfirmationToken;
import com.uniba.di.dfmdevelop.labservice.registration.token.ConfirmationTokenService;
import com.uniba.di.dfmdevelop.labservice.repository.UtenteGenericoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";
    private final UtenteGenericoRepository utenteGenericoRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    public CustomUserDetailService(UtenteGenericoRepository utenteGenericoRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ConfirmationTokenService confirmationTokenService) {
        this.utenteGenericoRepository = utenteGenericoRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.confirmationTokenService = confirmationTokenService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return utenteGenericoRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String signUpUser(UtenteGenerico utenteGenerico) {
        boolean userExists = utenteGenericoRepository
                .findByEmail(utenteGenerico.getEmail()).isPresent();

        if (userExists) {
            // TODO reinviare email con link di conferma
            throw new IllegalStateException("email already taken");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(utenteGenerico.getPassword());
        utenteGenerico.setPassword(encodedPassword);
        utenteGenericoRepository.save(utenteGenerico);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                utenteGenerico
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        return token;
    }

    public int enableUser(String email) {
        return utenteGenericoRepository.enableUtenteGenerico(email);
    }
}