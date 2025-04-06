package business.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import business.aircraft.Aircraft;
import business.dto.AircraftCapacityDTO;

@Stateless
public class AircraftServicesImpl implements AircraftServices {
    private EntityManager entityManager;
    
    @Override
    public boolean validateCapacity(List<AircraftCapacityDTO> listIdAircraft) {
        for (AircraftCapacityDTO air : listIdAircraft) {
            Aircraft aircraft = entityManager.find(Aircraft.class, air.getIdAircraft(), LockModeType.OPTIMISTIC);
            if (aircraft == null || !aircraft.isActive())
                return false;
            if (air.getTotalSeatsOccupied() > aircraft.getCapacity())
                return false;
        }
        return true;
    }

    @Inject public void setEntityManager(EntityManager entityManager) { this.entityManager = entityManager;}
}
