package br.com.metrocamp.livraria.utils.morphia;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.github.ganchix.morphia.configuration.MorphiaAutoConfiguration;
    
@Configuration
@Import(MorphiaAutoConfiguration.class)
public class MorphiaConfig {
    
}
