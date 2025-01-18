package pt.ipleiria.estg.dei.ei.dae.project.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
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
            for (int i = 1; i <= 12; i++) {
                productTypeBean.create(i);
            }

            // Assign Sensors to Product Types
            productTypeBean.addSensor(1, sensorTypeBean.find(1), 10);
            productTypeBean.addSensor(2, sensorTypeBean.find(2), 5);
            productTypeBean.addSensor(3, sensorTypeBean.find(3), 8);
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
            packageTypeBean.addSensor(1, sensorTypeBean.find(1), 10);
            packageTypeBean.addSensor(2, sensorTypeBean.find(2), 5);
            packageTypeBean.addSensor(3, sensorTypeBean.find(3), 8);
            packageTypeBean.addSensor(4, sensorTypeBean.find(1), 1);

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
            for (int i = 1; i <= 12; i++) {
                volumeBean.create(i, VolumeState.PACKED, i, i, Date.from(Instant.now()));
            }

            // Existing Sensors
            sensorBean.create(1, 1, "22.5", Date.from(Instant.now()), 1);  // Temperature Sensor - Temperature value in Celsius
            sensorBean.create(2, 2, "1013.25", Date.from(Instant.now()), 2);  // Atmospheric Pressure Sensor - Pressure in hPa
            sensorBean.create(3, 3, "0.05", Date.from(Instant.now()), 3);  // Accelerometer - Acceleration value in m/s²
            sensorBean.create(4, 4, "39.908889, -8.821111", Date.from(Instant.now()), 4);  // Global Positioning Sensor - Latitude and Longitude
            sensorBean.create(5, 5, "55", Date.from(Instant.now()), 5);  // Humidity Sensor - Humidity percentage
            sensorBean.create(6, 1, "25.0", Date.from(Instant.now()), 6);  // Temperature Sensor - Temperature value in Celsius
            sensorBean.create(7, 2, "1020.0", Date.from(Instant.now()), 7);  // Atmospheric Pressure Sensor - Pressure in hPa
            sensorBean.create(8, 3, "0.10", Date.from(Instant.now()), 8);  // Accelerometer - Acceleration value in m/s²
            sensorBean.create(9, 4, "39.900000, -8.820000", Date.from(Instant.now()), 1);  // Global Positioning Sensor - Latitude and Longitude
            sensorBean.create(10, 5, "60", Date.from(Instant.now()), 2);  // Humidity Sensor - Humidity percentage
            sensorBean.create(11, 1, "18.5", Date.from(Instant.now()), 3);  // Temperature Sensor - Temperature value in Celsius
            sensorBean.create(12, 2, "1015.75", Date.from(Instant.now()), 4);  // Atmospheric Pressure Sensor - Pressure in hPa


            // Existing Products
            productBean.create(111, "Primeiro", "Volkswagen", 1, "Grande PóPó", 1);
            productBean.create(112, "Segundo", "Ford", 2, "Compacto", 2);
            productBean.create(113, "Terceiro", "Tesla", 3, "Elétrico", 3);
            productBean.create(114, "Quarto", "Toyota", 4, "Híbrido", 4);
            productBean.create(115, "Quinto", "Honda", 5, "SUV", 5);
            productBean.create(116, "Sexto", "BMW", 6, "Luxo", 6);
            productBean.create(117, "Sétimo", "Audi", 7, "Esportivo", 7);
            productBean.create(118, "Oitavo", "Mercedes", 8, "Sedan", 8);
            productBean.create(119, "Nono", "Chevrolet", 9, "Crossover", 9);
            productBean.create(120, "Décimo", "Nissan", 10, "Pick-Up", 10);

            // Add Products to Volumes
            productVolumeMapping.create(111, 1, 1);
            productVolumeMapping.create(112, 2, 1);
            productVolumeMapping.create(113, 3, 1);
            productVolumeMapping.create(114, 4, 1);
            productVolumeMapping.create(115, 5, 1);
            productVolumeMapping.create(116, 6, 2);
            productVolumeMapping.create(117, 7, 2);
            productVolumeMapping.create(118, 8, 2);
            productVolumeMapping.create(119, 9, 2);
            productVolumeMapping.create(120, 10, 2);
            productVolumeMapping.create(111, 11, 3);
            productVolumeMapping.create(112, 12, 3);


        } catch (Exception e) {
            logger.severe("Config Bean: "+e.getMessage());
        }
    }
}
