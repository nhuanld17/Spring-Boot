package jrt.vku.spring.Constructor_Injection_Demo.service;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class EmailService implements MessageService {
    /*
    Khi sử dụng Prototype, Spring không gọi phương thức Destroy
    Khi sử dụng Prototypr, Không cần khai báo @Lazy
    */
    public EmailService() {
        System.out.println("Constructor of " + this.getClass().getSimpleName());
    }

    @Override
    public String sendMessage() {
        return "Email Sending ...";
    }
}
