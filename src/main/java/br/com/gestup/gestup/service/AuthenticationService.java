package br.com.gestup.gestup.service;

import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.gestup.gestup.dto.UserDTO;
import br.com.gestup.gestup.dto.request.CreateUserRequest;
import br.com.gestup.gestup.dto.request.LoginRequest;
import br.com.gestup.gestup.mapper.IMapper;
import br.com.gestup.gestup.model.Role;
import br.com.gestup.gestup.model.User;
import br.com.gestup.gestup.repository.RoleRepository;
import br.com.gestup.gestup.repository.UserRepository;
import br.com.gestup.gestup.security.UserDetailsImpl;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final DaoAuthenticationProvider daoAuthenticationProvider;

    public AuthenticationService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            JwtService jwtService,
            PasswordEncoder passwordEncoder,
            DaoAuthenticationProvider daoAuthenticationProvider,
            IMapper<User, UserDTO> userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.daoAuthenticationProvider = daoAuthenticationProvider;
    }

    public String register(CreateUserRequest request) {
        User user = this.createNewSellerUser(request);
        UserDetails userDetails = new UserDetailsImpl(this.userRepository.save(user));
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());
        return jwtService.generateToken(authentication, user.getId());
    }

    public String login(LoginRequest request) {
        User user = new User(null, request.email(), request.password());
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(
                user.getEmail(),
                user.getPassword());
        Authentication authentication = daoAuthenticationProvider.authenticate(token);
        return jwtService.generateToken(authentication, ((UserDetailsImpl) authentication.getPrincipal()).getId());
    }

    private User createNewSellerUser(CreateUserRequest request) throws UsernameNotFoundException {
        Role sellerRole = this.roleRepository.findByName("seller")
                .orElseThrow(() -> new UsernameNotFoundException("Role not found."));
        List<Role> roles = List.of(sellerRole);
        User user = new User(
                request.username(),
                request.email(),
                passwordEncoder.encode(request.password()),
                roles);
        return user;
    }
}