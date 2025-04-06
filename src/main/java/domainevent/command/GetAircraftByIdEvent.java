package domainevent.command;

import javax.ejb.Local;
import javax.ejb.Stateless;

import business.aircraft.AircraftDTO;
import domainevent.command.handler.BaseHandler;
import domainevent.command.handler.EventHandler;
import msa.commons.event.EventId;
import msa.commons.microservices.aircraft.qualifier.GetAircraftByIdQualifier;
import msa.commons.parser.NumberParser;

@Stateless
@GetAircraftByIdQualifier
@Local(EventHandler.class)
public class GetAircraftByIdEvent extends BaseHandler {

    @Override
    public void handleCommand(Object data) {
        long idAircraft = NumberParser.toLong(data);
        AircraftDTO a = this.aircraftServices.getAircraftById(idAircraft);
        this.jmsEventPublisher.publish(EventId.GET_AIRCRAFT_BY_ID, a);
    }
    
}
