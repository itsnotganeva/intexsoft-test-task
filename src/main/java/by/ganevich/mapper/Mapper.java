package by.ganevich.mapper;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class Mapper<T, W> implements IMapper<T, W> {

    private final ModelMapper mapper;

    @Override
    @SuppressWarnings("unchecked")
    public W toEntity(T dto, Class<?> entity) {
        return Objects.isNull(dto) ? null : (W) mapper.map(dto, entity);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T toDto(W entity, Class<?> dto) {
        return Objects.isNull(entity) ? null : (T) mapper.map(entity, dto);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> listToDto(List<W> listOfEntities, Class<?> dto) {
        return listOfEntities.stream()
                .map((entity) -> (T) mapper.map(entity, dto))
                .collect(Collectors.toList());
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<W> listToEntities(List<T> listOfDto, Class<?> entity) {
        return listOfDto.stream()
                .map((dto) -> (W) mapper.map(dto, entity))
                .collect(Collectors.toList());
    }

    @Override
    @SuppressWarnings("unchecked")
    public T optionalToDto (Optional<W> optionalOfEntity, Class<?> dto) {
        return (T) mapper.map(optionalOfEntity.get(), dto);
    }
}
