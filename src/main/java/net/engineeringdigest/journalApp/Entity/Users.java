package net.engineeringdigest.journalApp.Entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "Users")
@Data  //This is necessary
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users {
    @Id
    private ObjectId id ;

    private String email ;
    private boolean sentimentAnalysis ;

    @Indexed(unique = true)
    @NonNull
    private String username ;
    @NonNull
    private String password ;
    @DBRef
    List<JournalEntry> journalEntries = new ArrayList<>();

    private List<String> roles =new ArrayList<>();


}
