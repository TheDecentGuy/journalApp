package net.engineeringdigest.journalApp.Services;


import net.engineeringdigest.journalApp.Entity.JournalEntry;
import net.engineeringdigest.journalApp.Entity.Users;
import net.engineeringdigest.journalApp.Repository.JournalEntryRepo;
import net.engineeringdigest.journalApp.Repository.UserEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class JournalServices {

    @Autowired
    private JournalEntryRepo journalEntryRepo;

    @Autowired
    private UserEntryRepo userDB;

    @Transactional
    public ResponseEntity<?> createJournalEntriesOfUser(JournalEntry journalEntry, String username) {
        try {
            Date date = new Date();
            journalEntry.setDate(date);
            Users user = userDB.findByusername(username);
            JournalEntry saved = journalEntryRepo.save(journalEntry);
            user.getJournalEntries().add(saved);
            userDB.save(user);
            return new ResponseEntity<>(user,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
        }

    }

    public List<JournalEntry> getEntry(String username) {
        Users user = userDB.findByusername(username);
        return user.getJournalEntries();
    }

    public Optional<JournalEntry> getEntryID(ObjectId id) {
        return journalEntryRepo.findById(id);
    }

    public ResponseEntity<?> UpdateData(JournalEntry newEntry, ObjectId JournalID) {
        JournalEntry oldEntry = journalEntryRepo.findById(JournalID).orElse(null);
        if (oldEntry != null) {
            oldEntry.setTitle(newEntry.getTitle());
            oldEntry.setContent(newEntry.getContent());
            return new ResponseEntity<>(journalEntryRepo.save(oldEntry), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    public ResponseEntity<?> getJournalID(String username) {
        Users user = userDB.findByusername(username);
        List<JournalEntry> list = user.getJournalEntries();
        return new ResponseEntity<>(list, HttpStatus.FOUND);
    }

    public ResponseEntity<?> deleteJournalEntry(String username, ObjectId journalID) {
        Users user = userDB.findByusername(username);
        user.getJournalEntries().removeIf(x -> x.getId().equals(journalID));
        userDB.save(user);
        journalEntryRepo.deleteById(journalID);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
