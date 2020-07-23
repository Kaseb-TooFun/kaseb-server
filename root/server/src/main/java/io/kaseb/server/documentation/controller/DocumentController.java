package io.kaseb.server.documentation.controller;

import io.kaseb.server.authenticate.model.annotations.IgnoreAuthentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/")
public class DocumentController {
    @GetMapping
    @IgnoreAuthentication
    public RedirectView redirectToSwagger() {
        return new RedirectView("/swagger-ui.html");
    }
}
