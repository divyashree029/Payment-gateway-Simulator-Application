package payment_gateway.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import payment_gateway.security.JwtUtil;

import java.util.Map;

//all 3 annotations below defines API endpoints
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;
    //jwtUtil= custom class for JWT token generation


    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        if ("admin".equals(username) && "password".equals(password)) {
            String token = jwtUtil.generateToken(username); //username → encoded → signed → JWT token
            return Map.of("token", token);
        }
        throw new RuntimeException("Invalid credentials");

    }
}

//401	Security issue
//403	Authorization issue
//404	Controller not found
