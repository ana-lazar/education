package ro.ubb.pm.dal;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages="ro.ubb.pm.*")
@EntityScan(basePackages="ro.ubb.pm.*")
public class EntitiesConfiguration {
}
