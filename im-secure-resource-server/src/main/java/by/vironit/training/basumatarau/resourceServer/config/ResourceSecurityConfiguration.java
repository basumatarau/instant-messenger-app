package by.vironit.training.basumatarau.resourceServer.config;

import by.vironit.training.basumatarau.resourceServer.exception.CustomAccessDeniedHandler;
import by.vironit.training.basumatarau.resourceServer.exception.CustomAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceSecurityConfiguration extends ResourceServerConfigurerAdapter {
    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId("api");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .antMatcher("/api/**")
                    .authorizeRequests()
                    .antMatchers("/api/user/info**").permitAll()
                    .antMatchers("/api/user/signin**").permitAll()
                    .antMatchers("/api/user**").hasAnyAuthority("ADMIN", "USER")
                    .antMatchers("/api/admin**").hasAuthority("ADMIN")
                    .antMatchers("/api/**").authenticated()
                    .anyRequest().authenticated()
                .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(customAuthenticationEntryPoint)
                    .accessDeniedHandler(new CustomAccessDeniedHandler());
    }
}
