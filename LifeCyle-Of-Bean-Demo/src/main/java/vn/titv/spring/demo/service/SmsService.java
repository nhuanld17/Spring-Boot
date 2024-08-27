package vn.titv.spring.demo.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class SmsService implements MessageService{

    public SmsService(){
        System.out.println("Constructor của lớp : " + getClass().getSimpleName());
    }

    @Override
    public String sendMessage() {
        return "SMS sending ... ";
    }
}
