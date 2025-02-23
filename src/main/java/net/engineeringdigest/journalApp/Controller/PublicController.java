package net.engineeringdigest.journalApp.Controller;


import net.engineeringdigest.journalApp.Entity.JournalEntry;
import net.engineeringdigest.journalApp.Entity.Users;
import net.engineeringdigest.journalApp.Services.JournalServices;
import net.engineeringdigest.journalApp.Services.UserServices;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("Public")
public class PublicController {

    @Autowired
    private UserServices services;

    @PostMapping()
    public ResponseEntity<?> saveEntry(@RequestBody Users user) {
        Users usersEntry = services.saveEntry(user);
        if (usersEntry != null) {
            return new ResponseEntity<>(usersEntry, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping()
    public ResponseEntity<?> getEntry() {

        List<Users> list = services.getEntry();
        if (list != null) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
