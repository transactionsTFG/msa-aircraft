package business.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import business.aircraft.Aircraft;
import business.aircraft.AircraftDTO;

@Mapper
public interface AircraftMapper {
    AircraftMapper INSTANCE = Mappers.getMapper(AircraftMapper.class);
    AircraftDTO entityToDto(Aircraft aircraft);
}
