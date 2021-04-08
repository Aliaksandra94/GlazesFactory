package com.project.moroz.glazes_market.config;

import com.project.moroz.glazes_market.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/info/**", "/locale/**", "/registration/**", "/catalog",
                        "/contacts/**", "authorization", "products", "/resources/**", "/h2/**", "/css/**",
                        "/js/**", "/images/**", "/basket").permitAll()
                .antMatchers("/basket", "/basket/**", "/catalog/create/**").hasRole("USER")
                .antMatchers("/basket", "/basket/**", "/catalog/create/**").not().hasAnyRole("SELLER", "ADMIN", "PURCHASER", "PRODUCER")
                .antMatchers("/order/**").authenticated()
                .antMatchers("/sendEmail/**").hasAnyRole("USER", "SELLER", "ADMIN" )
                .antMatchers("/users/**", "/catalog/add/**", "/catalog/edit/**").hasAnyRole("SELLER", "ADMIN", "PURCHASER")
                .antMatchers("/userPage/orderHistory/activeTasks").hasAnyRole("SELLER", "ADMIN", "USER")
                .antMatchers("/userPage/reports/**").authenticated()
                .antMatchers("/userPage/reports/**").not().hasRole("USER")
                .antMatchers("/managers/**", "/userPage/reports/onManagers").hasRole("ADMIN")
                .antMatchers("/userPage/reports/onClients").not().hasAnyRole("USER", "PURCHASER", "PRODUCER")
                .antMatchers("/userPage/reports/onGlazesTypes", "/userPage/reports/onProducts", "/userPage/reports/onOrders").hasAnyRole("ADMIN", "SELLER", "PURCHASER", "PRODUCER")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin().loginPage("/login")
                .loginProcessingUrl("/login").permitAll()
                .defaultSuccessUrl("/login/successEnter", true)
                .usernameParameter("login")
                .passwordParameter("password")
                .failureUrl("/login/failedEnter") // default is /login?error
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/login").permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userService);
        return daoAuthenticationProvider;
    }
}
