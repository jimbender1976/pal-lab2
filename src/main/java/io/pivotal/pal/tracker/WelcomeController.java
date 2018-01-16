package io.pivotal.pal.tracker;

<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Autowired;
=======
>>>>>>> ebe75eee90e7549bae2328b4d4e8f3d38c97959c
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
