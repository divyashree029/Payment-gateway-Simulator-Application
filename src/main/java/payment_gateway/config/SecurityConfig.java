package payment_gateway.config;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import payment_gateway.security.JwtFilter;

@Configuration //class have config for app and this registers bean and applies security lines
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtFilter jwtFilter;
    @Bean    //
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    // SecurityFilterChain= req->security filter->controller
    // HttpSecurity http = here authenti, authoriz, CSRF and headers are configured


        http
                .csrf(csrf -> csrf.disable())   // .csrf(csrf -> csrf.disable()= someone tricks our browser by sending request hence disabling it bcz aplication is stateless REST API'S
                .authorizeHttpRequests(auth -> auth  //says who n all can access it
                        .requestMatchers("/auth/**").permitAll()   // allow login
                        .requestMatchers("/api/**").authenticated()  //api/refund → protected   , allow payment (for now)
                        //.anyRequest().authenticated()) //Any endpoint NOT matching /api/**
                                //.anyRequest().permitAll()   //allow EVERYTHING
                        //.formLogin(form -> form.disable())   // disable default login page
                        //.httpBasic(basic -> basic.disable()); // disable basic auth
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
                


        return http.build();
    }
}

//1. Request comes
//2. SecurityFilterChain intercepts
//3. Matches "/api/**"
//4. permitAll → allowed
//5. Goes to Controller
//6. Payment processed