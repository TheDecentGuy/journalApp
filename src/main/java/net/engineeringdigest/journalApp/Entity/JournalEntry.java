package net.engineeringdigest.journalApp.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Document(collection = "journaldb")
@Data
@NoArgsConstructor // which is required for Deserialization like JSON to POJO
public class JournalEntry {
    @Id
    private ObjectId id ;
    private String title ;
    private String content ;
    private Date date;


}
