package business.aircraft;

import lombok.Data;

@Data
public class AircraftDTO {
    private long id;
    private boolean active;
    private int capacity;
    private String model;
}
