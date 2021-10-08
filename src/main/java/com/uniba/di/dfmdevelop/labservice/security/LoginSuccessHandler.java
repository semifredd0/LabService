package com.uniba.di.dfmdevelop.labservice.security;

import com.uniba.di.dfmdevelop.labservice.model.UtenteGenerico;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        UtenteGenerico utenteGenerico = (UtenteGenerico) authentication.getPrincipal();
        String redirectURL = request.getContextPath();

        if (utenteGenerico.hasRole("LABORATORIO"))
            redirectURL = "laboratorio/index";
        // Aggiungere altri redirect

        response.sendRedirect(redirectURL);
    }
}
