package cs5031.group.one.thelibrary.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * This is the configuration file for clients connecting.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    // This class is used to configure the web application.
    @SuppressWarnings("null")
    @Override
    public void addCorsMappings(org.springframework.web.servlet.config.annotation.CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*");
    }

}
