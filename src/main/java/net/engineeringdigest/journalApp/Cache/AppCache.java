package net.engineeringdigest.journalApp.Cache;

import net.engineeringdigest.journalApp.Entity.ConfigJournalAppEntity;
import net.engineeringdigest.journalApp.Repository.ConfigJournalAppRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    public Map<String,String>APP_CACHE = new HashMap<>();



    private ConfigJournalAppRepo configJournalAppRepo;
    @Autowired
    private void setConfigJournalAppEntity(ConfigJournalAppRepo configJournalAppRepo){
        this.configJournalAppRepo =configJournalAppRepo;
    }
    @PostConstruct
    public void init(){
        List<ConfigJournalAppEntity> list = configJournalAppRepo.findAll();
        for(ConfigJournalAppEntity entity : list){
            APP_CACHE.put(entity.getKey(), entity.getValue());

        }

    }

}
