package jrt.vku.spring.Constructor_Injection_Demo.service;

import org.springframework.stereotype.Component;

@Component
public class ZaloService implements MessageService{

    public ZaloService() {
        System.out.println("Constructor of " + this.getClass().getSimpleName());
    }

    @Override
    public String sendMessage() {
        return "Zalo Sending ... ";
    }
}
