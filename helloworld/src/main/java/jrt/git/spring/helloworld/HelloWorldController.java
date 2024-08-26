package jrt.git.spring.helloworld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @GetMapping("/")
    public String greetingEnglish() {
        return "Hello World";
    }

    @GetMapping("/h2")
    public String greetingVietNamese() {
        return "Xin Chao";
    }
}
