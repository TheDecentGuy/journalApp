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

    private UserServices userServices;
    @Autowired
    public void setUserServices(UserServices userServices){
        this.userServices = userServices;
    }

    @GetMapping()
    public ResponseEntity<List<Users>> getEntry() {

        List<Users> list = userServices.getEntry();
        if (list != null) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @PostMapping()
    public ResponseEntity<Users> saveEntry(@RequestBody Users user) {
        Users usersEntry = userServices.saveNewUser(user);
        if (usersEntry != null) {
            return new ResponseEntity<>(usersEntry, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("GetByID/{MyID}")
    public ResponseEntity<Optional<Users>> getByID(@PathVariable ObjectId MyID) {
        Optional<Users> userEntry = userServices.getEntryID(MyID);
        if (userEntry.isPresent()) {
            return new ResponseEntity<>(userEntry, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity<Users> updateByID(@RequestBody Users newUser) {
        Users users = userServices.updateData(newUser);
        if (users != null) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
