package net.alagris;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class IpWhitelistManager {

    private static final Logger log = LoggerFactory.getLogger(IpWhitelistManager.class);
    private ArrayList<String> ipAdresses = new ArrayList<String>();
    @Autowired
    File ipWhitelist;

    public IpWhitelistManager(@Autowired File ipWhitelist) {
	log.info("Scanning ip whitelist: " + ipWhitelist);
	try {
	    Scanner sc = new Scanner(ipWhitelist);
	    while (sc.hasNextLine()) {
		ipAdresses.add(sc.nextLine());
	    }
	    sc.close();
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	}
    }

    public void reload() {
	log.info("Reload! Scanning ip whitelist: " + ipWhitelist);
	try {
	    Scanner sc = new Scanner(ipWhitelist);
	    ipAdresses.clear();
	    while (sc.hasNextLine()) {
		ipAdresses.add(sc.nextLine());
	    }
	    sc.close();
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	}
    }

    public boolean isOnWhitelist(String ip) {
	return ipAdresses.contains(ip);
    }

    public boolean isOnWhitelist(HttpServletRequest request) {
	return isOnWhitelist(request.getRemoteAddr());
    }

}
