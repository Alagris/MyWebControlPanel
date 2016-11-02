package net.alagris.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerMapping;

import net.alagris.IpWhitelistManager;

@Controller
public class IndexController {

    public static final String BROWSE_PREFIX = "/browse";
    public static final String VIEW_PREFIX = "/view";

    @Autowired
    IpWhitelistManager ipWhitelistManager;

    @RequestMapping(BROWSE_PREFIX + "/**")
    String index(ModelMap model, HttpServletRequest request) {
	if (!ipWhitelistManager.isOnWhitelist(request)) {
	    return "redirect:" + PassAuth.AUTH_PREFIX;
	}
	String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
	path = path.substring(BROWSE_PREFIX.length());
	File f = new File(path);
	if (f.isDirectory()) {
	    String names[] = f.list();
	    List<String> files = new ArrayList<String>();
	    List<String> dirs = new ArrayList<String>();
	    List<String> fileSizes = new ArrayList<String>();
	    for (int i = 0; i < names.length; i++) {
		File f2 = new File(path + File.separatorChar + names[i]);
		if (f2.isDirectory()) {
		    dirs.add(names[i]);
		} else {
		    files.add(names[i]);
		    fileSizes.add(stringifyMemUnits(f2.length()));
		}
	    }
	    if (path.endsWith(File.separator)) {
		model.addAttribute("path", path);
	    } else {
		model.addAttribute("path", path + File.separatorChar);
	    }
	    model.addAttribute("browse_prefix", BROWSE_PREFIX);
	    model.addAttribute("view_prefix", VIEW_PREFIX);
	    model.addAttribute("dirs", dirs.toArray(new String[dirs.size()]));
	    model.addAttribute("fileSizes", fileSizes.toArray(new String[fileSizes.size()]));
	    model.addAttribute("files", files.toArray(new String[files.size()]));
	    return "dir_view";
	} else if (f.isFile()) {

	    return "redirect:" + VIEW_PREFIX + path;
	} else {// doesn't exits
	    model.addAttribute("message", "No file or dir: " + path);
	    return "error_view";
	}

    }

    private static final String[] MemUnits = { "B", "KB", "MB", "GB" };
    private static final long[] preCalculatedValues = { 1, 1024, 1024 * 1024, 1024 * 1024 * 1024 };

    private String stringifyMemUnits(long sizeInBytes) {
	if(sizeInBytes==0)return "0 B";
	int power = (int) (Math.log(sizeInBytes) / Math.log(1024));
	return (long) (sizeInBytes / preCalculatedValues[power]) + " " + MemUnits[power];
    }

    @RequestMapping(VIEW_PREFIX + "/**")
    public void getAttachment(HttpServletRequest request, HttpServletResponse response) {
	if (!ipWhitelistManager.isOnWhitelist(request)) {
	    try {
		response.sendRedirect(request.getContextPath() + PassAuth.AUTH_PREFIX);
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	} else {
	    String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
	    path = path.substring(VIEW_PREFIX.length());
	    File f = new File(path);
	    try {
		response.setContentType(Files.probeContentType(f.toPath()));
		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + f.getName() + "\""));
		response.setContentLength((int) f.length());
		FileCopyUtils.copy(new FileInputStream(f), response.getOutputStream());
	    } catch (IOException e) {
		try {
		    response.sendRedirect(
			    request.getContextPath() + ErrorViewController.ERROR_PREFIX + "?" + e.getMessage());
		} catch (IOException e1) {
		    e1.printStackTrace();
		}
	    }
	}

    }
}
