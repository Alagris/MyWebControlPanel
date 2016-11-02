package net.alagris.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ErrorViewController {

    // notice /error has already been taken by spring
    public static final String ERROR_PREFIX = "/error_view";

    @RequestMapping(ERROR_PREFIX)
    String error(ModelMap model, @RequestParam(value="message",defaultValue="error") String message) {
	if (!model.containsAttribute("message")) {
	    model.addAttribute("message", message);
	}
	return "error_view";
    }
}
