package webapp;

import org.noear.solon.SolonApp;
import org.noear.solon.annotation.Controller;
import org.noear.solon.annotation.Mapping;

@Controller
public class HelloApp {
    public static void main(String[] args) {
        SolonApp.start(HelloApp.class, args);
    }

    @Mapping("/")
    public String hello(String name){
        return "hello world!";
    }
}
