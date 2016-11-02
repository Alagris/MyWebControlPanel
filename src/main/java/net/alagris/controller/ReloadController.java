package net.alagris.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import net.alagris.IpWhitelistManager;

@Controller
public class ReloadController {
    public static final String RELOAD_PREFIX = "/reload";

    @Autowired
    IpWhitelistManager ipWhitelistManager;

    @RequestMapping(RELOAD_PREFIX)
    String reload() {
	ipWhitelistManager.reload();
	return "redirect:/browse/";
    }
}
