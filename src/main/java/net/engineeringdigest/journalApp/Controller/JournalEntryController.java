package net.engineeringdigest.journalApp.Controller;


import net.engineeringdigest.journalApp.Entity.JournalEntry;
import net.engineeringdigest.journalApp.Entity.Users;
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

    @GetMapping("{username}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String username) {

        List<JournalEntry> list = services.getEntry(username);
        if (list != null) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("{username}")
    public ResponseEntity<?> createJournalEntriesOfUser(@RequestBody JournalEntry Journal, @PathVariable String username) {
        return services.createJournalEntriesOfUser(Journal, username);
    }

    @GetMapping("GetByID/{MyID}")
    public ResponseEntity<?> GetByID(@PathVariable ObjectId MyID) {
        Optional<JournalEntry> journalEntry = services.getEntryID(MyID);
        if (journalEntry.isPresent()) {
            return new ResponseEntity<>(journalEntry, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("update/{JournalID}")
    public ResponseEntity<?> updateByID(@PathVariable ObjectId JournalID, @RequestBody JournalEntry NewEntry) {
        ResponseEntity<?> newEntry = services.UpdateData(NewEntry, JournalID);
        if (newEntry != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @GetMapping("getJournalID/{username}")
    public ResponseEntity<?> getJournalID(@PathVariable String username) {
        return services.getJournalID(username);
    }

    @DeleteMapping("{username}/{journalID}")
    public ResponseEntity<?> deleteJournal(@PathVariable String username, @PathVariable ObjectId journalID) {
        return services.deleteJournalEntry(username,journalID);
    }

}
