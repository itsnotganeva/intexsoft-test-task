package by.ganevich.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"by.ganevich"})
public class ApplicationConfig {

    @Bean
    public ModelMapper getMapper() {
        return new ModelMapper();
    }
}
