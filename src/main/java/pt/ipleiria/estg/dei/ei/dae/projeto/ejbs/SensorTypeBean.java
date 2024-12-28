package pt.ipleiria.estg.dei.ei.dae.projeto.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.SensorsType;
import pt.ipleiria.estg.dei.ei.dae.projeto.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projeto.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projeto.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class SensorTypeBean {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(long id, String name) throws MyEntityExistsException, MyConstraintViolationException {
        if (entityManager.find(SensorsType.class, id) != null) {
            throw new MyEntityExistsException("Sensor Type with ID '" + id + "' already exists!");
        }

        try {
            var sensorType = new SensorsType(id, name);
            entityManager.persist(sensorType);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public SensorsType find(long id) throws MyEntityNotFoundException {
        SensorsType sensorType = entityManager.find(SensorsType.class, id);
        if (sensorType == null) {
            throw new MyEntityNotFoundException("Sensor Type with ID '" + id + "' not found!");
        }
        return sensorType;
    }

    public List<SensorsType> findAll() {
        return entityManager.createNamedQuery("getAllSensorTypes", SensorsType.class).getResultList();
    }

    public void update(long id, String name) throws MyEntityNotFoundException,MyConstraintViolationException {
        SensorsType sensorType = find(id);
        if (sensorType == null) {
            throw new MyEntityNotFoundException("Sensor Type with ID '" + id + "' not found!");
        }
        try {
            sensorType.setName(name);
            entityManager.merge(sensorType);
        }catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public void delete(long id) throws MyEntityNotFoundException {
        SensorsType sensorType = find(id);
        if (sensorType == null) {
            throw new MyEntityNotFoundException("Sensor Type with ID '" + id + "' not found!");
        }
        entityManager.remove(sensorType);
    }
}
