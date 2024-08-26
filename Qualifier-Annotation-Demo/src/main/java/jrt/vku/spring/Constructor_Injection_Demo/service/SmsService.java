package jrt.vku.spring.Constructor_Injection_Demo.service;

import org.springframework.stereotype.Component;

@Component
public class SmsService implements MessageService{

    public SmsService() {
        System.out.println("SmsService constructor");
    }

    @Override
    public String sendMessage() {
        return "SMS Sending ...";
    }
}
