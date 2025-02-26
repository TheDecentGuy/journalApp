package net.engineeringdigest.journalApp.Services;

import net.engineeringdigest.journalApp.Entity.Users;
import net.engineeringdigest.journalApp.Repository.UserEntryRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

import static org.mockito.Mockito.*;

public class UserDetailsServiceImplTest {

    @InjectMocks
    public UserDetailsServiceImpl userDetailsService;

    @Mock
    public UserEntryRepo userEntryRepo;

    @BeforeEach
    void setUP() {
        // we have used Mock UserEntryRepo and we are not using SpringBoot context to initialise. Thats why
        // we are using following way to initialise
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void loadUserByUsernameTest() {
        Users testuser = Users.builder()
                .username("testUser")
                .password("testPassword")
                .roles(Collections.singletonList("USER"))
                .build();
        when(userEntryRepo.findByusername(ArgumentMatchers.anyString())).thenReturn(testuser);
        UserDetails user = userDetailsService.loadUserByUsername("Ram");
        Assertions.assertNotNull(user);
    }

    @Test
    public void WheenUserIsNull() {
        when(userEntryRepo.findByusername(ArgumentMatchers.anyString())).thenReturn(null);
        Assertions.assertThrows(UsernameNotFoundException.class,()->{userDetailsService.loadUserByUsername("Test");});
    }
}
