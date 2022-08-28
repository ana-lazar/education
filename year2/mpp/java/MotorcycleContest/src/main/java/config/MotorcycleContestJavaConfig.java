package config;

import domain.validators.ParticipantValidator;
import domain.validators.RaceValidator;
import domain.validators.TeamValidator;
import domain.validators.UserValidator;
import myjdbc.MyJdbcTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import repositories.database.ParticipantJdbcRepository;
import repositories.database.RaceJdbcRepository;
import repositories.database.TeamJdbcRepository;
import repositories.database.UserJdbcRepository;
import repositories.interfaces.ParticipantRepository;
import repositories.interfaces.RaceRepository;
import repositories.interfaces.TeamRepository;
import repositories.interfaces.UserRepository;
import services.MotorcycleContestService;

@Configuration
public class MotorcycleContestJavaConfig {
    @Bean
    public MyJdbcTemplate myJdbcTemplate() {
        return new MyJdbcTemplate("bd.config");
    }

    @Bean
    public UserValidator userValidator() {
        return new UserValidator();
    }

    @Bean
    public RaceValidator raceValidator() {
        return new RaceValidator();
    }

    @Bean
    public TeamValidator teamValidator() {
        return new TeamValidator();
    }

    @Bean
    public ParticipantValidator participantValidator() {
        return new ParticipantValidator();
    }

    @Bean
    public UserRepository userRepository() {
        return new UserJdbcRepository(myJdbcTemplate(), userValidator());
    }

    @Bean
    public RaceRepository raceRepository() {
        return new RaceJdbcRepository(myJdbcTemplate(), raceValidator());
    }

    @Bean
    public TeamRepository teamRepository() {
        return new TeamJdbcRepository(myJdbcTemplate(), teamValidator());
    }

    @Bean
    public ParticipantRepository participantRepository() {
        return new ParticipantJdbcRepository(myJdbcTemplate(), participantValidator());
    }

    @Bean(name="service")
    public MotorcycleContestService motorcycleContestService() {
        return new MotorcycleContestService(userRepository(), raceRepository(), teamRepository(), participantRepository());
    }
}
