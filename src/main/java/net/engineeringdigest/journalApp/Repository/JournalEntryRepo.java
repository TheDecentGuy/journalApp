package net.engineeringdigest.journalApp.Repository;

import net.engineeringdigest.journalApp.Entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepo extends MongoRepository<JournalEntry, ObjectId> /*<Entity,Data Type (Primary Key)>*/ {
}
