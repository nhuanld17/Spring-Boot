package jrt.vku.spring.Constructor_Injection_Demo;

import org.springframework.stereotype.Component;

@Component
public class EmailService implements MessageInterface{

    @Override
    public String sendMessage() {
        return "Email Sending ...";
    }
}
