package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import pt.ipleiria.estg.dei.ei.dae.project.dtos.SensorDataDTO;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.OrderState;
import pt.ipleiria.estg.dei.ei.dae.project.entities.enums.VolumeState;
import pt.ipleiria.estg.dei.ei.dae.project.entities.mappings.ProductVolumeMapping;

import java.time.Instant;
import java.util.Date;
import java.util.logging.Logger;

@Singleton
@Startup
public class ConfigBean {

    public static final Logger logger = Logger.getLogger("ejbs.ConfigBean");
    @EJB
    private ClientBean clientBean;

    @EJB
    private ManagerBean managerBean;
    @EJB
    private OrderBean orderBean;
    @EJB
    private SensorBean sensorBean ;
    @EJB
    private ProductBean productBean ;
    @EJB
    private VolumeBean volumeBean ;
    @EJB
    private SensorTypeBean sensorTypeBean ;

    @EJB
    private ProductTypeBean productTypeBean ;

    @EJB
    private PackageTypeBean packageTypeBean ;

    @EJB
    private LogisticBean logisticBean;

    @EJB
    private ProductVolumeMappingBean productVolumeMapping;

    @PostConstruct
    public void populateDB() {
        try {
            // Existing Clients
            clientBean.create("email@email.com", "password", "name");
            clientBean.create("email1@email.com", "um", "um");
            clientBean.create("email2@email.com", "dois", "dois");
            clientBean.create("email3@email.com", "três", "três");
            clientBean.create("email4@email.com", "quatro", "quatro");
            clientBean.create("email5@email.com", "cinco", "cinco");
            clientBean.create("email6@email.com", "seis", "seis");
            clientBean.create("email7@email.com", "sete", "sete");
            clientBean.create("email8@email.com", "oito", "oito");
            clientBean.create("email9@email.com", "nove", "nove");

            // Existing Managers
            managerBean.create("admin1@admin.com", "password1", "admin1");
            managerBean.create("admin2@admin.com", "password2", "admin2");
            managerBean.create("admin3@admin.com", "password3", "admin3");
            managerBean.create("admin4@admin.com", "password4", "admin4");

            // Existing Logistics
            logisticBean.create("logistic1@logistic.com", "password1", "logistic1");
            logisticBean.create("logistic2@logistic.com", "password2", "logistic");
            logisticBean.create("logistic3@logistic.com", "password3", "logistic3");

            // Existing Sensor Types
            sensorTypeBean.create(1, "Temperature Sensor");
            sensorTypeBean.create(2, "Atmospheric Pressure Sensor");
            sensorTypeBean.create(3, "Accelerometer");
            sensorTypeBean.create(4, "Global Positioning Sensor");
            sensorTypeBean.create(5, "Humidity Sensor");


            // Existing Product Types
            for (int i = 1; i <= 5; i++) {
                productTypeBean.create(i);
            }

            // Assign Sensors to Product Types
            productTypeBean.addSensor(1, sensorTypeBean.find(1), 2);
            productTypeBean.addSensor(2, sensorTypeBean.find(2), 5);
            productTypeBean.addSensor(3, sensorTypeBean.find(3), 4);
            productTypeBean.addSensor(4, sensorTypeBean.find(1), 1);

            // Existing Package Types
            packageTypeBean.create(1, "Thermal");
            packageTypeBean.create(2, "Metal Box");
            packageTypeBean.create(3, "Wooden Box");
            packageTypeBean.create(4, "Plastic Bag");
            packageTypeBean.create(5, "Bubble Wrap");
            packageTypeBean.create(6, "Padded Envelope");
            packageTypeBean.create(7, "Glass Container");
            packageTypeBean.create(8, "Cardboard Box");
            packageTypeBean.create(9, "Plastic Container");
            packageTypeBean.create(10, "Foam Box");
            packageTypeBean.create(11, "Aluminum Crate");
            packageTypeBean.create(12, "Steel Drum");


            // Assign Sensors to Package Types
            packageTypeBean.addSensor(1, sensorTypeBean.find(1), 2);
            packageTypeBean.addSensor(2, sensorTypeBean.find(2), 1);
            packageTypeBean.addSensor(3, sensorTypeBean.find(3), 2);
            packageTypeBean.addSensor(4, sensorTypeBean.find(4), 1);
            packageTypeBean.addSensor(5, sensorTypeBean.find(5), 1);
            packageTypeBean.addSensor(6, sensorTypeBean.find(2), 4);
            packageTypeBean.addSensor(7, sensorTypeBean.find(3), 7);
            packageTypeBean.addSensor(8, sensorTypeBean.find(5), 1);
            packageTypeBean.addSensor(9, sensorTypeBean.find(1), 2);
            packageTypeBean.addSensor(10, sensorTypeBean.find(5), 5);
            packageTypeBean.addSensor(11, sensorTypeBean.find(3), 3);
            packageTypeBean.addSensor(12, sensorTypeBean.find(1), 2);

            // Existing Orders
            orderBean.create(1, 1.2f, OrderState.PROCESSED, Date.from(Instant.now()), Date.from(Instant.now()), 1);
            orderBean.create(2, 2.5f, OrderState.DELIVERED, Date.from(Instant.now()), Date.from(Instant.now()), 2);
            orderBean.create(3, 3.0f, OrderState.SHIPPED, Date.from(Instant.now()), Date.from(Instant.now()), 3);
            orderBean.create(4, 4.0f, OrderState.IN_TRANSIT, Date.from(Instant.now()), Date.from(Instant.now()), 4);
            orderBean.create(5, 5.5f, OrderState.PROCESSED, Date.from(Instant.now()), Date.from(Instant.now()), 5);
            orderBean.create(6, 6.0f, OrderState.SHIPPED, Date.from(Instant.now()), Date.from(Instant.now()), 6);
            orderBean.create(7, 7.2f, OrderState.DELIVERED, Date.from(Instant.now()), Date.from(Instant.now()), 7);
            orderBean.create(8, 8.1f, OrderState.PROCESSED, Date.from(Instant.now()), Date.from(Instant.now()), 8);
            orderBean.create(9, 9.3f, OrderState.IN_TRANSIT, Date.from(Instant.now()), Date.from(Instant.now()), 9);
            orderBean.create(10, 10.5f, OrderState.SHIPPED, Date.from(Instant.now()), Date.from(Instant.now()), 10);
            orderBean.create(11, 203.5f, OrderState.SHIPPED, Date.from(Instant.now()), Date.from(Instant.now()), 1);
            orderBean.create(12, 441.5f, OrderState.SHIPPED, Date.from(Instant.now()), Date.from(Instant.now()), 1);

            // Existing Volumes
            for (int i = 1; i <= 5; i++) {
                volumeBean.create(i, VolumeState.PACKED, i, i, Date.from(Instant.now()));
            }

            // Existing Sensors
            sensorBean.create(1, 1, "22.5", Date.from(Instant.now()), 1);  // Temperature Sensor - Temperature value in Celsius
            sensorBean.create(2, 1, "25.5", Date.from(Instant.now()), 1);  // Temperature Sensor - Temperature value in Celsius
            sensorBean.create(21, 1, "20.5", Date.from(Instant.now()), 1);  // Temperature Sensor - Temperature value in Celsius
            sensorBean.create(22, 1, "19.5", Date.from(Instant.now()), 1);  // Temperature Sensor - Temperature value in Celsius

            sensorBean.create(23, 2, "1010.25", Date.from(Instant.now()), 1);  // Atmospheric Pressure Sensor - Pressure in hPa
            sensorBean.create(24, 2, "1007.25", Date.from(Instant.now()), 1);  // Atmospheric Pressure Sensor - Pressure in hPa
            sensorBean.create(25, 2, "1002.25", Date.from(Instant.now()), 1);  // Atmospheric Pressure Sensor - Pressure in hPa
            sensorBean.create(26, 2, "1020.25", Date.from(Instant.now()), 1);  // Atmospheric Pressure Sensor - Pressure in hPa
            sensorBean.create(27, 2, "1023.25", Date.from(Instant.now()), 1);  // Atmospheric Pressure Sensor - Pressure in hPa

            sensorBean.create(28, 2, "1010.25", Date.from(Instant.now()), 1);  // Atmospheric Pressure Sensor - Pressure in hPa
            sensorBean.create(29, 2, "1007.25", Date.from(Instant.now()), 1);  // Atmospheric Pressure Sensor - Pressure in hPa
            sensorBean.create(30, 2, "1002.25", Date.from(Instant.now()), 1);  // Atmospheric Pressure Sensor - Pressure in hPa
            sensorBean.create(31, 2, "1020.25", Date.from(Instant.now()), 1);  // Atmospheric Pressure Sensor - Pressure in hPa
            sensorBean.create(32, 2, "1023.25", Date.from(Instant.now()), 1);  // Atmospheric Pressure Sensor - Pressure in hPa


            sensorBean.create(3, 2, "1013.25", Date.from(Instant.now()), 2);  // Atmospheric Pressure Sensor - Pressure in hPa

            sensorBean.create(33, 3, "0.65", Date.from(Instant.now()), 2);  // Accelerometer - Acceleration value in m/s²
            sensorBean.create(34, 3, "1.05", Date.from(Instant.now()), 2);  // Accelerometer - Acceleration value in m/s²
            sensorBean.create(35, 3, "1.25", Date.from(Instant.now()), 2);  // Accelerometer - Acceleration value in m/s²
            sensorBean.create(36, 3, "0.75", Date.from(Instant.now()), 2);  // Accelerometer - Acceleration value in m/s²

            sensorBean.create(37, 3, "1.65", Date.from(Instant.now()), 2);  // Accelerometer - Acceleration value in m/s²
            sensorBean.create(38, 3, "1.25", Date.from(Instant.now()), 2);  // Accelerometer - Acceleration value in m/s²
            sensorBean.create(39, 3, "1.15", Date.from(Instant.now()), 2);  // Accelerometer - Acceleration value in m/s²
            sensorBean.create(40, 3, "1.05", Date.from(Instant.now()), 2);  // Accelerometer - Acceleration value in m/s²

            sensorBean.create(4, 3, "0.05", Date.from(Instant.now()), 3);  // Accelerometer - Acceleration value in m/s²
            sensorBean.create(5, 3, "0.08", Date.from(Instant.now()), 3);  // Accelerometer - Acceleration value in m/s²
            sensorBean.create(41, 4, "39.908889,-8.821111", Date.from(Instant.now()), 3);  // Global Positioning Sensor - Latitude and Longitude


            sensorBean.create(6, 4, "39.908889,-8.821111", Date.from(Instant.now()), 4);  // Global Positioning Sensor - Latitude and Longitude

            sensorBean.create(7, 5, "55", Date.from(Instant.now()), 5);  // Humidity Sensor - Humidity percentage
            sensorBean.create(42, 2, "1010.25", Date.from(Instant.now()), 5);  // Atmospheric Pressure Sensor - Pressure in hPa
            sensorBean.create(43, 2, "1007.25", Date.from(Instant.now()), 5);  // Atmospheric Pressure Sensor - Pressure in hPa
            sensorBean.create(44, 2, "1002.25", Date.from(Instant.now()), 5);  // Atmospheric Pressure Sensor - Pressure in hPa
            sensorBean.create(45, 2, "1020.25", Date.from(Instant.now()), 5);  // Atmospheric Pressure Sensor - Pressure in hPa
            sensorBean.create(46, 2, "1023.25", Date.from(Instant.now()), 5);  // Atmospheric Pressure Sensor - Pressure in hPa

            sensorBean.create(47, 3, "0.65", Date.from(Instant.now()), 5);  // Accelerometer - Acceleration value in m/s²
            sensorBean.create(48, 3, "1.05", Date.from(Instant.now()), 5);  // Accelerometer - Acceleration value in m/s²
            sensorBean.create(49, 3, "1.25", Date.from(Instant.now()), 5);  // Accelerometer - Acceleration value in m/s²
            sensorBean.create(50, 3, "0.75", Date.from(Instant.now()), 5);  // Accelerometer - Acceleration value in m/s²

            sensorBean.create(51, 3, "1.65", Date.from(Instant.now()), 5);  // Accelerometer - Acceleration value in m/s²
            sensorBean.create(52, 3, "1.25", Date.from(Instant.now()), 5);  // Accelerometer - Acceleration value in m/s²
            sensorBean.create(53, 3, "1.15", Date.from(Instant.now()), 5);  // Accelerometer - Acceleration value in m/s²
            sensorBean.create(54, 3, "1.05", Date.from(Instant.now()), 5);  // Accelerometer - Acceleration value in m/s²


            // Existing Products
            productBean.create(111, "Primeiro", "Volkswagen", 1, "Grande PóPó", 1);
            productBean.create(112, "Segundo", "Ford", 2, "Compacto", 2);
            productBean.create(113, "Terceiro", "Tesla", 3, "Elétrico", 3);
            productBean.create(114, "Quarto", "Toyota", 4, "Híbrido", 4);
            productBean.create(115, "Quinto", "Honda", 5, "SUV", 5);
            productBean.create(116, "Sexto", "BMW", 6, "Luxo", 5);
            productBean.create(117, "Sétimo", "Audi", 7, "Esportivo", 2);
            productBean.create(118, "Oitavo", "Mercedes", 8, "Sedan", 3);
            productBean.create(119, "Nono", "Chevrolet", 9, "Crossover", 4);
            productBean.create(120, "Décimo", "Nissan", 10, "Pick-Up", 1);

            // Add Products to Volumes
            productVolumeMapping.create(111, 1, 1);
            productVolumeMapping.create(112, 1, 2);

            productVolumeMapping.create(113, 2, 1);

            productVolumeMapping.create(114, 3, 1);
            productVolumeMapping.create(115, 3, 2);

            productVolumeMapping.create(116, 4, 1);

            productVolumeMapping.create(117, 5, 1);
            productVolumeMapping.create(118, 5, 2);

            for (int i = 0; i < 5; i++){
                int wow = i+15;
                double wow2 = 1.65 + i*0.01;
                sensorBean.createNewSingleValue(new SensorDataDTO(Integer.toString(wow),1));
                sensorBean.createNewSingleValue(new SensorDataDTO(Double.toString(wow2),37));
            }


        } catch (Exception e) {
            logger.severe("Config Bean: "+e.getMessage());
        }
    }
}
