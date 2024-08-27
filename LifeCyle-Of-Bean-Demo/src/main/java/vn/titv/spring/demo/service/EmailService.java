package vn.titv.spring.demo.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class EmailService implements MessageService {

    @PostConstruct
    public void init(){
        System.out.println("Đoạn code này được run ngay sau khi tạo "+getClass().getSimpleName());
    }

    @PreDestroy
    public void myDestroy(){
        System.out.println("Đoạn code này được run trước khi hủy bỏ "+getClass().getSimpleName());
    }

    @PreDestroy
    public void myDestroy1(){
        System.out.println("Đoạn code này được run trước khi hủy bỏ "+getClass().getSimpleName());
    }

    public EmailService(){
        System.out.println("Constructor của lớp : " + getClass().getSimpleName());
    }

    @Override
    public String sendMessage() {
        return "Email sending ..... ";
    }

}
