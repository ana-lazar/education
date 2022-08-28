package ro.ubb.pm.dal;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(EntitiesConfiguration.class)
@ComponentScan(basePackages="ro.ubb.pm.*")
public class DomainAutoConfiguration {
}
