package com.training.controller;

import com.training.dto.MailDto;
import com.training.entities.User;
import com.training.jwt.JwtProvider;
import com.training.service.EmailService;
import com.training.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("/forgotPassword")
public class ForgotPasswordController {

    @Autowired
    private UserService userService;

    @Autowired
    private Environment env;

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Autowired
    JwtProvider jwtProvider;

    @Value("${app.jwtExpirationMs.forgetPassword}")
    private int jwtExpirationForgetPassword;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder encoder;


    @GetMapping
    public String getForgotPasswordPage(ModelMap modelMap) {
        modelMap.put("message", "");
        return "forgot-password";
    }

    @PostMapping
    public String sendCodeVerify(@RequestParam String email, ModelMap modelMap,
                                 HttpServletResponse response, HttpServletRequest request) throws MessagingException {
        User user = userService.findUserByEmail(email);
        if (null == user) {
            modelMap.put("message", "Email not exist");
            return "forgot-password";

        } else {

            String token = Jwts.builder()
                    .setSubject((user.getAccount()))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date((new Date()).getTime() + jwtExpirationForgetPassword))
                    .signWith(SignatureAlgorithm.HS512, jwtSecret)
                    .compact();


            String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                    .replacePath(null)
                    .build()
                    .toUriString();
            modelMap.put("message", "");
            modelMap.put("user", new User());
            MailDto mailDto = new MailDto();
            mailDto.setFrom(env.getProperty("spring.mail.username", String.class));
            mailDto.setMailTo(user.getEmail());
            mailDto.setSubject("Code verify account");
            Map<String, Object> attribute = new HashMap<>();
            attribute.put("name", user.getAccount());
            attribute.put("urlChangePass", baseUrl + "/forgotPassword/resetPassword?token=" + token);
            attribute.put("id", user.getId());
            attribute.put("location", "Viet Nam");
            mailDto.setProps(attribute);
            emailService.sendEmail(mailDto, "template-email-verify-code");

            Cookie cookie = new Cookie("token_reset_password", token);
            cookie.setMaxAge(2592000);
            cookie.setSecure(true);
            cookie.setHttpOnly(true);
            cookie.setPath("/"); // global cookie accessible every where
            response.addCookie(cookie);
            modelMap.put("message_send_code","We sent a link reset password to email for you, please check email \n " +
                    "Password change link is valid for 30 minutes,if you don't receive the email, please click sent again");
            modelMap.put("email",email);

            return "forgot-password";

        }
    }

    @GetMapping("/resetPassword")
    public String showFormResetPassword(@RequestParam String token, HttpServletRequest request, ModelMap modelMap) throws Exception {
        String tokenCookie = "";

        if ((request.getCookies() != null)) {
            tokenCookie = Arrays.stream(request.getCookies())
                    .filter(c -> c.getName().equals("token_reset_password"))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElse(null);
        }
        if(token.equals(tokenCookie)){
            boolean validateToken = jwtProvider.validateJwtToken(token);
            if (validateToken) {
                modelMap.put("message","");
                return "change-forgot-password";
            }
        }

        throw new Exception("Truy cap khong hop le");
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestParam String newPassword, @RequestParam String confirmPassword ,
                                HttpServletRequest request, ModelMap modelMap , HttpServletResponse response) throws Exception {
        String tokenCookie = "";
        if(!newPassword.equals(confirmPassword)){
            modelMap.put("message","New password and old password not equals");
            return "change-forgot-password";
        }
        if ((request.getCookies() != null)) {
            tokenCookie = Arrays.stream(request.getCookies())
                    .filter(c -> c.getName().equals("token_reset_password"))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElse(null);
        }
        if(tokenCookie != null){
            boolean validateToken = jwtProvider.validateJwtToken(tokenCookie);
            if (validateToken) {
                String username = jwtProvider.getUserNameFromJwtToken(tokenCookie);
                User user = userService.findUserByAccount(username);
                user.setPassword(encoder.encode(newPassword));
                userService.addOrUpdateUser(user);

                Cookie cookie = new Cookie("token_reset_password", null);
                cookie.setMaxAge(0);
                cookie.setSecure(true);
                cookie.setHttpOnly(true);
                cookie.setPath("/"); // global cookie accessible every where
                response.addCookie(cookie);

                System.out.println(Arrays.stream(request.getCookies())
                        .filter(c -> c.getName().equals("token_reset_password"))
                        .findFirst()
                        .map(Cookie::getValue)
                        .orElse(null));
                return "redirect:/login?changePassword=true";
            }
        }
        throw new Exception("Truy cap khong hop le");
    }

}
