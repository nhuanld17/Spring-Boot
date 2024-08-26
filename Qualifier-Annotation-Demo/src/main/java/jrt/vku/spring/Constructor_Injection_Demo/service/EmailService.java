package jrt.vku.spring.Constructor_Injection_Demo.service;

import org.springframework.stereotype.Component;

@Component
public class EmailService implements MessageService {

    public EmailService() {
        System.out.println("EmailService constructor");
    }

    @Override
    public String sendMessage() {
        return "Email Sending ...";
    }
}
