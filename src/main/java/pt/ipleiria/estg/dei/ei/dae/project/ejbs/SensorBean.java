package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.SensorDataDTO;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.project.entities.SensorValueHistory;
import pt.ipleiria.estg.dei.ei.dae.project.entities.Volume;
import pt.ipleiria.estg.dei.ei.dae.project.entities.SensorsType;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.project.exceptions.MyEntityNotFoundException;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Stateless
public class SensorBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(long code, long sensorTypeCode, String value, Date timestamp, long volumeCode)
            throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {

        if (entityManager.find(Sensor.class, code) != null) {
            throw new MyEntityExistsException("Sensor with code " + code + " already exists");
        }

        Volume volume1 = entityManager.find(Volume.class, volumeCode);
        if (volume1 == null) {
            throw new MyEntityNotFoundException("Volume with code " + code + " not found");
        }
        var sensorType = entityManager.find(SensorsType.class, sensorTypeCode);
        if (sensorType == null) {
            throw new MyEntityNotFoundException("SensorType with code " + code + " not found");
        }

        try {
            var sensor = new Sensor(code, sensorType, value, timestamp, volume1);

            entityManager.persist(sensor);
            updateSensorValue(sensor);
        }catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
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

    public void update(long code, SensorsType type, String value, Date timestamp, long volumeCode) throws MyConstraintViolationException {
        Sensor sensor = entityManager.find(Sensor.class, code);
        if (sensor == null || !entityManager.contains(sensor)) {
            return;
        }
        try {
            sensor.setType(type);
            sensor.setValue(value);
            sensor.setVolume(entityManager.find(Volume.class, volumeCode));
            sensor.setTimestamp(timestamp);
            entityManager.merge(sensor);
            updateSensorValue(sensor);
        }catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public void updateSensorValue(Sensor sensor) {
        SensorValueHistory history = new SensorValueHistory(
                sensor,
                sensor.getValue(),
                sensor.getTimestamp()
        );
        entityManager.persist(history);
    }

    public List<SensorValueHistory> getSensorHistory(long sensorCode) {
        return entityManager
                .createNamedQuery("getSensorHistory", SensorValueHistory.class)
                .setParameter("sensorCode", sensorCode)
                .getResultList();
    }



    public void createNewValue(List<SensorDataDTO> sensorDataNew) throws MyEntityNotFoundException {
        for (SensorDataDTO sensorData : sensorDataNew) {
            Sensor sensor = entityManager.find(Sensor.class, sensorData.getCode());
            if (sensor == null) {
                throw new MyEntityNotFoundException("Sensor not found");
            }

            // Verify the value based on the sensor type
            if (!isValidSensorValue(sensor.getType().getName(), sensorData.getValue())) {
                throw new IllegalArgumentException("Invalid value for sensor type: " + sensor.getType());
            }

            // Update sensor with the verified value
            sensor.setValue(sensorData.getValue());
            sensor.setTimestamp(Date.from(Instant.now()));
            entityManager.persist(sensor);
            updateSensorValue(sensor);
        }
    }


    private boolean isValidSensorValue(String typename, String value) {
        if (value == null || value.trim().isEmpty()) {
            return false; // Null or empty values are not valid
        }

        try {
            switch (typename) {
                case "Temperature Sensor":
                    // Expecting a numeric value for temperature
                    double temp = Double.parseDouble(value);
                    return temp >= -50 && temp <= 150; // Example range in °C

                case "Atmospheric Pressure Sensor":
                    // Expecting a numeric value for pressure
                    double pressure = Double.parseDouble(value);
                    return pressure >= 300 && pressure <= 1100; // Example range in hPa

                case "Accelerometer":
                    // Expecting a numeric value for accelerometer
                    double accel = Double.parseDouble(value);
                    return accel >= 0 && accel < 99999; // Example range in g

                case "Global Positioning Sensor":
                    // Example: Could validate a coordinate string like "latitude,longitude"
                    return value.matches("-?\\d+(\\.\\d+)?,-?\\d+(\\.\\d+)?"); // e.g., "40.7128,-74.0060"

                case "Humidity Sensor":
                    // Expecting a numeric value for humidity
                    double humidity = Double.parseDouble(value);
                    return humidity >= 0 && humidity <= 100; // Example range in %

                case "Light Sensor":
                    // Expecting a numeric value for light intensity
                    double light = Double.parseDouble(value);
                    return light >= 0 && light <= 100000; // Example range in lux

                case "Infrared Sensor":
                    // Expecting a numeric value for infrared
                    double infrared = Double.parseDouble(value);
                    return infrared >= 0 && infrared <= 10000; // Example range in arbitrary units

                case "Ultrasonic Sensor":
                    // Expecting a numeric value for distance
                    double ultrasonic = Double.parseDouble(value);
                    return ultrasonic >= 0 && ultrasonic <= 400; // Example range in cm

                default:
                    throw new IllegalArgumentException("Unknown sensor type: " + typename);
            }
        } catch (NumberFormatException e) {
                // Handle cases where the value cannot be parsed to a number
                return false;
        }
    }



    public void createNewSingleValue(SensorDataDTO sensorData) throws MyEntityNotFoundException {
        Sensor sensor = entityManager.find(Sensor.class, sensorData.getCode());
        if(sensor == null){
            throw new MyEntityNotFoundException("Sensor not found");
        }
        // Verify the value based on the sensor type
        if (!isValidSensorValue(sensor.getType().getName(), sensorData.getValue())) {
            throw new IllegalArgumentException("Invalid value for sensor type: " + sensor.getType());
        }

        sensor.setValue(sensorData.getValue());
        sensor.setTimestamp(Date.from(Instant.now()));
        updateSensorValue(sensor);
        entityManager.persist(sensor);
    }
}
