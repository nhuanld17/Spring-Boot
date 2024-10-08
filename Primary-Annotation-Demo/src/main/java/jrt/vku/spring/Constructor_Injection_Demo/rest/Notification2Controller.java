package jrt.vku.spring.Constructor_Injection_Demo.rest;

import jrt.vku.spring.Constructor_Injection_Demo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Notification2Controller {
    private MessageService emailService;
    private MessageService smsService;
    private MessageService zaloService;

    @Autowired
    public Notification2Controller(
            @Qualifier("emailService") MessageService emailService,
            @Qualifier("smsService") MessageService smsService,
            @Qualifier("zaloService") MessageService zaloService
            ){
        this.emailService = emailService;
        this.smsService = smsService;
        this.zaloService = zaloService;
    }

    @GetMapping("/email")
    public String sendEmail(){
        return this.emailService.sendMessage();
    }

    @GetMapping("/sms")
    public String sendSMS(){
        return this.smsService.sendMessage();
    }

    @GetMapping("/zalo")
    public String sendZalo(){
        return this.zaloService.sendMessage();
    }

}
