package net.engineeringdigest.journalApp.Services;


import net.engineeringdigest.journalApp.Entity.JournalEntry;
import net.engineeringdigest.journalApp.Entity.Users;
import net.engineeringdigest.journalApp.Repository.JournalEntryRepo;
import net.engineeringdigest.journalApp.Repository.UserEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JournalServices {

    private JournalEntryRepo journalEntryRepo;

    @Autowired
    public void setJournalEntryRepo(JournalEntryRepo journalEntryRepo) {
        this.journalEntryRepo = journalEntryRepo;
    }

    private UserEntryRepo userEntryRepo;

    @Autowired
    public void setUserEntryRepo(UserEntryRepo userEntryRepo) {
        this.userEntryRepo = userEntryRepo;
    }

    @Transactional
    public ResponseEntity<Users> createJournalEntriesOfUser(JournalEntry journalEntry) {
        try {
            Date date = new Date();
            journalEntry.setDate(date);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Users userIndb = userEntryRepo.findByusername(auth.getName());
            Users user = userEntryRepo.findByusername(userIndb.getUsername());
            JournalEntry saved = journalEntryRepo.save(journalEntry);
            user.getJournalEntries().add(saved);
            userEntryRepo.save(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public List<JournalEntry> getEntry() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users userIndb = userEntryRepo.findByusername(auth.getName());
        Users user = userEntryRepo.findByusername(userIndb.getUsername());
        return user.getJournalEntries();
    }

    public ResponseEntity<JournalEntry> getEntryID(ObjectId id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users userIndb = userEntryRepo.findByusername(auth.getName());
        List<JournalEntry> list = userIndb.getJournalEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        if (!list.isEmpty()) {
            return new ResponseEntity<>(journalEntryRepo.findById(id).orElse(null), HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<JournalEntry> updateData(JournalEntry newEntry, ObjectId journalID) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users userIndb = userEntryRepo.findByusername(auth.getName());
        List<JournalEntry> list = userIndb.getJournalEntries().stream().filter(x -> x.getId().equals(journalID)).collect(Collectors.toList());
        if (!list.isEmpty()) {
            JournalEntry oldEntry = list.get(0);
            oldEntry.setTitle(newEntry.getTitle());
            oldEntry.setContent(newEntry.getContent());
            return new ResponseEntity<>(journalEntryRepo.save(oldEntry), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    public ResponseEntity<List<JournalEntry>> getJournalID(String username) {
        Users user = userEntryRepo.findByusername(username);
        List<JournalEntry> list = user.getJournalEntries();
        return new ResponseEntity<>(list, HttpStatus.FOUND);
    }

    public ResponseEntity<String> deleteJournalEntry(ObjectId journalID) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users userIndb = userEntryRepo.findByusername(auth.getName());
        List<JournalEntry> list = userIndb.getJournalEntries().stream().filter(x -> x.getId().equals(journalID)).collect(Collectors.toList());
        if (!list.isEmpty()) {
            userIndb.getJournalEntries().removeIf(x -> x.getId().equals(journalID));
            userEntryRepo.save(userIndb);
            journalEntryRepo.deleteById(journalID);
            return new ResponseEntity<>("Record Delete Successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Given ID is not associated With your Username or It may not be Present.", HttpStatus.FORBIDDEN);
    }
}
