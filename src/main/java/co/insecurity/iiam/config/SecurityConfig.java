package co.insecurity.iiam.config;

import co.insecurity.iiam.core.domain.UserRole;
import co.insecurity.iiam.event.users.CreateUserEvent;
import co.insecurity.iiam.event.users.UserInfo;
import co.insecurity.iiam.persistence.service.UserPersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private UserPersistenceService userService;

    @Autowired
    private SessionRegistry sessionRegistry;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth,
                                @Value("${admin.username}") String adminUsername,
                                @Value("${admin.password}") String adminPassword)
            throws Exception {
        auth.authenticationProvider(authenticationProvider);
        UserInfo adminUser = new UserInfo(
                adminUsername,
                passwordEncoder.encode(adminPassword));
        adminUser.getRoles().add(UserRole.ADMIN);
        userService.createUser(new CreateUserEvent(adminUser));
        LOG.debug("Added default admin user: {}", adminUser);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .requiresChannel()
                .anyRequest()
                    .requiresSecure()
                .and()
            .authorizeRequests()
                .antMatchers("/admin/**")
                    .hasRole(UserRole.ADMIN.toString())
                .antMatchers("/user/**")
                    .hasRole(UserRole.USER.toString())
                .antMatchers("/**")
                    .permitAll()
                .and()
            .formLogin()
                .loginPage("/login")
                    .permitAll()
                .and()
            .logout()
                .permitAll()
                .and()
            .sessionManagement()
                .maximumSessions(1)
                .expiredUrl("/login?logout")
                .sessionRegistry(sessionRegistry)
                .and()
                .invalidSessionUrl("/login?logout");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public SessionRegistry getSessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider getAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder);
        authProvider.setUserDetailsService(userService);
        return authProvider;
    }
}

