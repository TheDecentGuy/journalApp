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
@RequestMapping("Admin")
public class AdminController {

    @Autowired
    private UserServices services;

    @GetMapping("all-users")
    public ResponseEntity<?> getEntry() {

        List<Users> list = services.getEntry();
        if (list != null) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
