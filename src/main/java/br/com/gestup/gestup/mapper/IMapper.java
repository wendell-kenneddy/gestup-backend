package br.com.gestup.gestup.mapper;

public interface IMapper<T, U> {
    public U entityToDTO(T entity);

    public T DTOToEntity(U DTO);
}
