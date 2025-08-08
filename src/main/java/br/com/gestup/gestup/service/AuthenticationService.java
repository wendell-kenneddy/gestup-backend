package br.com.gestup.gestup.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.gestup.gestup.dto.UserDTO;
import br.com.gestup.gestup.dto.request.CreateUserRequest;
import br.com.gestup.gestup.dto.request.LoginRequest;
import br.com.gestup.gestup.mapper.IMapper;
import br.com.gestup.gestup.model.User;
import br.com.gestup.gestup.repository.UserRepository;
import br.com.gestup.gestup.security.UserDetailsImpl;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final DaoAuthenticationProvider daoAuthenticationProvider;

    public AuthenticationService(
            UserRepository userRepository,
            JwtService jwtService,
            PasswordEncoder passwordEncoder,
            DaoAuthenticationProvider daoAuthenticationProvider,
            IMapper<User, UserDTO> userMapper) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.daoAuthenticationProvider = daoAuthenticationProvider;
    }

    public String register(CreateUserRequest request) {
        User user = userRepository.save(new User(
                request.username(),
                request.email(),
                passwordEncoder.encode(request.password())));
        UserDetails userDetails = new UserDetailsImpl(user);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());
        return jwtService.generateToken(token, user.getId());
    }

    public String login(LoginRequest request) {
        User user = new User(null, request.email(), request.password());
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(
                user.getEmail(),
                user.getPassword());
        Authentication authentication = daoAuthenticationProvider.authenticate(token);
        return jwtService.generateToken(authentication, ((UserDetailsImpl) authentication.getPrincipal()).getId());
    }
}