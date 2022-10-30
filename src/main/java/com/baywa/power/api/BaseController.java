package com.baywa.power.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Home redirection to swagger api documentation
 */
@Slf4j
@Controller
public class BaseController {

    @GetMapping(value = "/")
    public RedirectView index() {
        log.debug("redirect root to /swagger-ui/index.html");
        return new RedirectView("/swagger-ui/index.html");
    }
}
