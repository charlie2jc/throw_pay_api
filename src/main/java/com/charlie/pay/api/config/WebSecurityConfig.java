package com.charlie.pay.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true, jsr250Enabled=true, prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.headers().frameOptions().sameOrigin().and()
				.cors().and()
				.csrf().disable()
				.authorizeRequests()
				.antMatchers("/api/throw", "/api/receive", "/favicon.ico", "/h2-console/**").permitAll()
				.anyRequest().authenticated().and()
				.exceptionHandling().and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
}