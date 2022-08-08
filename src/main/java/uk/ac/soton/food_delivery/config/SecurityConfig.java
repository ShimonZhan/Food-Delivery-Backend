package uk.ac.soton.food_delivery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uk.ac.soton.food_delivery.filter.JwtAuthenticationTokenFilter;

import javax.annotation.Resource;

/**
 * @Author: Shimon Zhan
 * @Date: 2022-03-11 06:50:26
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Resource
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Resource
    private AccessDeniedHandler accessDeniedHandler;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()// 由于使用的是JWT，我们这里不需要csrf
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// 基于token，所以不需要session
                .and().authorizeRequests()
//                .antMatchers("/**/*").permitAll()        // permit all
//                .antMatchers(HttpMethod.GET, // allow swagger3
//                        "/swagger-ui/**",
//                        "/swagger-resources/**",
//                        "/v3/api-docs"
//                ).permitAll()
//                .antMatchers(
//                        "/user/login",
//                        "/user/register",
//                        "/user/activate",
//                        "/user/sendActivateMail",
//                        "/user/sendForgetPasswordMail",
//                        "/user/changeForgetPassword"
//                ).anonymous() // 对登录注册要允许匿名访问

//                .antMatchers("/test/**").permitAll() // 测试接口
//                .antMatchers(HttpMethod.OPTIONS).permitAll()//跨域请求会先进行一次options请求
                .anyRequest().permitAll();
        // add filter
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        // config ExceptionHandler
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);
        // allow cors
        http.cors();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
