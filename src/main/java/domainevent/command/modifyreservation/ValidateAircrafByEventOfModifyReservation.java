package domainevent.command.modifyreservation;


import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;

import business.dto.AircraftCapacityDTO;
import business.qualifier.modify.ValidateCapacityAircrafEventModifyReservationftQualifier;
import domainevent.command.handler.BaseHandler;
import domainevent.command.handler.CommandPublisher;
import msa.commons.event.EventData;
import msa.commons.event.EventId;
import msa.commons.microservices.reservationairline.updatereservation.command.UpdateReservationCommand;

@Stateless
@ValidateCapacityAircrafEventModifyReservationftQualifier
@Local(CommandPublisher.class)
public class ValidateAircrafByEventOfModifyReservation extends BaseHandler{

    @Override
    public void commandPublisher(String json) {
        EventData eventData = EventData.fromJson(json, UpdateReservationCommand.class);
        UpdateReservationCommand u = (UpdateReservationCommand) eventData.getData();
        List<AircraftCapacityDTO> aircraftIds = u.getFlightInstanceInfo().stream().map(info -> {
            AircraftCapacityDTO a = new AircraftCapacityDTO();
            a.setIdAircraft(info.getIdAircraft());
            a.setTotalSeatsOccupied(info.getTotalOccupiedSeats());
            return a;
        }).toList();
        boolean isValid = this.aircraftServices.validateCapacity(aircraftIds);
        if (isValid) 
            this.jmsEventPublisher.publish(EventId.FLIGHT_UPDATE_FLIGHT_BY_AIRLINE_MODIFY_RESERVATION_COMMIT_SAGA, eventData);
        else 
            this.jmsEventPublisher.publish(EventId.RESERVATION_AIRLINE_MODIFY_RESERVATION_ROLLBACK_SAGA, eventData);
    }
    
}
