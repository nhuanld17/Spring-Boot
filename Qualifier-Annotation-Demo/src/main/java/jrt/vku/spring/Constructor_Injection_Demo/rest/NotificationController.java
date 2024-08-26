package jrt.vku.spring.Constructor_Injection_Demo.rest;

import jrt.vku.spring.Constructor_Injection_Demo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {
    private MessageService service;

    //    Constructor Injection
    @Autowired
    public NotificationController(@Qualifier("smsService")MessageService messageService) {
        this.service = messageService;
    }

    @GetMapping("/send-email")
    public String sendEmail() {
        return this.service.sendMessage();
    }
}
