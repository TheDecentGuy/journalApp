package net.engineeringdigest.journalApp.Entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Document(collection = "journaldb")
@Getter
@Setter
@Data //This is necessary
@AllArgsConstructor
@Builder
@NoArgsConstructor // which is required for Deserialization like JSON to POJO
public class JournalEntry {
    @Id
    private ObjectId id ;
    private String title ;
    private String content ;
    private Date date;


}
