package com.training.controller;

import com.training.dto.MailDto;
import com.training.entities.User;
import com.training.jwt.JwtProvider;
import com.training.service.EmailService;
import com.training.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * author tungns14
 */
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtProvider jwtProvider;

    @Value("${app.jwtExpirationMs}")
    private int jwtExpiration;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private EmailService emailService;

    @Autowired
    private Environment env;


    @GetMapping("/login")
    public String showLogin(Model model) {
        return "login";
    }

    @GetMapping("/logout-account")
    public String logout(HttpServletResponse response, HttpServletRequest request) {
        Cookie cookie = new Cookie("token_web", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null) {
            new SecurityContextLogoutHandler().logout(request, null, null);
        }
        return "redirect:/login";

    }

    @PostMapping("/login")
    public String submitLogin(
            @RequestParam(name = "userName") String account,
            @RequestParam(name = "password") String password,
            Model model,
            HttpServletResponse response,
            HttpServletRequest request
    ) throws MessagingException {
        User user = userService.findUserByAccount(account);
        if (user != null) {
            if (!user.isActive()) {
                String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                        .replacePath(null)
                        .build()
                        .toUriString();
                MailDto mail = new MailDto();
                mail.setFrom(env.getProperty("spring.mail.username"));
                mail.setMailTo(user.getEmail());
                mail.setSubject("Update password");
                Map<String, Object> attribute = new HashMap<>();
                attribute.put("name", user.getAccount());
                attribute.put("password", user.getPassword());
                attribute.put("id", user.getId());

                attribute.put("url", baseUrl);

                attribute.put("location", "Viet Nam");
                mail.setProps(attribute);
                emailService.sendEmail(mail,"template_email.html");

                model.addAttribute("message", "Account is not active, please check email!");
                return "login";
            }
            try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                account,
                                password
                        )
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
                String jwt = jwtProvider.generateJwtToken(authentication, jwtExpiration);
                //System.out.println(jwt);
                Cookie cookie = new Cookie("token_web", jwt);
                cookie.setHttpOnly(true);
                cookie.setSecure(true);
                cookie.setMaxAge(2592000);
                response.addCookie(cookie);
                return "redirect:dashboard";
            } catch (Exception e) {
                model.addAttribute("message", "UserName or Password is not correct!");
                return "login";
            }

        } else {
            model.addAttribute("message", "Account is not exist!");
            return "login";
        }


    }

}
