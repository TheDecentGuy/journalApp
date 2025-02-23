package net.engineeringdigest.journalApp.Controller;


import net.engineeringdigest.journalApp.Entity.Users;
import net.engineeringdigest.journalApp.Services.UserServices;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("Users")
public class UserEntryController {

    @Autowired
    private UserServices services;

    @GetMapping()
    public ResponseEntity<?> getEntry() {

        List<Users> list = services.getEntry();
        if (list != null) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<?> saveEntry(@RequestBody Users user) {
        Users usersEntry = services.saveNewUser(user);
        if (usersEntry != null) {
            return new ResponseEntity<>(usersEntry, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("GetByID/{MyID}")
    public ResponseEntity<?> GetByID(@PathVariable ObjectId MyID) {
        Optional<Users> userEntry = services.getEntryID(MyID);
        if (userEntry.isPresent()) {
            return new ResponseEntity<>(userEntry, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateByID(@RequestBody Users newUser) {
        Users UserEntry = services.UpdateData(newUser);
        if (UserEntry != null) {
            return new ResponseEntity<>(UserEntry, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
