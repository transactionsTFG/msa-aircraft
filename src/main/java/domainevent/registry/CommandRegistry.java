package domainevent.registry;

import java.util.EnumMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import business.qualifier.createreservation.ValidateCapacityAircraEventCreateReservationftQualifier;
import business.qualifier.modify.ValidateCapacityAircrafEventModifyReservationftQualifier;
import domainevent.command.handler.CommandPublisher;

import msa.commons.event.EventId;


@Singleton
@Startup
public class CommandRegistry {
    private Map<EventId, CommandPublisher> handlers = new EnumMap<>(EventId.class);
    private CommandPublisher validateCapacityAircraEventCreateReservation;
    private CommandPublisher validateCapacityAircraEventModifyReservation;

    @PostConstruct
    public void init(){
        this.handlers.put(EventId.AIRCRAFT_VALIDATE_CAPACITY_RESERVATION_AIRLINE_CREATE_RESERVATION, validateCapacityAircraEventCreateReservation);
        this.handlers.put(EventId.AIRCRAFT_VALIDATE_CAPACITY_RESERVATION_AIRLINE_MODIFY_RESERVATION , validateCapacityAircraEventModifyReservation);
    }

    public CommandPublisher getHandler(EventId eventId) {
        return this.handlers.get(eventId);
    }

    @Inject
    public void setGetAircraftByIdHandler(@ValidateCapacityAircraEventCreateReservationftQualifier CommandPublisher validateCapacityAircraEventCreateReservation) {
        this.validateCapacityAircraEventCreateReservation = validateCapacityAircraEventCreateReservation;
    }

    @Inject
    public void setValidateCapacityAircraEventModifyReservation(@ValidateCapacityAircrafEventModifyReservationftQualifier CommandPublisher validateCapacityAircraEventModifyReservation) {
        this.validateCapacityAircraEventModifyReservation = validateCapacityAircraEventModifyReservation;
    }
}
