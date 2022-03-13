package by.ganevich.mapper;

import java.util.List;
import java.util.Optional;

public interface IMapper<T, W> {

    W toEntity(T dto, Class<?> entity);

    T toDto(W entity, Class<?> dto);

    List<T> listToDto(List<W> listOfEntities, Class<?> dto);

    List<W> listToEntities(List<T> listOfDto, Class<?> entity);

    T optionalToDto (Optional<W> optionalOfEntity, Class<?> dto);
}
