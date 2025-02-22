package net.engineeringdigest.journalApp.Services;


import net.engineeringdigest.journalApp.Entity.JournalEntry;
import net.engineeringdigest.journalApp.Entity.Users;
import net.engineeringdigest.journalApp.Repository.JournalEntryRepo;
import net.engineeringdigest.journalApp.Repository.UserEntryRepo;
import org.apache.catalina.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.beans.Encoder;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserServices {

    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();
    @Autowired
    private UserEntryRepo userEntryRepo;


    public Users saveEntry(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("User"));
        return userEntryRepo.save(user);
    }

    public List<Users> getEntry() {
        return userEntryRepo.findAll();
    }

    public Optional<Users> getEntryID(ObjectId id) {
        return userEntryRepo.findById(id);
    }

    //
    public Users UpdateData( Users newUser) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users userIndb = userEntryRepo.findByusername(auth.getName());
        userIndb.setUsername(newUser.getUsername());
        userIndb.setPassword(encoder.encode(newUser.getPassword()));
        return userEntryRepo.save(userIndb);
    }
}
