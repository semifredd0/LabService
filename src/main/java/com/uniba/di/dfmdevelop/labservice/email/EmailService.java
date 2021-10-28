package com.uniba.di.dfmdevelop.labservice.email;

import com.uniba.di.dfmdevelop.labservice.exception.CustomException;
import com.uniba.di.dfmdevelop.labservice.exception.ErrorMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender {

    private final JavaMailSender mailSender;

    @Override
    @Async
    public void send(String to, String email, String subject) throws CustomException {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email,true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom("labservice.management@gmail.com");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new CustomException(ErrorMessage.EMAIL_FAIL_SEND);
        }
    }
}