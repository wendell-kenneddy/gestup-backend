package br.com.gestup.gestup.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.gestup.gestup.model.Role;
import br.com.gestup.gestup.model.User;
import br.com.gestup.gestup.repository.RoleRepository;
import br.com.gestup.gestup.repository.UserRepository;

@Component
public class Seed implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    @Value("${seed.enable}")
    private boolean shouldRun;
    @Value("${admin.username}")
    private String adminUsername;
    @Value("${admin.email}")
    private String adminEmail;
    @Value("${admin.password}")
    private String adminPassword;

    public Seed(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (this.shouldRun) {
            Role adminRole = new Role("admin");
            Role sellerRole = new Role("seller");
            User admin = new User(adminUsername, adminEmail, passwordEncoder.encode(adminPassword), List.of(adminRole));
            roleRepository.saveAll(List.of(adminRole, sellerRole));
            userRepository.save(admin);
        }
    }

}
