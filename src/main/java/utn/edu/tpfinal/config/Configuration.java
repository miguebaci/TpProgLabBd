package utn.edu.tpfinal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import utn.edu.tpfinal.session.BackofficeSessionFilter;
import utn.edu.tpfinal.session.SessionFilter;

@org.springframework.context.annotation.Configuration
@PropertySource("application.properties")
@EnableScheduling
public class Configuration {

    @Autowired
    SessionFilter sessionFilter;
    @Autowired
    BackofficeSessionFilter backofficeSessionFilter;

    @Bean
    public FilterRegistrationBean userFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(sessionFilter);
        registration.addUrlPatterns("/api/*");
        return registration;
    }

    @Bean
    public FilterRegistrationBean backofficeFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(backofficeSessionFilter);
        registration.addUrlPatterns("/backoffice/*");
        return registration;
    }
}
