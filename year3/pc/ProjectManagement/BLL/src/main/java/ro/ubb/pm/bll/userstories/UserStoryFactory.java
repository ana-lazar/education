package ro.ubb.pm.bll.userstories;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.ubb.pm.bll.users.UserMapper;
import ro.ubb.pm.bll.users.UserMapperImpl;

@Configuration
public class UserStoryFactory {

    @Bean
    public UserStoryMapper userStoryMapper() {
        return new UserStoryMapperImpl();
    }
}
