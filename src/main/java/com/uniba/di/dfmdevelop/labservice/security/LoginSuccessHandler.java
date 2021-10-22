package com.uniba.di.dfmdevelop.labservice.security;

import com.uniba.di.dfmdevelop.labservice.model.UtenteGenerico;
import com.uniba.di.dfmdevelop.labservice.model.laboratorio.Laboratorio;
import com.uniba.di.dfmdevelop.labservice.repository.LaboratorioRepository;
import com.uniba.di.dfmdevelop.labservice.repository.UtenteGenericoRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

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
            // Prendere utente_generico e laboratorio dal db e passarli come model
            Optional<UtenteGenerico> utenteGenericoTmp = utenteGenericoRepository.findByEmail(utenteGenerico.getEmail())
                    .stream().findFirst();
            Laboratorio laboratorio = laboratorioRepository.getByIdUtente(utenteGenericoTmp.get());
            ModelAndView model = new ModelAndView();
            model.addObject("laboratorio", laboratorio);
            model.addObject("utente", utenteGenericoTmp.get());
            redirectURL = "laboratorio/index";
            // Sbagliato, devo passare un model non un modelandview
        }
        // Aggiungere altri redirect

        response.sendRedirect(redirectURL);
    }
}
