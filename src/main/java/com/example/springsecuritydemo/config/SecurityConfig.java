package com.example.springsecuritydemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/auth/login").permitAll()
                .defaultSuccessUrl("/auth/success")
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", "POST"))
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/auth/login");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }


    /* ?????????????????? ???????????????????? http
     * antMatchers ?????????????????? ???? ?????????? url ?????? ?????????? ????????????
     * permitAll - ?????? ?????????? ????????????
     *  HttpMethod.GET, "/api/**" ???? ?????????????? ???????????? ?????????? ?????????? ???? ????????????
     * hasAnyRole - ?????????? ???? ?????????????????? ??????????
     * httpBasic - ?? ???????????????????? Base64
     * formLogin - ?????????????????? ???????????????????? ?????????? ???????????? ?? ????????????
     * .logout().logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", "POST")); - ???????????? ????????????????????????????
     * ???????????? ???? ???????????? /auth/logout ?? ?????????? ???????????????????????? POST.
     */
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {

        //first version
//        http
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/")
//                .permitAll()
//                .antMatchers(HttpMethod.GET, "/api/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
//                .antMatchers(HttpMethod.POST, "/api/**").hasRole(Role.ADMIN.name())
//                .antMatchers(HttpMethod.DELETE, "/api/**").hasRole(Role.ADMIN.name())
//                .anyRequest()
//                .authenticated()
//                .and()
//                .httpBasic();
        //second version
//        http
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/")
//                .permitAll()
//                .antMatchers(HttpMethod.GET, "/api/**").hasAuthority(Permission.DEVELOPERS_READ.getPermission())
//                .antMatchers(HttpMethod.POST, "/api/**").hasAuthority(Permission.DEVELOPERS_WRITE.getPermission())
//                .antMatchers(HttpMethod.DELETE, "/api/**").hasAuthority(Permission.DEVELOPERS_WRITE.getPermission())
//                .anyRequest()
//                .authenticated()
//                .and()
//                .httpBasic();

        //third version
//        http
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/")
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .httpBasic();
        //fourth version
//        http
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/")
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/auth/login").permitAll()
//                .defaultSuccessUrl("/auth/success")
//                .and()
//                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout", "POST"))
//                .invalidateHttpSession(true)
//                .clearAuthentication(true)
//                .deleteCookies("JSESSIONID")
//                .logoutSuccessUrl("/auth/login");
//
//
//    }
//
//
//    /* ?????? ?????? ???????????? ???????????????????????? ???????????????? ?? ????????????, ???? ?????????????????? ???? ?? ????????????*/
//
//    @Bean
//    @Override
//    protected UserDetailsService userDetailsService() {
//        return new InMemoryUserDetailsManager(
//
//
//                //second version
//                User.builder()
//                        .username("admin")
//                        .password(passwordEncoder().encode("admin"))
//                        .authorities(Role.ADMIN.getAuthorities())
//                        .build(),
//                User.builder()
//                        .username("user")
//                        .password(passwordEncoder().encode("user"))
//                        .authorities(Role.USER.getAuthorities())
//                        .build()
//
//
//                //1-???? ????????????
//
////                User.builder()
////                        .username("admin")
////                        .password(passwordEncoder().encode("admin"))
////                        .roles(Role.ADMIN.name())
////                        .build(),
////                User.builder()
////                        .username("user")
////                        .password(passwordEncoder().encode("user"))
////                        .roles(Role.USER.name())
////                        .build()
//        );
//
//    }
//
//    /* ?????? ?????? ???????????????? ???????????? ?? ????????????*/
//    @Bean
//    protected PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(12);
//    }
}
