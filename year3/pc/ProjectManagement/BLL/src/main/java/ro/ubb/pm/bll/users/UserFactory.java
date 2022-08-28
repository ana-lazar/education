package ro.ubb.pm.bll.users;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserFactory {

    @Bean
    public UserMapper userMapper() {
        return new UserMapperImpl();
    }
}
