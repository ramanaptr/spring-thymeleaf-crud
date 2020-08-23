package com.ramanaptr.thymeleaf.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.h2.H2ConsoleProperties
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.stereotype.Component


@Component
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var console: H2ConsoleProperties

    @Autowired
    private lateinit var userService: UserDetailsService

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(authenticationProvider())
    }

    private fun authenticationProvider(): AuthenticationProvider {
        val auth = DaoAuthenticationProvider()
        auth.setUserDetailsService(userService)
        auth.setPasswordEncoder(passwordEncoder())
        return auth
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder(5)
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
                .antMatchers(JS, CSS).permitAll()
//                .antMatchers(REGISTER, FORGET_PASSWORD, USER_REGISTER).permitAll()
                .antMatchers(ALL).permitAll()
                .mvcMatchers("robot.txt").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
                .and().csrf().ignoringAntMatchers(REGISTER)

        h2SecurityAccess()
    }

    private fun h2SecurityAccess() {
        // H2 security allowed
        val path: String = this.console.path
        val antPattern = if (path.endsWith("/")) "$path**" else "$path/**"
        val h2Console = http.antMatcher(antPattern)
        h2Console.csrf().disable()
        h2Console.httpBasic()
        h2Console.headers().frameOptions().sameOrigin()
    }

    companion object {
        private const val ALL = "/**"
        private const val JS = "/js/**"
        private const val CSS =  "/css/**"
        private const val REGISTER = "/register/**"
        private const val FORGET_PASSWORD = "/forget-password/**"
        private const val USER_REGISTER = "/user/register/**"
    }
}