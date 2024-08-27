package jrt.vku.spring.Constructor_Injection_Demo.rest;

import jrt.vku.spring.Constructor_Injection_Demo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {
    private MessageService service1;
    private MessageService service2;

    //    Constructor Injection
    @Autowired
    public NotificationController(
            @Qualifier("emailService")MessageService messageService1,
            @Qualifier("emailService")MessageService messageService2) {
        this.service1 = messageService1;
        this.service2 = messageService2;
    }

    @GetMapping("/send-email")
    public String sendEmail() {
        return this.service1.sendMessage();
    }


    @GetMapping("/check")
    public String check(){
        if (service1 == service2){
            return "SINGLETON";
        } else {
            return "PROTOTYPE";
        }
    }
}
