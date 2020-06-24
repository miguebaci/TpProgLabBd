package utn.edu.tpfinal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import utn.edu.tpfinal.session.AntennaSessionFilter;
import utn.edu.tpfinal.session.BackofficeSessionFilter;
import utn.edu.tpfinal.session.SessionFilter;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;


@org.springframework.context.annotation.Configuration
@PropertySource("application.properties")
@EnableSwagger2WebMvc
@EnableScheduling
public class Configuration {

    @Autowired
    SessionFilter sessionFilter;
    @Autowired
    BackofficeSessionFilter backofficeSessionFilter;
    @Autowired
    AntennaSessionFilter antennaSessionFilter;

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

    @Bean
    public FilterRegistrationBean antennaFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(antennaSessionFilter);
        registration.addUrlPatterns("/antenna/*");
        return registration;
    }
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("utn.edu"))
                .paths(PathSelectors.any())
                .build();
    }
}
