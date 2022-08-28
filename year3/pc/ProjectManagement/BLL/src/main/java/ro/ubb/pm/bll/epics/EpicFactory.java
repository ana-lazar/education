package ro.ubb.pm.bll.epics;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EpicFactory {

    @Bean
    public EpicMapper epicMapper() {
        return new EpicMapperImpl();
    }


}