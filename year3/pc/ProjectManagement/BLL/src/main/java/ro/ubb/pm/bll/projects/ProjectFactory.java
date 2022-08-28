package ro.ubb.pm.bll.projects;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectFactory {

    @Bean
    public ProjectMapper projectMapper() {
        return new ProjectMapperImpl();
    }
}
