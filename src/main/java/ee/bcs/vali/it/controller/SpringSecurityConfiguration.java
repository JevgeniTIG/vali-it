package ee.bcs.vali.it.controller;

        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.core.annotation.Order;
        import org.springframework.security.config.annotation.web.builders.HttpSecurity;
        import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
        import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
        import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity

public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/styles.css", "/logo.png", "/images/**",
                        "/pic_carousel_1.jpg", "/register_new_member", "/member_register_page.html", "/register_new_host",
                        "/host_register_page.html", "/css/**", "/fonts/**", "/", "/add_service", "/show_logged_host_services",
                        "/delete_service", "/show_suitable_services", "/index.html", "/add_experienced_service",
                        "/show_logged_member_services_history", "/rate_service", "/update_rating").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/member_login_page.html")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/member_page.html")


                //.failureUrl("/?error=true")
                .permitAll()
                .and()
                .logout()
                .permitAll();
        http.csrf().disable();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}



