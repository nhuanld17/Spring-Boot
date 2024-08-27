package jrt.vku.spring.Constructor_Injection_Demo.service;

import org.springframework.stereotype.Component;

@Component
public class SmsService implements MessageService{

    @Override
    public String sendMessage() {
        return "SMS Sending ...";
    }
}
