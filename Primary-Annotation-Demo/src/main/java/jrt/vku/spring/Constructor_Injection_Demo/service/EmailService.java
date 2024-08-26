package jrt.vku.spring.Constructor_Injection_Demo.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class EmailService implements MessageService {

    @Override
    public String sendMessage() {
        return "Email Sending ...";
    }
}
