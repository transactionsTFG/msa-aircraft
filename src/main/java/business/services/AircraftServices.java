package business.services;

import java.util.List;

import business.dto.AircraftCapacityDTO;

public interface AircraftServices {
    boolean validateCapacity(List<AircraftCapacityDTO> listIdAircraft);
}
