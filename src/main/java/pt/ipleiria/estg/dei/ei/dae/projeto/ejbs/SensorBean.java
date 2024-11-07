package pt.ipleiria.estg.dei.ei.dae.projeto.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.Volume;
import pt.ipleiria.estg.dei.ei.dae.projeto.entities.enums.SensorsType;
import pt.ipleiria.estg.dei.ei.dae.projeto.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projeto.exceptions.MyEntityNotFoundException;

import java.util.Date;
import java.util.List;

@Stateless
public class SensorBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(long code, SensorsType type, String value, Date timestamp, long volumeCode)
            throws MyEntityExistsException, MyEntityNotFoundException {

        if (entityManager.find(Sensor.class, code) != null){
            throw new MyEntityNotFoundException("Sensor with code " + code + " not found");
        }

        Volume volume1 = entityManager.find(Volume.class,volumeCode);
        if ( volume1 == null){
            throw new MyEntityNotFoundException("Volume with code " + code + " not found");
        }

        var sensor = new Sensor(code,type,value,timestamp,volume1 );

        entityManager.persist(sensor);
    }

    public List<Sensor> findAll() {
        // remember, maps to: “SELECT s FROM Student s ORDER BY s.name”
        return entityManager.createNamedQuery("getAllSensors", Sensor.class).getResultList();
    }
    public Sensor find(long id) {
        var sensor = entityManager.find(Sensor.class,id);
        if (sensor == null) {
            throw new RuntimeException("Sensor " + id + " not found");
        }
        return sensor;
    }

    public void delete(long sensor) {
        entityManager.remove(entityManager.find(Sensor.class, sensor));
    }

    public void update(long code, SensorsType type, String value, Date timestamp, long volumeCode) {
        Sensor sensor = entityManager.find(Sensor.class, code);
        if (sensor == null || !entityManager.contains(sensor)){
            return;
        }
        sensor.setType(type);
        sensor.setValue(value);
        sensor.setVolume(entityManager.find(Volume.class, volumeCode));
        sensor.setTimestamp(timestamp);
        entityManager.merge(sensor);
    }


}
