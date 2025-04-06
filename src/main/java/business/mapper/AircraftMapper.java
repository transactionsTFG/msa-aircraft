package business.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import business.country.Aircraft;
import business.country.AircraftDTO;

@Mapper
public interface AircraftMapper {
    AircraftMapper INSTANCE = Mappers.getMapper(AircraftMapper.class);
    AircraftDTO entityToDto(Aircraft aircraft);
}
