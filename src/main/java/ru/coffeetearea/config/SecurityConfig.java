package ru.coffeetearea.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.coffeetearea.model.Role;
import ru.coffeetearea.security.JwtUserDetailsService;
import ru.coffeetearea.security.jwt.JwtConfigurer;
import ru.coffeetearea.security.jwt.JwtTokenProvider;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // Fields
    //
//    private final JwtTokenProvider jwtTokenProvider;

    private final JwtUserDetailsService jwtUserDetailsService;

    /**
     * Использован BCrypt
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    @Bean
    public JwtTokenProvider jwtTokenProvider(JwtUserDetailsService userDetailsService) {
        return new JwtTokenProvider(userDetailsService);
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    /**
     * Метод конфигураций.
     * Вместо аннотаций над методами и классами для доступа по ролям,
     * было решено использовать antMatchers с ограничениями по ссылкам(по ролям).
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().disable().csrf().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**").permitAll()
                .antMatchers("/home/login",
                        "/cart-item", "/cart-drinks", "/**/add-cart/", "/**/delete-cart/",
                        "/delete-all-cart", "/drink-add", "/add-in-favourites/**", "/coffee",
                        "/coffees", "/coffees-filter", "/tea", "/teas", "/teas-filter",
                        "/drink", "/**/favourite-delete", "/**/add-favourites/", "/drinks-favourite",
                        "/order", "/make-order", "/coffee-add", "/tea-add", "/{coffeeId}/coffee-delete",
                        "/{teaId}/tea-delete", "/**/coffee-edit", "/**/tea-edit").permitAll()
                .antMatchers()
                .hasRole(Role.CUSTOMER.name()) // Все ссылки, которые доступны только заказчику
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider(jwtUserDetailsService)));
    }
}