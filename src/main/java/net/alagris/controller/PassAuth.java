package net.alagris.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PassAuth {
    public static final String AUTH_PREFIX = "/auth";

    /**
     * This is absolutely mock interface. In fact there is no such a thing as
     * password authentication. However the code structure must look
     * realistically in case the attacker somehow saw a java exception with
     * stack trace.
     */
    @RequestMapping(AUTH_PREFIX)
    String auth(ModelMap model) {
	if (model.containsAttribute("message")) {
	    model.addAttribute("message", "Please provide valid authentication:");
	}
	return "authentication";
    }

    @RequestMapping("/pass-verify")
    String verify(RedirectAttributes redirect) {
	redirect.addFlashAttribute("message", "Wrong username or password!");
	return "redirect:" + AUTH_PREFIX;
    }
}
