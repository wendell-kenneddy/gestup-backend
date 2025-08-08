package br.com.gestup.gestup.mapper;

import org.springframework.stereotype.Component;

import br.com.gestup.gestup.dto.UserDTO;
import br.com.gestup.gestup.model.User;

@Component
public class UserMapperImpl implements IMapper<User, UserDTO> {
    public UserDTO entityToDTO(User entity) {
        return new UserDTO(entity.getUsername(), entity.getEmail());
    }

    @Override
    public User DTOToEntity(UserDTO DTO) {
        return new User(DTO.username(), DTO.email(), null);
    }
}
