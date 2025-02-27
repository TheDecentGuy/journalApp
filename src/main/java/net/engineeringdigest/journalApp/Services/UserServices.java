package net.engineeringdigest.journalApp.Services;


import net.engineeringdigest.journalApp.Entity.Users;
import net.engineeringdigest.journalApp.Repository.UserEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServices {

    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();
    private UserEntryRepo userEntryRepo;
    @Autowired
    public void setUserEntryRepo(UserEntryRepo userEntryRepo){
        this.userEntryRepo = userEntryRepo;
    }


    public Users saveNewUser(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles(Collections.singletonList("User"));
        return userEntryRepo.save(user);
    }

    public Users saveUser(Users user) {
        user.setPassword(user.getPassword());
        user.setRoles(Collections.singletonList("User"));
        return userEntryRepo.save(user);
    }

    public List<Users> getEntry() {
        return userEntryRepo.findAll();
    }

    public Optional<Users> getEntryID(ObjectId id) {
        return userEntryRepo.findById(id);
    }

    //
    public Users updateData(Users newUser) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users userIndb = userEntryRepo.findByusername(auth.getName());
        userIndb.setUsername(newUser.getUsername());
        userIndb.setPassword(encoder.encode(newUser.getPassword()));
        return userEntryRepo.save(userIndb);
    }
}
