package by.ganevich.mapper.interfaces;
import by.ganevich.dto.ClientDto;
import by.ganevich.entity.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientDto toDto(Client client);
    Client toEntity(ClientDto clientDto);
}
