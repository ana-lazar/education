package ro.ubb.pm.bll.sprints;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SprintFactory {

    @Bean
    public SprintMapper sprintMapper() {
        return new SprintMapperImpl();
    }


}
