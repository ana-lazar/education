package ro.ubb.pm.bll.epics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.ubb.pm.dal.EpicsRepository;
import ro.ubb.pm.model.Epic;

import java.util.List;

@Component
public class EpicBLL {

    EpicsRepository epicsRepository;

    @Autowired
    public void setEpicsRepository(EpicsRepository epicsRepository) {
        this.epicsRepository = epicsRepository;
    }

    public List<Epic> getAllEpicsByProjectId(int projectId){
        return epicsRepository.findAllByProjectId(projectId);
    }
}
