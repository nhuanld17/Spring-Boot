package vn.titv.spring.demo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.titv.spring.demo.service.EmailService;
import vn.titv.spring.demo.service.MessageService;

@RestController
public class NotificationController {
    private MessageService service;
    private MessageService service_2;
    @Autowired
    public NotificationController(
            @Qualifier("emailService") MessageService service,
            @Qualifier("emailService") MessageService service_2) {
        this.service = service;
        this.service_2 = service_2;
    }

    @GetMapping("/send-email")
    public String sendEmail() {
        return this.service.sendMessage();
    }


    @GetMapping("/check")
    public String check(){
        return "Kiểm tra: " + (service==service_2);
    }

    @GetMapping("/check2")
    public String check2(){
        if (service==service_2)
            return "SINGLETON";
        else
            return "PROTOTYPE";
    }
}
