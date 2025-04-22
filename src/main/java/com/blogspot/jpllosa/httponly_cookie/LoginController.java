package com.blogspot.jpllosa.httponly_cookie;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String postLogin(
            @RequestParam(name="username", required=true) String username,
            @RequestParam(name="password", required=true) String password,
            HttpServletResponse response,
            HttpSession session) {

        session.setAttribute("username", username);
        session.setAttribute("password", password);

        Cookie cookie = new Cookie("MY_SESSION", "supercalifragilisticexpialidocious");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return "redirect:/welcome";
    }

    @GetMapping("/welcome")
    public String welcomeUser(
            @CookieValue(value = "MY_SESSION") String mySession,
            Model model,
            HttpSession session) {

        model.addAttribute("username", session.getAttribute("username"));
        model.addAttribute("password", session.getAttribute("password"));
        model.addAttribute("mySession", mySession);

        return "welcome";
    }

    @GetMapping("/clear-my-session")
    public @ResponseBody String clearMySession(HttpServletResponse response) {

        Cookie cookie = new Cookie("MY_SESSION", "deleted");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "";
    }
}