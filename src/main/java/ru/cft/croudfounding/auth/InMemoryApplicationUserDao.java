package ru.cft.croudfounding.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static ru.cft.croudfounding.security.ApplicationUserRole.*;

@Repository("inMemory")
public class InMemoryApplicationUserDao implements ApplicationUserDao {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public InMemoryApplicationUserDao(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return getApplicationUsers()
                .stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }

    private List<ApplicationUser> getApplicationUsers() {
        return List.of(
                new ApplicationUser(
                        "anna",
                        passwordEncoder.encode("annapass"),
                        STUDENT.getGrantedAuthorities()),
                new ApplicationUser(
                        "linda",
                        passwordEncoder.encode("lindapass"),
                        ADMIN.getGrantedAuthorities()),
                new ApplicationUser(
                        "tom",
                        passwordEncoder.encode("tompass"),
                        ADMINTRAINEE.getGrantedAuthorities())
        );
    }

}
