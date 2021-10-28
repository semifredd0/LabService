package com.uniba.di.dfmdevelop.labservice.controller;

import com.uniba.di.dfmdevelop.labservice.email.EmailService;
import com.uniba.di.dfmdevelop.labservice.exception.CustomException;
import com.uniba.di.dfmdevelop.labservice.model.UtenteGenerico;
import com.uniba.di.dfmdevelop.labservice.service.CustomUserDetailService;
import lombok.AllArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@AllArgsConstructor
public class ForgotPasswordController {

    private EmailService emailService;
    private CustomUserDetailService service;

    @GetMapping("/forgot_password")
    public String showForgotPasswordForm() {
        return "forgotPassword";
    }

    @PostMapping("/forgot_password")
    public String processForgotPassword(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        String token = RandomString.make(30);
        try {
            service.updateResetPasswordToken(token, email);
            String link = request.getRequestURL().toString()
                    .replace(request.getServletPath(),"") + "/reset_password?token=" + token;
            sendEmail(email,link);
            model.addAttribute("message", "Una mail è stata inviata all'indirizzo specificato.");
        } catch (UsernameNotFoundException ex) {
            model.addAttribute("error", "Indirizzo e-mail non registrato.");
        } catch (CustomException ex) {
            model.addAttribute("error", ex.getMessage());
        }
        return "forgotPassword";
    }

    public void sendEmail(String to, String link) throws CustomException {
        String subject = "Reset password link";
        String content = "<p>Ciao,</p>"
                + "<p>Clicca il link qui sotto per cambiare la tua password:</p>"
                + "<p><a href=\"" + link + "\">Modifica password</a></p>"
                + "<br>"
                + "<p>Ignora questa mail se ricordi la tua password, "
                + "oppure se non hai effettuato la richiesta.</p>";
        emailService.send(to,content,subject);
    }


    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        UtenteGenerico utenteGenerico = service.getByResetPasswordToken(token);
        model.addAttribute("token", token);
        if (utenteGenerico == null) {
            model.addAttribute("message", "Token non valido.");
        }
        return "resetPasswordForm";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        UtenteGenerico customer = service.getByResetPasswordToken(token);
        if (customer == null) {
            model.addAttribute("message", "Token non valido.");
        } else {
            service.updatePassword(customer, password);
            model.addAttribute("message", "Password aggiornata correttamente.");
        }
        return "resetPasswordForm";
    }
}