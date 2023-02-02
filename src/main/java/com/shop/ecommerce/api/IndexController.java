package com.shop.ecommerce.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;


// @RequestMapping("/")
public class IndexController {

    @GetMapping
    public RedirectView redirectToSwagger(){
        return new RedirectView("/swagger-ui.html");
    }
}
