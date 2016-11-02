package net.alagris.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {

    public class SessionInfo {
	private String addr;
	private String host;
	private int port;
	private String user;
	private String sessionId;
	private String cookies;

	public SessionInfo() {
	}

	public String getAddr() {
	    return addr;
	}

	public void setAddr(String addr) {
	    this.addr = addr;
	}

	public String getHost() {
	    return host;
	}

	public void setHost(String host) {
	    this.host = host;
	}

	public int getPort() {
	    return port;
	}

	public void setPort(int port) {
	    this.port = port;
	}

	public String getUser() {
	    return user;
	}

	public void setUser(String user) {
	    this.user = user;
	}

	public String getSessionId() {
	    return sessionId;
	}

	public void setSessionId(String sessionId) {
	    this.sessionId = sessionId;
	}

	public String getCookies() {
	    return cookies;
	}

	public void setCookies(String cookies) {
	    this.cookies = cookies;
	}
    }

    private String parseCookieAge(int age) {
	return age == -1 ? "SesEnd" : String.valueOf(age);
    }

    @RequestMapping(value = "/info")
    public SessionInfo getSessionInfo(HttpSession session, HttpServletRequest request) {

	SessionInfo info = new SessionInfo();
	info.setAddr(request.getRemoteAddr());
	info.setHost(request.getRemoteHost());
	info.setPort(request.getRemotePort());
	info.setUser(request.getRemoteUser());
	info.setSessionId(session.getId());// request.getRequestedSessionId() is
					   // specified by client
	String cookies = "[ ";
	for (Cookie c : request.getCookies()) {
	    cookies += c.getName() + "=" + c.getValue() + ";" + parseCookieAge(c.getMaxAge()) + " ";
	}
	cookies += "]";
	info.setCookies(cookies);
	return info;
    }
}
