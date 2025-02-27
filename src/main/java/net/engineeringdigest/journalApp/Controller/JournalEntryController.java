package net.engineeringdigest.journalApp.Controller;


import net.engineeringdigest.journalApp.Entity.JournalEntry;
import net.engineeringdigest.journalApp.Services.JournalServices;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("Journal")
public class JournalEntryController {

    @Autowired
    private JournalServices services;

    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser() {

        List<JournalEntry> list = services.getEntry();
        if (list != null) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> createJournalEntriesOfUser(@RequestBody JournalEntry Journal) {
        return services.createJournalEntriesOfUser(Journal);
    }

    @GetMapping("{MyID}")
    public ResponseEntity<?> GetByID(@PathVariable ObjectId MyID) {
        return services.getEntryID(MyID);
    }

    @PutMapping("{JournalID}")
    public ResponseEntity<?> updateByID(@PathVariable ObjectId JournalID, @RequestBody JournalEntry NewEntry) {
        return services.updateData(NewEntry, JournalID);
    }

    @DeleteMapping("{journalID}")
    public ResponseEntity<?> deleteJournal( @PathVariable ObjectId journalID) {
        return services.deleteJournalEntry(journalID);
    }

}
