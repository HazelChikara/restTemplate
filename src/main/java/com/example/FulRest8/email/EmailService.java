package com.example.FulRest8.email;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Service
@AllArgsConstructor
public class EmailService implements EmailSender {
//   logging, use SLF4J to get Logger.error
    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);


    private final JavaMailSender mailSender;


    @Override
//    We can queue to resend email, but we will set it as an asynch method where it does not block the client
    @Async
    public void send(String to, String email){
//        Send the rich email, if it cannot send it will throw an exception
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Confirm your email");
            helper.setFrom("confirm@hazelchikara.com");
            mailSender.send(mimeMessage);
        }
        catch(MessagingException e) {
            LOGGER.error("Failed to send email", e);
            throw new IllegalStateException("Failed to send email");

        }
        }

    }

