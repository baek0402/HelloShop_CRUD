package jpabook.jpashop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class HomeController {

    @RequestMapping("/") //그냥 이렇게만 쓰면 get/post 둘다 처리해준대
    public String home() {
        log.info("home controller");
        return "home";
    }
}
