package business.dto;

import lombok.Data;

@Data
public class AircraftCapacityDTO {
    private Long idAircraft;
    private int totalSeatsOccupied;
}
