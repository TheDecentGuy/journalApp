package net.engineeringdigest.journalApp.Services;

import net.engineeringdigest.journalApp.Entity.Users;
import net.engineeringdigest.journalApp.Repository.UserEntryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserEntryRepo userEntryRepo;

    @Autowired
    public void setUserEntryRepo(UserEntryRepo userEntryRepo) {
        this.userEntryRepo = userEntryRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userEntryRepo.findByusername(username);
        if (users != null) {
            return User.builder()
                    .username(users.getUsername())
                    .password(users.getPassword())
                    .roles(users.getRoles().toArray(new String[0])).build();
        }
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
