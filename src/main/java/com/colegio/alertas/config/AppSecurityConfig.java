package com.colegio.alertas.config;

import com.colegio.alertas.util.enums.Rol;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author Sistema de Alertas
 */
@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT nombre_usuario, contrasenia, 1 "
                        + "FROM sa_usuario "
                        + "WHERE nombre_usuario = ?")
                .authoritiesByUsernameQuery("SELECT nombre_usuario, rol "
                        + "FROM sa_usuario_rol "
                        + "WHERE nombre_usuario = ?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                /*.antMatchers("/admin/**").hasAuthority(Rol.ADMIN.toString())
                .antMatchers("/docente/**", "/aulas/**").hasAuthority(Rol.DOCENTE.toString())
                .antMatchers("/padre/**").hasAuthority(Rol.PADRE.toString())
                .antMatchers("/css/**", "/fonts/**", "/img/**", "/js/**", "/login", "/*.js").permitAll()*/
                .anyRequest().permitAll()
                .and()
                .formLogin().loginPage("/login").loginProcessingUrl("/springLogin").defaultSuccessUrl("/")
                .and()
                .logout().logoutUrl("/springLogout").deleteCookies("JSESSIONID");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
