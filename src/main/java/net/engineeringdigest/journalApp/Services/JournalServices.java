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

    @Autowired
    private JournalEntryRepo journalEntryRepo;
    @Autowired
    private UserEntryRepo userEntryRepo;


    @Autowired
    private UserEntryRepo userDB;

    @Transactional
    public ResponseEntity<?> createJournalEntriesOfUser(JournalEntry journalEntry) {
        try {
            Date date = new Date();
            journalEntry.setDate(date);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Users userIndb = userEntryRepo.findByusername(auth.getName());
            Users user = userDB.findByusername(userIndb.getUsername());
            JournalEntry saved = journalEntryRepo.save(journalEntry);
            user.getJournalEntries().add(saved);
            userDB.save(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    public List<JournalEntry> getEntry() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users userIndb = userEntryRepo.findByusername(auth.getName());
        Users user = userDB.findByusername(userIndb.getUsername());
        return user.getJournalEntries();
    }

    public ResponseEntity<?> getEntryID(ObjectId id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users userIndb = userEntryRepo.findByusername(auth.getName());
        List<JournalEntry> list = userIndb.getJournalEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        if (!list.isEmpty()) {
            return new ResponseEntity<>(journalEntryRepo.findById(id).orElse(null), HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>("Given ID is not associated With your Username or It may not be Present.", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> updateData(JournalEntry newEntry, ObjectId journalID) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users userIndb = userEntryRepo.findByusername(auth.getName());
        List<JournalEntry> list = userIndb.getJournalEntries().stream().filter(x -> x.getId().equals(journalID)).collect(Collectors.toList());
        if (!list.isEmpty()) {
            JournalEntry oldEntry = list.get(0);
            oldEntry.setTitle(newEntry.getTitle());
            oldEntry.setContent(newEntry.getContent());
            return new ResponseEntity<>(journalEntryRepo.save(oldEntry), HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Given ID is not associated With your Username or It may not be Present.", HttpStatus.FORBIDDEN);
    }

    public ResponseEntity<?> getJournalID(String username) {
        Users user = userDB.findByusername(username);
        List<JournalEntry> list = user.getJournalEntries();
        return new ResponseEntity<>(list, HttpStatus.FOUND);
    }

    public ResponseEntity<?> deleteJournalEntry( ObjectId journalID) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users userIndb = userEntryRepo.findByusername(auth.getName());
        List<JournalEntry> list = userIndb.getJournalEntries().stream().filter(x -> x.getId().equals(journalID)).collect(Collectors.toList());
        if (!list.isEmpty()) {
            userIndb.getJournalEntries().removeIf(x -> x.getId().equals(journalID));
            userDB.save(userIndb);
            journalEntryRepo.deleteById(journalID);
            return new ResponseEntity<>("Record Delete Successfully",HttpStatus.OK);
        }
        return new ResponseEntity<>("Given ID is not associated With your Username or It may not be Present.", HttpStatus.FORBIDDEN);
    }
}
