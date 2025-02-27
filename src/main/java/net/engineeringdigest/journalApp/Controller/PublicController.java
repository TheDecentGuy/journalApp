package net.engineeringdigest.journalApp.Controller;


import net.engineeringdigest.journalApp.Entity.Users;
import net.engineeringdigest.journalApp.Services.UserServices;
import net.engineeringdigest.journalApp.Services.WeatherService;
import net.engineeringdigest.journalApp.apiResponse.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("Public")
public class PublicController {

    @Autowired
    private UserServices services;
    @Autowired
    private WeatherService weatherService;

    @GetMapping("{city}")
    public ResponseEntity<?> getEntry(@PathVariable String city ) {
        WeatherResponse weatherResponse =  weatherService.getWeather(city);
        return new ResponseEntity<>("Hii Vrushabh current temperature of "+city+" is "+ weatherResponse.getCurrent().getTempC(),HttpStatus.OK);
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

}
