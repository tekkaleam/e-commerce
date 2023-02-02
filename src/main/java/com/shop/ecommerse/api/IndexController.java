package com.shop.ecommerse.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;


// @RequestMapping("/")
public class IndexController {

    @GetMapping
    public RedirectView redirectToSwagger(){
        return new RedirectView("/swagger-ui.html");
    }
}
