package business.services;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import business.country.Aircraft;
import business.country.AircraftDTO;
import business.mapper.AircraftMapper;

@Stateless
public class AircraftServicesImpl implements AircraftServices {
    private EntityManager entityManager;
    
    @Override
    public AircraftDTO getAircraftById(long id) {
        Aircraft aircraft = entityManager.find(Aircraft.class, id, LockModeType.OPTIMISTIC);
        if (aircraft == null) 
            return null;
        return AircraftMapper.INSTANCE.entityToDto(aircraft);
    }

    @Inject public void setEntityManager(EntityManager entityManager) { this.entityManager = entityManager;}
}
