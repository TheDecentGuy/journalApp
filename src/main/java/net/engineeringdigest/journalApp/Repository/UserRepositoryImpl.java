package net.engineeringdigest.journalApp.Repository;

import net.engineeringdigest.journalApp.Entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRepositoryImpl {

    @Autowired
    public MongoTemplate mongoTemplate;


    public List<Users> getUserForSA() {
        Query query = new Query();
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        query.addCriteria(Criteria.where("email").regex(emailRegex));
        query.addCriteria(Criteria.where("SA").is(true));
        query.addCriteria(Criteria.where("roles").in("User"));
        List<Users> list = mongoTemplate.find(query, Users.class);
        return list ;
    }

}
