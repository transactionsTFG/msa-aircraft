package domainevent.command.createreservation;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;

import business.dto.AircraftCapacityDTO;
import domainevent.command.handler.BaseHandler;
import domainevent.command.handler.CommandPublisher;
import msa.commons.event.EventData;
import msa.commons.event.EventId;
import msa.commons.microservices.aircraft.qualifier.ValidateCapacityAircraEventCreateReservationftQualifier;
import msa.commons.microservices.reservationairline.commandevent.CreateReservationCommand;

@Stateless
@ValidateCapacityAircraEventCreateReservationftQualifier
@Local(CommandPublisher.class)
public class ValidateAircrafByEventOfCreateReservation extends BaseHandler {

    @Override
    public void commandPublisher(String json) {
        EventData eventData = EventData.fromJson(json, CreateReservationCommand.class);
        CreateReservationCommand c = (CreateReservationCommand) eventData.getData();
        List<AircraftCapacityDTO> aircraftIds = c.getFlightInstanceInfo().stream().map(info -> {
            AircraftCapacityDTO a = new AircraftCapacityDTO();
            a.setIdAircraft(info.getIdAircraft());
            a.setTotalSeatsOccupied(info.getTotalOccupiedSeats());
            return a;
        }).toList();
        boolean isValid = this.aircraftServices.validateCapacity(aircraftIds);
        if (isValid) 
            this.jmsEventPublisher.publish(EventId.AIRCRAFT_PROPAGATION_SAVE_RESERVATION_AIRLINE_CREATE_RESERVATION_COMMIT_SAGA, eventData);
        else 
            this.jmsEventPublisher.publish(EventId.RESERVATION_AIRLINE_CREATE_RESERVATION_ROLLBACK_SAGA, eventData);
    }
    
}
