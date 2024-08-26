package jrt.vku.spring.Springboot_Devtools_Demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {
    @GetMapping("/")
    public String index() {
        return "Hello World";
    }

    @GetMapping("/h2")
    public String index2() {
        return "Hello Nhuan";
    }
}
