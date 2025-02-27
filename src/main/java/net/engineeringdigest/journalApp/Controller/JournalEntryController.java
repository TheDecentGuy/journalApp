package net.engineeringdigest.journalApp.Controller;


import net.engineeringdigest.journalApp.Entity.JournalEntry;
import net.engineeringdigest.journalApp.Entity.Users;
import net.engineeringdigest.journalApp.Services.JournalServices;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("Journal")
public class JournalEntryController {

    private JournalServices journalServices;
    @Autowired
    private void setJournalServices(JournalServices journalServices) {
        this.journalServices =  journalServices;
    }

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser() {

        List<JournalEntry> list = journalServices.getEntry();
        if (list != null) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Users> createJournalEntriesOfUser(@RequestBody JournalEntry Journal) {
        return journalServices.createJournalEntriesOfUser(Journal);
    }

    @GetMapping("{myID}")
    public ResponseEntity<JournalEntry> getByID(@PathVariable ObjectId myID) {
        return journalServices.getEntryID(myID);
    }

    @PutMapping("{journalID}")
    public ResponseEntity<JournalEntry> updateByID(@PathVariable ObjectId journalID, @RequestBody JournalEntry newEntry) {
        return journalServices.updateData(newEntry, journalID);
    }

    @DeleteMapping("{journalID}")
    public ResponseEntity<String> deleteJournal(@PathVariable ObjectId journalID) {
        return journalServices.deleteJournalEntry(journalID);
    }

}
