package domainevent.command.handler;

import javax.ejb.EJB;
import javax.inject.Inject;

import com.google.gson.Gson;

import business.services.AircraftServices;
import domainevent.publisher.IJMSCommandPublisher;

public abstract class BaseHandler implements CommandPublisher {
    protected AircraftServices aircraftServices;
    protected IJMSCommandPublisher jmsEventPublisher;
    protected Gson gson;
    @EJB
    public void setTypeUserServices(AircraftServices aircraftServices) {
        this.aircraftServices = aircraftServices;
    }

    @EJB
    public void setJmsEventPublisher(IJMSCommandPublisher jmsEventPublisher) {
        this.jmsEventPublisher = jmsEventPublisher;
    }

    @Inject
    public void setGson(Gson gson) {
        this.gson = gson;
    }
}
