package by.ganevich.mapper.interfaces;

import by.ganevich.dto.ClientDto;
import by.ganevich.entity.Client;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = ClientMapper.class)
public interface ClientListMapper {
    List<Client> toEntityList(List<ClientDto> clientsDto);
    List<ClientDto> toDtoList(List<Client> clients);
}
