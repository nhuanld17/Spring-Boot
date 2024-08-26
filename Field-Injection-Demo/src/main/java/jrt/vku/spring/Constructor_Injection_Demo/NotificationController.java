package jrt.vku.spring.Constructor_Injection_Demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {
    // Field Injection
    @Autowired
    private MessageInterface messageService;

//    Constructor Injection
//    @Autowired
//    public NotificationController(MessageInterface messageService) {
//        this.messageService = messageService;
//    }

//    Setter Injection
//    @Autowired
//    public void setEmail(EmailService emailService){
//        this.messageService = emailService;
//    }

    @GetMapping("/send-email")
    public String sendEmail() {
        return this.messageService.sendMessage();
    }
}
