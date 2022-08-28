package ro.ubb.pm.bll;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ro.ubb.pm.bll.epics.EpicFactory;
import ro.ubb.pm.bll.projects.ProjectFactory;
import ro.ubb.pm.bll.sprints.SprintFactory;
import ro.ubb.pm.bll.tasks.TaskFactory;
import ro.ubb.pm.bll.users.UserFactory;
import ro.ubb.pm.bll.userstories.UserStoryFactory;
import ro.ubb.pm.bll.userstories.UserStoryMapper;

@Configuration
@Import({UserFactory.class, TaskFactory.class, SprintFactory.class, EpicFactory.class,
        ProjectFactory.class, UserStoryFactory.class
     })
@ComponentScan(basePackages = {"ro.ubb.pm.bll"})
public class BLLAutoConfiguration {
}
