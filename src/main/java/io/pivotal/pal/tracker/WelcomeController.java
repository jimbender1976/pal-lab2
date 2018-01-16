package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @Autowired
    public WelcomeController( @Value("${WELCOME_MESSAGE}") String helloMessage) {
        this.helloMessage = helloMessage;
    }


    private String helloMessage;


    @GetMapping("/")
    public String sayHello() {
        //return "hello";
        return helloMessage;
    }
}
