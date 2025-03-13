package edu.eci.arsw.blueprints.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import edu.eci.arsw.blueprints.filter.BlueprintFilter;
import edu.eci.arsw.blueprints.filter.impl.RedundancyFilter;

@Configuration
@ComponentScan(basePackages = "edu.eci.arsw.blueprints")
public class AppConfig {

    @Bean
    @Primary
    public BlueprintFilter blueprintFilter() {
        
        return new RedundancyFilter();
    
    }
}