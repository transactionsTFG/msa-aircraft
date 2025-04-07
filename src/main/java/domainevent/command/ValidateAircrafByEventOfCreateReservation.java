package domainevent.command;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;

import business.dto.AircraftCapacityDTO;
import domainevent.command.handler.BaseHandler;
import domainevent.command.handler.EventHandler;
import msa.commons.event.EventId;
import msa.commons.microservices.aircraft.qualifier.ValidateCapacityAircraEventCreateReservationftQualifier;
import msa.commons.microservices.reservationairline.commandevent.CreateReservationCommand;

@Stateless
@ValidateCapacityAircraEventCreateReservationftQualifier
@Local(EventHandler.class)
public class ValidateAircrafByEventOfCreateReservation extends BaseHandler {

    @Override
    public void handleCommand(Object data) {
        CreateReservationCommand c = (CreateReservationCommand) data;
        List<AircraftCapacityDTO> aircraftIds = c.getFlightInstanceInfo().stream().map(info -> {
            AircraftCapacityDTO a = new AircraftCapacityDTO();
            a.setIdAircraft(info.getIdAircraft());
            a.setTotalSeatsOccupied(info.getNumberSeats());
            return a;
        }).toList();
        boolean isValid = this.aircraftServices.validateCapacity(aircraftIds);
        if (isValid) 
            this.jmsEventPublisher.publish(EventId.AIRCRAFT_PROPAGATION_SAVE_RESERVATION_AIRLINE_CREATE_RESERVATION_COMMIT_SAGA, c);
        else 
            this.jmsEventPublisher.publish(EventId.RESERVATION_AIRLINE_CREATE_RESERVATION_ROLLBACK_SAGA, c);
    }
    
}
