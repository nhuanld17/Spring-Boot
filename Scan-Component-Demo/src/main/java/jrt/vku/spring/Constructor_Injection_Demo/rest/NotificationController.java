package jrt.vku.spring.Constructor_Injection_Demo.rest;

import jrt.vku.spring.Constructor_Injection_Demo.service.MessageInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {
    private MessageInterface messageService;

//    Constructor Injection
    @Autowired
    public NotificationController(MessageInterface messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/send-email")
    public String sendEmail() {
        return this.messageService.sendMessage();
    }
}