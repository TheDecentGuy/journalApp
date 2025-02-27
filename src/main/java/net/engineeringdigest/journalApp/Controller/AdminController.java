package net.engineeringdigest.journalApp.Controller;


import net.engineeringdigest.journalApp.Entity.Users;
import net.engineeringdigest.journalApp.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("Admin")
public class AdminController {

    private UserServices userServices;
    @Autowired
    public void setUserServices(UserServices userServices){
        this.userServices= userServices;
    }

    @GetMapping("all-users")
    public ResponseEntity<List<Users>> getEntry() {

        List<Users> list = userServices.getEntry();
        if (list != null) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
