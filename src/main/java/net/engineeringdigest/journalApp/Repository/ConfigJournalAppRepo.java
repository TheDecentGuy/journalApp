package net.engineeringdigest.journalApp.Repository;

import net.engineeringdigest.journalApp.Entity.ConfigJournalAppEntity;
import net.engineeringdigest.journalApp.Entity.Users;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

    public interface ConfigJournalAppRepo extends MongoRepository<ConfigJournalAppEntity, ObjectId> /*<Entity,Data Type (Primary Key)>*/ {


}
