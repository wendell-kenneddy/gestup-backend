package br.com.gestup.gestup.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.gestup.gestup.dto.UserDTO;
import br.com.gestup.gestup.mapper.IMapper;
import br.com.gestup.gestup.model.User;
import br.com.gestup.gestup.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final IMapper<User, UserDTO> userMapper;

    public UserService(UserRepository userRepository, IMapper<User, UserDTO> userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDTO getProfile(Authentication authentication) {
        User user = this.userRepository.findById(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("Invalid credentials."));
        return this.userMapper.entityToDTO(user);
    }
}
