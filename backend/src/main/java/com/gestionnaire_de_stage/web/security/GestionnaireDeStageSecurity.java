package com.gestionnaire_de_stage.web.security;

import com.gestionnaire_de_stage.services.JwtUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@Slf4j
public class GestionnaireDeStageSecurity {

    @Profile("prod")
    public static class Production extends WebSecurityConfigurerAdapter {

        static final String[] WHITELISTED_URLS = {
                "/h2/**",
                "/auth/login**",
                "/auth/signup",
                "/favicon.ico"
        };

        @Autowired
        private JwtUserDetailsService userDetailsService;

        @Autowired
        private JwtRequestFilter jwtRequestFilter;

        @Autowired
        private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

        //@formatter:off
        @Override
        protected void configure(final HttpSecurity http) throws Exception {
            log.debug("running in production mode security");
            http.csrf()
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .and()
                .cors()
            .and()
                .headers()
                    .frameOptions()
                        .disable()
                    .xssProtection()
                .and()
                    .cacheControl()
                .and()
            .and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                    .authorizeRequests()
                        .antMatchers(WHITELISTED_URLS).permitAll()
                        .anyRequest().authenticated()
            .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .and()
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        }
        //@formatter:on

        @Override
        public void configure(WebSecurity web) {
            web.ignoring().antMatchers(WHITELISTED_URLS);
        }

        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
        }

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Bean
        public PasswordEncoder bCryptPasswordEncoder() {
            final int SECURITY_LEVEL = 10;
            return new BCryptPasswordEncoder(SECURITY_LEVEL);
        }
    }


    @Profile("dev")
    public static class Developpement extends WebSecurityConfigurerAdapter{

        @Override
        protected void configure(final HttpSecurity http) throws Exception {
           log.debug("running in security dev mode...");
        }

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Bean
        public PasswordEncoder bCryptPasswordEncoder() {
            final int SECURITY_LEVEL = 10;
            return new BCryptPasswordEncoder(SECURITY_LEVEL);
        }
    }
}
