package com.uniba.di.dfmdevelop.labservice.security;

import com.uniba.di.dfmdevelop.labservice.model.UtenteGenerico;
import com.uniba.di.dfmdevelop.labservice.repository.LaboratorioRepository;
import com.uniba.di.dfmdevelop.labservice.repository.UtenteGenericoRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@AllArgsConstructor
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private LaboratorioRepository laboratorioRepository;
    private UtenteGenericoRepository utenteGenericoRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        UtenteGenerico utenteGenerico = (UtenteGenerico) authentication.getPrincipal();
        String redirectURL = request.getContextPath();

        if (utenteGenerico.hasRole("LABORATORIO")) {
            redirectURL = "laboratorio/index";
        }
        if (utenteGenerico.hasRole("CITTADINO")) {
            redirectURL = "cittadino/index";
        }
        if (utenteGenerico.hasRole("MEDICO")) {
            redirectURL = "medico/index";
        }
        if (utenteGenerico.hasRole("DATORE")) {
            redirectURL = "datore/index";
        }

        response.sendRedirect(redirectURL);
    }
}
