package net.engineeringdigest.journalApp.Controller;


import net.engineeringdigest.journalApp.Entity.Users;
import net.engineeringdigest.journalApp.Services.UserServices;
import net.engineeringdigest.journalApp.Services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Public")
public class PublicController {


    private UserServices userServices;
    @Autowired
    public void userServices(UserServices userServices){
        this.userServices = userServices;
    }

    private WeatherService weatherService;
    @Autowired
    public void weatherService(WeatherService weatherService){
        this.weatherService=weatherService;
    }

    @GetMapping("{city}")
    public ResponseEntity<String> getEntry(@PathVariable String city ) {
        return new ResponseEntity<>("Hii Vrushabh current temperature of "+city+" is "+ weatherService.getWeather(city).getCurrent().getTempC(),HttpStatus.OK);
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

}
