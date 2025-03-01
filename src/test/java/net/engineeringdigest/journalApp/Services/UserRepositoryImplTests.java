package net.engineeringdigest.journalApp.Services;

import net.engineeringdigest.journalApp.Entity.Users;
import net.engineeringdigest.journalApp.Repository.UserRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class UserRepositoryImplTests {
    @Autowired
    private UserRepositoryImpl userRepository;

    @Test
    void userRepositoryTest() {
        List<Users> list = userRepository.getUserForSA();
        System.out.println(list);
        Assertions.assertNotNull(list);
    }
}
